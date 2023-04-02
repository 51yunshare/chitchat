package com.chitchat.portal.bean.room;

import com.chitchat.common.base.BaseBean;
import com.chitchat.common.util.EnumUtil;
import com.chitchat.portal.enumerate.EnumKickOutDuration;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 房间踢出用户表
 *
 * Created by Js on 2022/9/7 .
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomKickOutAccountLink extends BaseBean{

    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private Long id;

    /**
     * 房间id
     */
    private Long roomId;

    /**
     * 用户id
     */
    private Long accountId;
    /**
     * 封禁时长(1 hour 1 day forever)
     */
    private Integer kickOutDuration;
    private EnumKickOutDuration enumKickOutDuration;

    public void setKickOutDuration(Integer kickOutDuration) {
        this.kickOutDuration = kickOutDuration;
        this.enumKickOutDuration = kickOutDuration == null ? null : EnumUtil.valueOf(EnumKickOutDuration.class, kickOutDuration);
    }
    //操作人
    private Long createdUserId;
    private String createdUserName;
    /**
     * 解禁时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date kickOutEndTime;
}
