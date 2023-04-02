package com.chitchat.portal.bean.room;

import com.chitchat.common.base.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 房间麦位邀请信息
 *
 * Created by Js on 2022/8/15 .
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomMicInviteInfo extends BaseBean {

    private Long id;
    /**
     * 房间id
     */
    private Long roomId;
    /**
     * 房间用户关联id
     */
    private Long roomAccountLinkId;
    /**
     * 账号id
     *
     */
    private Long accountId;
    /**
     * 操作人
     */
    private String creator;
    /**
     * 操作状态(1-已操作 0-未操作)
     */
    private Integer operateStatus;
    /**
     * 操作时间
     */
    private Date operateTime;
}
