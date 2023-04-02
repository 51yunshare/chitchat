package com.chitchat.portal.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;

/**
 * Zego回调接口签名校验
 * <p>
 * Created by Js on 2022/8/18 .
 **/
public class CallbackSignUtil {


    /**
     *
     * // 从请求参数中获取到 signature, timestamp, nonce
     * String signature = request.getParameter("signature");
     * long timestamp = request.getParameter("timestamp");
     * String nonce = request.getParameter("nonce");
     *
     * // 后台获取的 callbacksecret
     * String secret = callbacksecret;
     *
     * String[] tempArr = {secret, ""+timestamp, nonce};
     * Arrays.sort(tempArr);
     *
     * String tmpStr = "";
     * for (int i = 0; i < tempArr.length; i++) {
     *     tmpStr += tempArr[i];
     * }
     * tmpStr = org.apache.commons.codec.digest.DigestUtils.sha1Hex(tmpStr);
     *
     * return tmpStr.equals(signature);
     *
     *
     * $timestamp = 1470820198;
     * $nonce = 123412;
     * $secret = 'secret';
     *
     * 排序拼接后需要加密的原始串为:1234121470820198secret
     * 加密的结果为:5bd59fd62953a8059fb7eaba95720f66d19e4517
     *
     *
     */

    /**
     * 回调签名校验
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param callbackSecret
     * @return
     */
    public static boolean checkCallbackSign(String signature, long timestamp, String nonce, String callbackSecret) {
        String[] tempArr = {callbackSecret, "" + timestamp, nonce};
        Arrays.sort(tempArr);

        String tmpStr = "";
        for (int i = 0; i < tempArr.length; i++) {
            tmpStr += tempArr[i];
        }
        tmpStr = DigestUtils.sha1Hex(tmpStr);

        return tmpStr.equals(signature);
    }
}
