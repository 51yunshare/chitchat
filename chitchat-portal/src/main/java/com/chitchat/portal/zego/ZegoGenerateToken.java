package com.chitchat.portal.zego;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Base64;

/**
 * 即构生成Token(基础Token、权限Token)
 * 创建房间
 * <p>
 * Created by Js on 2022/8/3 .
 **/
@Slf4j
public class ZegoGenerateToken {

    /**
     * 有效时间，单位：秒
     */
    private static final int effectiveTimeInSeconds = 300;

    /**
     * 生成权限Token
     *
     * @param appId
     * @param serverSecret
     * @param userId
     * @param roomId
     * @return
     */
    public static JSONObject generateAuthToken(String appId, String serverSecret, String userId, String roomId) {
        TokenServerAssistant.TokenInfo token = getTokenInfo(appId, serverSecret, userId, roomId);
        if (token.error == null || token.error.code == TokenServerAssistant.ErrorCode.SUCCESS) {
            log.info("\r\ndecrypt the token ...");
            return decryptToken(token.data, serverSecret);
        }
        return null;
    }

    /**
     * 生成基础Token
     *
     * @param appId
     * @param serverSecret
     * @param userId
     * @return
     */
    public static JSONObject generateBaseToken(String appId, String serverSecret, String userId) {
        TokenServerAssistant.TokenInfo token = getTokenInfo(appId, serverSecret, userId);
        log.info(" token 主体 : {}" ,token.data);
        if (token.error == null || token.error.code == TokenServerAssistant.ErrorCode.SUCCESS) {
            log.info("\r\ndecrypt the token ...");
            return decryptToken(token.data, serverSecret);
        }
        return null;
    }

    /**
     * Token封装
     *
     * @param appId
     * @param serverSecret
     * @param userId
     * @return
     */
    private static TokenServerAssistant.TokenInfo getTokenInfo(String appId, String serverSecret, String userId) {
        TokenServerAssistant.VERBOSE = true;    // 调试时，置为 true, 可在控制台输出更多信息；正式运行时，最好置为 false
        TokenServerAssistant.TokenInfo token = TokenServerAssistant.generateToken04(Long.parseLong(appId), userId, serverSecret, effectiveTimeInSeconds, getPayload());
        return token;
    }

    private static TokenServerAssistant.TokenInfo getTokenInfo(String appId, String serverSecret, String userId, String roomId) {
        TokenServerAssistant.VERBOSE = false;    // 调试时，置为 true, 可在控制台输出更多信息；正式运行时，最好置为 false
        TokenServerAssistant.TokenInfo token = TokenServerAssistant.generateToken04(Long.parseLong(appId), userId, serverSecret, effectiveTimeInSeconds, getPayload(roomId));
        return token;
    }

    /**
     * 基础鉴权 token，payload字段可传空
     *
     * @return
     */
    private static String getPayload() {
        //填入自定义的payload值，如示例payload字段。 非必输，不传则赋值null。
        return "{\"payload\":\"payload\"}";
    }

    private static String getPayload(String roomId) {
        JSONObject payloadData = new JSONObject();
        // 房间id，限制用户只能登录特定房间，必输。
        payloadData.put("room_id", roomId);
        // 必输，权限位
        JSONObject privilege = new JSONObject();
        //登录房间权限 TokenServerAssistant.PrivilegeEnable 代表允许，TokenServerAssistant.PrivilegeDisable 代表不允许
        //此处代表允许登录房间
        privilege.put(TokenServerAssistant.PrivilegeKeyLogin, TokenServerAssistant.PrivilegeEnable);

        //是否允许推流 TokenServerAssistant.PrivilegeEnable 代表允许，TokenServerAssistant.PrivilegeDisable 代表不允许
        //此处代表不允许推流
        privilege.put(TokenServerAssistant.PrivilegeKeyPublish, TokenServerAssistant.PrivilegeDisable);
        payloadData.put("privilege", privilege); // 必输，登录房间、推流两个权限位必须赋值其中一个或两个。
        payloadData.put("stream_id_list", null); // 流列表，非必输
        return payloadData.toJSONString();

    }

    /**
     * Token 加密
     *
     * @param token
     * @param secretKey
     */
    private static JSONObject decryptToken(String token, String secretKey) {
        String noVersionToken = token.substring(2);

        byte[] tokenBytes = Base64.getDecoder().decode(noVersionToken.getBytes());
        ByteBuffer buffer = ByteBuffer.wrap(tokenBytes);
        buffer.order(ByteOrder.BIG_ENDIAN);
        long expiredTime = buffer.getLong();
        log.info("expiredTime: {}", expiredTime);
        int IVLength = buffer.getShort();
        byte[] ivBytes = new byte[IVLength];
        buffer.get(ivBytes);
        int contentLength = buffer.getShort();
        byte[] contentBytes = new byte[contentLength];
        buffer.get(contentBytes);

        try {
            SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "AES");
            IvParameterSpec iv = new IvParameterSpec(ivBytes);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, iv);

            byte[] rawBytes = cipher.doFinal(contentBytes);
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(new String(rawBytes));
            log.info("Token 加密后：{}", json);
            return json;
        } catch (Exception e) {
            log.error("decrypt failed: {}", e);
        }
        return null;
    }

    enum TokenType {
        /**
         * 基础Token
         * 描述： 开发者在登录房间时必须带上 Token 参数，来验证用户的合法性。
         * 场景：基础鉴权 Token 为 Token 的基本能力，用于业务的简单权限验证场景，绝大多数情况下生成该 Token 即可。
         */
        BASE,
        /**
         * 权限Token
         * 描述： 为了进一步提高安全性开放了房间 ID 和推流 ID 这两个权限位，可以验证登录房间的 ID 和推流 ID。
         * <p>
         * 场景：房间 ID 和推流 ID 权限位的一般使用场景如下：
         * <p>
         * 1.房间有普通房间和会员房间的区别，需要控制非会员用户登录会员房间。
         * 2.语聊房或秀场直播中，需要控制推流用户和麦上用户的一致，防止“幽灵麦”现象，即在房间里听到了非麦上用户声音的情况。
         * 3.狼人杀等发言游戏，需要防止应用被黑客破解之后，黑客可以使用其他用户 ID 登录同一房间，获取到游戏进行的信息进行作弊，影响正常用户的游戏体验。
         */
        AUTH;
    }
}
