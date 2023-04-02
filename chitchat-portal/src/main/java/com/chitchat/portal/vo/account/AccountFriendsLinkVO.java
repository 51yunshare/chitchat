package com.chitchat.portal.vo.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 好友关联
 */
@Data
@Accessors(chain = true)
public class AccountFriendsLinkVO implements Serializable {

    private static final long serialVersionUID = 8382674223890249143L;

//    @ApiModelProperty(value = "主键")
//    private Long id;

    @ApiModelProperty(value = "好友账号id")
    private Long accountId;

//    @ApiModelProperty(value = "好友账号id")
//    private Long friendAccountId;

//    @ApiModelProperty(value = "是否好友1-是 0-否")
//    private Integer friendStatus;
    //用户昵称
    @ApiModelProperty("用户昵称")
    private String nickName;
    //头像
    @ApiModelProperty("头像")
    private String icon;
    //性别
    @ApiModelProperty("性别：0->未知；1->男；2->女")
    private Integer gender;
    /**
     * 国家
     */
    @ApiModelProperty("国家")
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

    @ApiModelProperty(value = "取消好友时间")
    private Date cancelTime;

    @ApiModelProperty(value = "添加好友时间(二次添加)")
    private Date friendTime;

}
