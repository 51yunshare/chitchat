package com.chitchat.portal.dto.room;

import com.chitchat.common.util.EnumUtil;
import com.chitchat.portal.enumerate.EnumUserLogoutReason;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 登出房间回调
 *
 * Created by Js on 2022/8/3 .
 **/
@Data
@ApiModel(value = "用户登出房间回调请求体")
public class RoomLogoutCallbackDTO implements Serializable {
    //	String 用户账号 ID。
    private String userAccount;
    //	String 用户昵称。
    private String userNickname;
    //Int	用户角色。1：主播。2：观众。
    private Integer userRole;
    //房间用户列表变更 seq，用户登录或者退出都会递增 1
    private Integer userUpdateSeq;
    //	String 用户会话 ID。
    private String sessionId;
    //	String房间 ID。
    private String roomId;
    // String 房间生命周期唯一标识。例如用户两次登录 room_id 都为 r1，但是第一次登录的房间 room_seq 为 123，
    // 第二次登录的房间 room_seq 为 234。此时 room_seq（123）房间销毁后才能创建 room_seq（234）房间，虽然 room_id 一样，但是为不同房间。
    private String roomSeq;
    //	Int 用户登录房间时间戳，单位：毫秒。
    private long logoutTime;
    //Int	服务器当前时间，Uinx 时间戳。
    private long timestamp;
    //String	随机数。
    private String nonce;
    //String	检验串见检验说明。
    private String signature;
    //退出原因
    //0：正常退出。 1：心跳超时退出。 2：用户断线退出。
    //3：调用后台接口被踢出。 4：token 过期退出。
    private Integer reason;
    private EnumUserLogoutReason enumUserLogoutReason;
    //String	APP 的唯一标识。
    private String appid;
    //String	回调事件，此回调返回值为 room_login。
    private String event;

    public void setReason(Integer reason) {
        this.reason = reason;
        this.enumUserLogoutReason = reason == null ? null : EnumUtil.valueOf(EnumUserLogoutReason.class, reason);
    }
}
