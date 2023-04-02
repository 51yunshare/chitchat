package com.chitchat.portal.vo.index;

import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 搜索房间结果
 * Created by Js on 2022/8/17.
 **/
@Data
@ApiModel("搜索房间-响应体")
public class SearchRoomVO implements Serializable {

    @ApiModelProperty(value = "房间id")
    private Long id;

    @ApiModelProperty(value = "房间名称")
    private String roomName;
    /**
     * 房间图片
     */
    @ApiModelProperty(value = "房间图片")
    private String roomImg;
    //人数
    @ApiModelProperty(value = "房间在线人数")
    private Integer roomUserNum;
    /**
     * 房间上限人数
     */
    @ApiModelProperty(value = "房间上限人数")
    private Integer limitUserNum;
    /**
     * 公告
     */
    @ApiModelProperty(value = "房间公告")
    private String roomNotice;
    /**
     * 房间标签
     *
     */
    @ApiModelProperty(value = "房间标签")
    private JSONArray roomTag;
}
