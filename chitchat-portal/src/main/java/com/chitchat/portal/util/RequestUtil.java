package com.chitchat.portal.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import com.chitchat.common.base.CodeMsg;
import com.chitchat.common.enumerate.HttpMethodName;
import com.chitchat.common.exception.ChitchatException;
import com.chitchat.portal.enumerate.EnumZegoRequestEvent;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Http请求
 * <p>
 * Created by Js on 2022/8/16.
 */
@Slf4j
public class RequestUtil {
    /**
     * 请求体
     */
    @Data
    @Builder
    public static class InterRequest {
        private Map<String, Object> param;
        private Map<String, String> header;
        private String requestBody;
        private HttpMethodName httpMethod;
        private String uri;
    }

    /**
     * 公共参数
     *
     * AppId	Uint32	是	AppId，ZEGO 分配的用户唯一凭证。
     * Signature	String	是	签名，签名的生成请参考 3 签名机制。
     * SignatureNonce	String	是	随机数。
     * SignatureVersion	String	是	签名版本号，默认值为 2.0。
     * Timestamp Int64	是	Unix 时间戳，单位为秒。最多允许 10 分钟的误差。
     * IsTest String 否（2021-11-16 之后创建的项目） 是否为测试环境。默认为正式环境，可忽略不填写
     *
     */
    @Data
    @Builder
    public static class CommonParam {
        private long AppId;
        private String Signature;
        private String SignatureNonce;
        private String SignatureVersion;
        private long Timestamp;
        private String IsTest;
    }


    /**
     * 发送请求
     *
     * @param request
     * @param body
     * @param getForm
     * @return
     */
    public static JSONObject request(InterRequest request, boolean body, boolean getForm) {
        String response = "";
        HttpResponse httpResponse = null;
        if (request.getHttpMethod() == HttpMethodName.GET) {
            HttpRequest httpRequest = HttpRequest.get(request.getUri())
                    .addHeaders(request.getHeader())
                    .form(getForm ? request.getParam() : null)
                    .timeout(Integer.MAX_VALUE);//超时，毫秒

            if (body) {
                httpRequest.body(body ? request.getRequestBody() : null);
            }
            httpResponse = httpRequest.execute();
            response = httpResponse.body();
        } else if (request.getHttpMethod() == HttpMethodName.POST) {

            HttpRequest httpRequest = HttpRequest.post(request.getUri())
                    .addHeaders(request.getHeader())
                    .form(body == false ? request.getParam() : null)
                    .timeout(Integer.MAX_VALUE);//超时，毫秒
            if (body) {
                httpRequest.body(body ? request.getRequestBody() : null);
            }
            httpResponse = httpRequest.execute();
            response = httpResponse.body();
        }
        JSONObject result = formatResult(response, httpResponse);
        return result;
    }

    /**
     * Get 重复参数没法使用map
     *
     * @param request
     * @param name
     * @param value
     * @param parameters
     * @return
     */
    public static JSONObject request(InterRequest request, String name, Object value, Object... parameters) {
        HttpRequest httpRequest = HttpRequest.get(request.getUri())
                .addHeaders(request.getHeader())
                .form(name, value, parameters)
                .timeout(Integer.MAX_VALUE);//超时，毫秒
        HttpResponse httpResponse = httpRequest.execute();
        JSONObject result = formatResult(httpResponse.body(), httpRequest.execute());
        return result;
    }

    /**
     * Get请求重复参数处理
     *
     * @param request
     * @param getForm
     * @param name
     * @param value
     * @return
     */
    public static JSONObject request(InterRequest request, boolean getForm, String name, Object value) {
        HttpRequest httpRequest = HttpRequest.get(request.getUri())
                .addHeaders(request.getHeader())
                .form(getForm ? request.getParam() : null)
                .timeout(Integer.MAX_VALUE);//超时，毫秒
        //重复参数单独处理
        if (name != null && value != null){
            getHttpRequestByRepeat(httpRequest, name, value);
        }
        HttpResponse httpResponse = httpRequest.execute();
        JSONObject result = formatResult(httpResponse.body(), httpResponse);
        return result;
    }

    /**
     * 重复参数处理
     *
     * @param httpRequest
     * @param name
     * @param value
     */
    private static void getHttpRequestByRepeat(HttpRequest httpRequest, String name, Object value) {
        //重复参数单独处理
        Collection cl = (Collection) value;
        Iterator it = cl.iterator();
        while(it.hasNext()){
            httpRequest.form(name, it.next());
        }
    }


    /**
     * 格式化响应体
     *
     * @param response
     * @return
     */
    private static JSONObject formatResult(String response) {
        log.debug("请求返回原始信息：{}", response);
        JSONObject result = JSONObject.parseObject(response);
        if (result.getIntValue("Code") == 0) {
            log.debug("请求成功，返回信息：{}", response);
            return result;
        } else {
            String errMsg = result.getString("Message");
            log.error("请求出错，错误码：{}；错误原因：{}；\n\r全量错误：{}", result.getInteger("Code"), errMsg, response);
            throw new ChitchatException(CodeMsg.SERVER_ERROR, errMsg);
        }
    }

    private static JSONObject formatResult(String response, HttpResponse httpResponse) {
        log.debug("请求返回原始信息：{}", response);
        JSONObject result = JSONObject.parseObject(response);
        if (httpResponse != null
                && httpResponse.getStatus() == 200 || httpResponse.getStatus() == 201) {
            log.debug("请求成功，返回信息：{}", response);
            return result;
        } else {
            return formatResult(response);
        }
    }

    /**
     * 拼接URL
     *
     * @param baseUrl
     * @param requestEvent
     * @return
     */
    public static String getUrl(String baseUrl, EnumZegoRequestEvent requestEvent) {
        return baseUrl + "?Action=" + requestEvent.getUri();
    }

    /**
     * 封装公共参数
     *
     * @return
     */
    public static CommonParam getCommonParam(long appId, String serverSecret){
        final String signatureNonce = SignatureUtil.getSignatureNonce();
        final long timeStamp = DateUtil.currentSeconds();
        return CommonParam.builder()
                .AppId(appId)
                .Signature(SignatureUtil.GenerateSignature(appId, signatureNonce, serverSecret, timeStamp))
                .SignatureNonce(signatureNonce)
                .SignatureVersion("2.0")
                .Timestamp(timeStamp)
                .build();
    }
}
