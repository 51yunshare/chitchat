package com.chitchat.portal.bean.account;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class AccountLoginLog implements Serializable {
    private static final long serialVersionUID = 3872471619398313443L;

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "会员id")
    private Long accountId;
    @ApiModelProperty(value = "登录名")
    private String username;
    @ApiModelProperty(value = "登录密码")
    private String password;

    @ApiModelProperty(value = "登录ip")
    private String loginIp;

    @ApiModelProperty(value = "登录类型：0->PC；1->android;2->ios;3->小程序")
    private Integer loginType;
    /**
     * 浏览器 UA
     */
    @ApiModelProperty(value = "浏览器UA")
    private String userAgent;
    /**
     * 登陆结果
     */
    @ApiModelProperty(value = "登陆结果")
    private JSONObject loginResult;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

}
