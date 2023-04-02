package com.chitchat.portal.bean.room;

import com.chitchat.common.base.BaseBean;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 房间用户麦位信息
 *
 * Created by Js on 2022/8/15 .
 **/
@Data
@Accessors(chain = true)
public class RoomMicAccountLink extends BaseBean {

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
     * 麦位id
     */
    private Long micId;
    /**
     * 麦位状态(是否打开麦克风)
     */
    private Integer accountMicStatus;
    /**
     * 麦位是否被静音
     */
    private Integer muteMicStatus;
    /**
     * 下麦时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date offMicTime;
    /**
     * 下麦操作人
     */
    private Long offMicOperatorId;
    private String offMicOperatorName;
}
