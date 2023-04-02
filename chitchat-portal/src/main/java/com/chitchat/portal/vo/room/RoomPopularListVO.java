package com.chitchat.portal.vo.room;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 推荐房间信息
 *
 * Created by Js on 2022/8/17.
 **/
@Data
public class RoomPopularListVO extends MyRoomPageListVO {

    @ApiModelProperty("推荐排序")
    private Integer recommendSort;
}
