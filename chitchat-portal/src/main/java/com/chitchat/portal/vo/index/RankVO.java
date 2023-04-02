package com.chitchat.portal.vo.index;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Js on 2022/11/10 .
 **/
@Data
@ApiModel("排行榜-响应体")
public class RankVO implements Serializable {

    @ApiModelProperty(value = "房间/用户Id")
    private Long targetId;

    @ApiModelProperty(value = "房间/用户名称")
    private String targetName;
    @ApiModelProperty(value = "房间/用户头像")
    private String targetImg;
    //性别
    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "国家")
    private String country;

    //用户等级
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

    @ApiModelProperty(value = "排行榜值")
    private Long rankScore;

}
