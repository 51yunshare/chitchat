package com.chitchat.portal.api.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 */
@Data
@Builder
public class AccountInfoVO implements Serializable {

    private static final long serialVersionUID = -6970497192451104222L;
    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户登录名")
    private String username;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("电话")
    private String mobile;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("背景图")
    private String bgImg;

    @ApiModelProperty("用户头像")
    private String icon;

    @ApiModelProperty(value = "性别：0->未知；1->男；2->女")
    private Integer gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    @ApiModelProperty(value = "出生年月")
    private Date birthday;

    /**
     * 国家
     */
    @ApiModelProperty(value = "所在国家")
    private String country;
    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "职业")
    private String job;

    @ApiModelProperty(value = "个人签名")
    private String personalizedSignature;

    @ApiModelProperty(value = "用户等级")
    private Long accountLevelId;
    /**
     * 游戏
     */
    @ApiModelProperty(value = "游戏等级")
    private Long accountGameLevelId;
    /**
     * VIP等级
     */
    @ApiModelProperty(value = "VIP等级")
    private Long vipLevelId;
    /**
     * 贵族等级
     */
    @ApiModelProperty(value = "贵族等级")
    private Long kingLevelId;

    private Integer sourceType;

    private Integer integration;

    private Integer growth;

    private Integer luckCount;

    private Integer historyIntegration;

    private Integer status;

    private Date createdTime;
}
