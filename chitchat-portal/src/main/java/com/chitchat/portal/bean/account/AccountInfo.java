package com.chitchat.portal.bean.account;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Js on 2022/7/31.
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "背景图片")
    private String bgImg;

    @ApiModelProperty(value = "头像")
    private String icon;

    @ApiModelProperty(value = "性别：0->未知；1->男；2->女")
    private Integer gender;

    @ApiModelProperty(value = "生日")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date birthday;

    @ApiModelProperty(value = "所在城市")
    private String city;
    /**
     * 国家
     */
    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "职业")
    private String job;

    @ApiModelProperty(value = "个性签名")
    private String personalizedSignature;
    /**
     * 用户等级
     */
    @ApiModelProperty(value = "用户等级")
    private Long accountLevelId;
    /**
     * 用户等级经验值
     */
    @ApiModelProperty(value = "用户等级经验值")
    private Integer accountLevelExp;
    /**
     * 游戏
     */
    @ApiModelProperty(value = "游戏等级")
    private Long accountGameLevelId;
    /**
     * 游戏等级经验值
     */
    @ApiModelProperty(value = "游戏等级经验值")
    private Integer accountGameLevelExp;
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

    @ApiModelProperty(value = "用户来源")
    private Integer sourceType;
    /**
     * 我的关注数量
     */
    @ApiModelProperty(value = "我的关注数量")
    private Integer followingNum;
    /**
     * 关注我的数量
     */
    @ApiModelProperty(value = "关注我的数量")
    private Integer followerNum;

    @ApiModelProperty(value = "帐号启用状态:0->禁用；1->启用")
    private Integer status;

    @ApiModelProperty(value = "注册时间")
    private Date createdTime;

    @ApiModelProperty(value = "最后登录时间")
    private Date lastLoginTime;
}
