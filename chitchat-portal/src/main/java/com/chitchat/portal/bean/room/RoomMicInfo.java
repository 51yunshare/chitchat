package com.chitchat.portal.bean.room;

import com.chitchat.common.base.BaseBean;
import lombok.Builder;
import lombok.Data;

/**
 * 房间麦位信息
 *
 * Created by Js on 2022/8/15 .
 **/
@Data
@Builder
public class RoomMicInfo extends BaseBean {

    private Long id;
    /**
     * 房间id
     */
    private Long roomId;
    /**
     * 麦位序号
     */
    private Integer orderNum;
    /**
     * 麦克风状态(0-空闲1-非空闲)
     *
     */
    private Integer micStatus;
    /**
     * 麦位是否锁定
     */
    private Integer whetherLock;
}
