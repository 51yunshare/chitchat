package com.chitchat.portal.vo.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Js on 2022/7/31.
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("个人中心返回对象(粉丝/关注/访客/朋友)")
public class AccountFollowLinkVO implements Serializable {

    private static final long serialVersionUID = 1L;
//    @ApiModelProperty("关联id")
//    private Long id;
    /**
     * 关注目标id
     */
//    @ApiModelProperty("关注用户id")
//    private Long targetId;
    /**
     * 用户id
     */
    @ApiModelProperty("粉丝/关注/访客/朋友的用户id")
    private Long accountId;
    /**
     * 关注状态
     */
    @ApiModelProperty("是否关注(1-是 0-取消关注)")
    private Integer followStatus;
    /**
     * 是否互关
     */
//    @ApiModelProperty("是否互关(0-否 1-是)")
//    private Integer friend;
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
    //时间
//    @ApiModelProperty("关注时间")
//    private Date createdTime;
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
