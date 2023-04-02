package com.chitchat.common.sms.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.chitchat.common.sms.config.AliSmsConfig;
import com.chitchat.common.sms.enumerate.EnumMessageType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 阿里短信通道
 * Created by Js on 2022/8/4 .
 */
@Service
@Slf4j
public class AliSms implements Sms {

    @Autowired
    private AliSmsConfig aliSmsConfig;

    @Override
    public boolean send(String mobile, EnumMessageType shortMessageType , String... templateParam ) {
        return this.toSend(mobile , aliSmsConfig.getAliSmsProperties().getModel().get(shortMessageType.getConfigName())
                , shortMessageType.getParamName() ,templateParam);
    }

    private boolean toSend(String mobile , String model ,String[] paramName , String... templateParam){
        //请求失败这里会抛ClientException异常
        try {
            SendSmsResponse sendSmsResponse = aliSmsConfig.getIAcsClient().getAcsResponse(this.getSendSmsRequest(mobile , model ,paramName, templateParam));
            if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                log.debug("短信发送成功，手机号：{}；模板id：{}" , mobile,model);
                return true;
            }else{
                log.error("短信发送失败，code：{}；message：{}；模板信息：{}",sendSmsResponse.getCode(), sendSmsResponse.getMessage(),model);
//                throw new ChitchatException(String.format("短信发送失败，code：%s；message：%s；模板信息：%s",sendSmsResponse.getCode(), sendSmsResponse.getMessage(), model));
            }
        } catch (ClientException e) {
            log.error("短信发送失败，未知错误：{}" , ExceptionUtils.getStackTrace(e));
//            throw new ChitchatException(String.format("短信发送失败，未知错误：%s" , ExceptionUtils.getStackTrace(e)));
        }
        return false;
    }

    private SendSmsRequest getSendSmsRequest(String mobile, String model, String[] paramName, String... templateParam){
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
        request.setPhoneNumbers(mobile);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(aliSmsConfig.getAliSmsProperties().getSignName());
        //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
        request.setTemplateCode(model);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam(this.covParam(paramName,templateParam));
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
//        request.setOutId("yunxiu");
        return request;
    }

    private String covParam(String[] paramName , String[] templateParam){
        JSONObject result = new JSONObject();
        for (int i = 0; i < paramName.length; i++) {
            result.put(paramName[i],templateParam[i]);
        }
        return result.toJSONString();
    }
}
