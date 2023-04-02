package com.chitchat.portal.bean.room;

import com.chitchat.common.base.BaseBean;
import com.chitchat.common.util.EnumUtil;
import com.chitchat.portal.enumerate.EnumRoomUserRole;
import com.chitchat.portal.enumerate.EnumUserLogoutReason;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by Js on 2022/8/3 .
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomAccountLink extends BaseBean {

    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private Long id;
    private Long roomId;
    /**
     * 用户id
     */
    private Long accountId;

    /**
     * 用户名
     */
    private String nickName;
    //用户会话id
    private String sessionId;
    /**
     * 用户角色
     */
    private Integer userRole;
    private EnumRoomUserRole enumRoomUserRole;
    /**
     * 用户退出房间理由
     */
    private Integer reason;
    private EnumUserLogoutReason enumUserLogoutReason;

    /**
     * 登录房间时间
     */
    private Date loginTime;
    /**
     * 退出房间时间
     *
     */
    private Date logoutTime;

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
        this.enumRoomUserRole = userRole == null ? null : EnumUtil.valueOf(EnumRoomUserRole.class, userRole);
    }

    public void setReason(Integer reason) {
        this.reason = reason;
        this.enumUserLogoutReason = reason == null ? null : EnumUtil.valueOf(EnumUserLogoutReason.class, reason);
    }
}
