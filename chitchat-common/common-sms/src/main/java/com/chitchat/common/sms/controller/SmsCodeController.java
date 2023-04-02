package com.chitchat.common.sms.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.CodeMsg;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.exception.ChitchatException;
import com.chitchat.common.redis.RedisKey;
import com.chitchat.common.redis.RedisTemplateStringUtil;
import com.chitchat.common.sms.bean.ShortMsgInfo;
import com.chitchat.common.sms.dto.ShortMsgRequest;
import com.chitchat.common.sms.enumerate.EnumMessageType;
import com.chitchat.common.sms.service.AliSms;
import com.chitchat.common.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Api(tags = "短信验证码")
@RestController
@RequestMapping("/sms")
@RequiredArgsConstructor
public class SmsCodeController extends BaseController {

    private final AliSms aliSms;

    private final RedisTemplateStringUtil redisTemplateUtil;

    @ApiOperation(value = "发送短信验证码")
    @ApiImplicitParam(name = "mobile", example = "18500504388", value = "手机号", required = true)
    @PostMapping("/send")
    public ResultTemplate sendSmsCode(String mobile) {
        if (StrUtil.isBlank(mobile) || !StringUtil.isMobileNOThird(mobile)){
            throw new ChitchatException(CodeMsg.BIND_ERROR, "手机号码为空或格式错误！");
        }
        long nowTime = DateUtil.currentSeconds();
        //判断发送短信验证码的时间(限制5分钟不能重复发送)
        ShortMsgInfo oldMsg = redisTemplateUtil.getKey(RedisKey.getSmsCode(ShortMsgInfo.class, mobile), ShortMsgInfo.class);
        if (oldMsg != null) {
            int flag = (int) (nowTime - oldMsg.getCreatedTime());
            if (flag < 60*5) {
                throw new ChitchatException(CodeMsg.SMS_FAIL, "5分钟之内不能重复发送手机短信");
            }
        }
        String code = RandomUtil.randomNumbers(6);
        ShortMsgInfo msgInfo = new ShortMsgInfo().setCode(code).setCreatedTime(nowTime).setMobile(mobile);
        boolean result = aliSms.send(mobile, EnumMessageType.用户注册, code);
        if (result){
            redisTemplateUtil.setValue(RedisKey.getSmsCode(ShortMsgInfo.class, mobile), JSONObject.toJSONString(msgInfo), 5, TimeUnit.MINUTES);
        }
        return this.success(result);
    }

    /**
     * 校验手机验证码
     *
     * @param request
     * @return
     */
    @ApiOperation("校验手机验证码")
    @PostMapping(value = "/checkCode")
    public ResultTemplate doCheckCode(@RequestParam @Validated ShortMsgRequest request) {
        //手机号
        if (StrUtil.isBlank(request.getMobile()) && !StringUtil.isMobileNOThird(request.getMobile())){
            throw new ChitchatException(CodeMsg.BIND_ERROR, "手机号码为空或格式错误！");
        }
        //校验验证码
        ShortMsgInfo oldMsg = redisTemplateUtil.getKey(RedisKey.getSmsCode(ShortMsgInfo.class, request.getMobile()), ShortMsgInfo.class);
        if (oldMsg == null){
            throw new ChitchatException(CodeMsg.BIND_ERROR, "验证码已经过期！");
        }
        if (StrUtil.isBlank(request.getCode()) || StrUtil.isBlank(oldMsg.getCode()) || !oldMsg.getCode().equals(request.getCode())) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, "验证码错误！");
        }
        redisTemplateUtil.remove(RedisKey.getSmsCode(ShortMsgInfo.class, request.getMobile()));
        return this.success();
    }
}
