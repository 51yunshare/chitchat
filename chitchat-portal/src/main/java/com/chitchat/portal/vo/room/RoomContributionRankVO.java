package com.chitchat.portal.vo.room;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 房间用户贡献榜单信息
 *
 * Created by Js on 2022/8/3 .
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("房间用户贡献榜单信息")
public class RoomContributionRankVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long accountId;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户昵称")
    private String nickName;
    //头像
    @ApiModelProperty(value = "用户头像")
    private String icon;
    //性别
    @ApiModelProperty(value = "用户性别")
    private Integer gender;
    //国家
    @ApiModelProperty(value = "用户国家")
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

    /**
     * 贡献值
     */
    @ApiModelProperty(value = "用户贡献值")
    private BigDecimal contributionValue;
}
