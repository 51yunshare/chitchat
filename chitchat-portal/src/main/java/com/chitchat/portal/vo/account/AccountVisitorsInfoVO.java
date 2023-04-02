package com.chitchat.portal.vo.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 账号访客信息
 */
@Data
@Accessors(chain = true)
public class AccountVisitorsInfoVO implements Serializable {

    private static final long serialVersionUID = -6080451162642484329L;

//    @ApiModelProperty(value = "账号访客关联主键id")
//    private Long id;

    @ApiModelProperty(value = "访客账号id")
    private Long accountId;

//    @ApiModelProperty(value = "账号id")
//    private Long accountId;

    @ApiModelProperty(value = "访客时间")
    private Date createdTime;

    //昵称
    @ApiModelProperty("用户昵称")
    private String nickName;
    //头像
    @ApiModelProperty("用户icon")
    private String icon;
    //性别
    @ApiModelProperty("用户性别: 0-未知 1-男 2-女")
    private Integer gender;
    /**
     * 国家
     */
    @ApiModelProperty("用户国家")
    private String country;
    /**
     * 用户等级
     */
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

}
