package com.chitchat.portal.util;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.chitchat.common.enumerate.HttpMethodName;
import com.chitchat.portal.enumerate.EnumZegoRequestEvent;
import okhttp3.OkHttpClient;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Map;

/**
 * 生成签名
 *
 * 调用Zego接口生成签名
 *
 * md5(AppId + SignatureNonce + ServerSecret + Timestamp)
 */
public class SignatureUtil {
    /**
     * 字节数组转16进制
     *
     * @param bytes 需要转换的byte数组
     * @return 转换后的Hex字符串
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();
        //把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 0; i < bytes.length; i++) {
            digital = bytes[i];
            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString();
    }

    /**
     * 生成签名
     *
     * @param appId
     * @param signatureNonce
     * @param serverSecret
     * @param timestamp
     * @return
     */
    // Signature=md5(AppId + SignatureNonce + ServerSecret + Timestamp)
    public static String GenerateSignature(long appId, String signatureNonce, String serverSecret, long timestamp) {
        String str = appId + signatureNonce + serverSecret + timestamp;
        String signature = "";
        try {
            //创建一个提供信息摘要算法的对象，初始化为md5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            //计算后获得字节数组
            byte[] bytes = md.digest(str.getBytes("utf-8"));
            //把数组每一字节换成16进制连成md5字符串
            signature = bytesToHex(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signature;
    }

    public static String GenerateSignature(long appId, String serverSecret, long timestamp) {
        return GenerateSignature(appId, getSignatureNonce(), serverSecret, timestamp);
    }

    public static String GenerateSignature(long appId, String serverSecret) {
        return GenerateSignature(appId, serverSecret, System.currentTimeMillis() / 1000L);
    }

    /**
     * 生成16进制随机字符串
     *
     * @return
     */
    public static String getSignatureNonce(){
        //生成16进制随机字符串(16位)
        byte[] bytes = new byte[8];
        //使用SecureRandom获取高强度安全随机数生成器
        SecureRandom sr = new SecureRandom();

        sr.nextBytes(bytes);
        return bytesToHex(bytes);
    }



    public static void main(String[] args) {
        String signatureNonce = getSignatureNonce();
//        long appId = 963448231L;       //使用你的appId和serverSecret，数字后要添加大写L或小写l表示long类型
        String appId = "735480636";
        String serverSecret = "13e85d85a7a71d89213c9b069ef35afd";
        long timestamp = System.currentTimeMillis() / 1000L;
        System.out.println(GenerateSignature(Long.parseLong(appId), signatureNonce, serverSecret, timestamp));


        RequestUtil.CommonParam commonParam = RequestUtil.getCommonParam(Long.parseLong(appId), serverSecret);
        Map<String, Object> param = BeanUtil.beanToMap(commonParam);
//        param.put("RoomId[]", 1);
//        param.put("RoomId[]", 2);
//        param.put("RoomId[]", 3);
        System.out.println(param);
        RequestUtil.InterRequest request = RequestUtil.InterRequest.builder()
                .httpMethod(HttpMethodName.GET)
                .param(param)
                .uri(RequestUtil.getUrl("https://rtc-api-sha.zego.im/" , EnumZegoRequestEvent.获取房间人数))
                .build();
//        JSONObject response = RequestUtil.request(request,false, true);
//        JSONObject responseR = RequestUtil.request(request,
//                "RoomId[]", 1, "RoomId[]", 2,"RoomId[]", 3,
//                "AppId", commonParam.getAppId(), "SignatureNonce", commonParam.getSignatureNonce(),
//                "Signature", commonParam.getSignature(), "SignatureVersion", commonParam.getSignatureVersion(),
//                "Timestamp",  commonParam.getTimestamp());
        JSONObject responseS = RequestUtil.request(request, true, "RoomId[]", Arrays.asList("1","2","3"));

        OkHttpClient client = new OkHttpClient();
    }
}
