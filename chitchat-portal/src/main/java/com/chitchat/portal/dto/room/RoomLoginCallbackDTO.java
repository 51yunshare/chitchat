package com.chitchat.portal.dto.room;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Js on 2022/8/3 .
 **/
@Data
@ApiModel("用户登录房间回调请求体")
public class RoomLoginCallbackDTO implements Serializable {
    //	String 用户账号 ID。
    private String userAccount;
    //	String 用户昵称。
    private String userNickname;
    //	String 用户会话 ID。
    private String sessionId;
    //	String房间 ID。
    private String roomId;
    // String 房间生命周期唯一标识。例如用户两次登录 room_id 都为 r1，但是第一次登录的房间 room_seq 为 123，
    // 第二次登录的房间 room_seq 为 234。此时 room_seq（123）房间销毁后才能创建 room_seq（234）房间，虽然 room_id 一样，但是为不同房间。
    private String roomSeq;
    //	String 房间名。
    private String roomName;
    //	Int 用户登录房间时间戳，单位：毫秒。
    private long loginTime;
    //Int	服务器当前时间，Uinx 时间戳。
    private long timestamp;
    //String	随机数。
    private String nonce;
    //String	检验串见检验说明。
    private String signature;
    //String	APP 的唯一标识。
    private String appid;
    //String	回调事件，此回调返回值为 room_login。
    private String event;
    //Int	用户角色。1：主播。2：观众。
    private Integer userRole;
    //房间用户列表变更 seq，用户登录或者退出都会递增 1
    private Integer userUpdateSeq;

}
