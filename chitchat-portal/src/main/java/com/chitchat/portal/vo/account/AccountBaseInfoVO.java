package com.chitchat.portal.vo.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户基本信息
 *
 * Created by Js on 2022/7/31.
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户基本信息")
public class AccountBaseInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    private Long id;
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
