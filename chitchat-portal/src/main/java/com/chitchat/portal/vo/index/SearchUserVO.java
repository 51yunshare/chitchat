package com.chitchat.portal.vo.index;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 搜索用户结果
 * Created by Js on 2022/8/17.
 **/
@Data
@ApiModel("搜索用户-响应体")
public class SearchUserVO implements Serializable {

    //id
    @ApiModelProperty(value = "用户id")
    private Long id;
    //昵称
    @ApiModelProperty(value = "用户昵称")
    private String nickName;
    //头像
    @ApiModelProperty(value = "用户头像")
    private String icon;
    //性别
    @ApiModelProperty(value = "性别")
    private Integer gender;

    /**
     * 国家
     */
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
    //是否关注
    @ApiModelProperty(value = "是否关注(true-是 false-否)")
    private boolean follow;
}
