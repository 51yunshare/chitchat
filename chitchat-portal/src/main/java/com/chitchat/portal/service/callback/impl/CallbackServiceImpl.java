package com.chitchat.portal.service.callback.impl;

import cn.hutool.core.date.DateUtil;
import com.chitchat.portal.bean.account.AccountInfo;
import com.chitchat.portal.bean.room.RoomAccountLink;
import com.chitchat.portal.bean.room.RoomInfo;
import com.chitchat.portal.dto.room.RoomLoginCallbackDTO;
import com.chitchat.portal.dto.room.RoomLogoutCallbackDTO;
import com.chitchat.portal.service.account.AccountInfoServiceI;
import com.chitchat.portal.service.callback.CallbackServiceI;
import com.chitchat.portal.service.room.RoomAccountLinkServiceI;
import com.chitchat.portal.service.room.RoomServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 回调serviceImpl
 *
 * Created by Js on 2022/8/23.
 **/
@Service
public class CallbackServiceImpl implements CallbackServiceI {

    @Autowired
    private RoomServiceI roomServiceI;
    @Autowired
    private RoomAccountLinkServiceI roomAccountLinkServiceI;
    @Autowired
    private AccountInfoServiceI accountInfoServiceI;

    /**
     * 登录房间回调-添加房间信息和用户信息
     *
     * @param dto
     */
    @Override
    public void loginRoomCallback(RoomLoginCallbackDTO dto) {
        //1.判断房间是否创建
        RoomInfo roomInfo = roomServiceI.getById(Long.valueOf(dto.getRoomId()));
        if(roomInfo == null){
            RoomInfo newRoom = new RoomInfo()
                    .setRoomName(dto.getRoomName())
                    .setRoomSeq(dto.getRoomSeq())
                    .setUserUpdateSeq(dto.getUserUpdateSeq());
            //用户登录时间当成房间创建时间
            newRoom.setCreatedTime(DateUtil.date(dto.getLoginTime()));
            roomServiceI.insert(newRoom);
            //新增用户房间关联表
            this.insertRoomAccountLink(dto, newRoom.getId());
            return;
        }
        //2.判断roomSeq是否相等
        if (!roomInfo.getRoomSeq().equals(dto.getRoomSeq())){
            //不相等判断创建时间
            if (roomInfo.getCreatedTime().getTime() > dto.getLoginTime()){
                //如果即构创建时间小于数据库时间，就修改数据库创建时间
                RoomInfo updateRoom = new RoomInfo();
                updateRoom.setId(roomInfo.getId());
                updateRoom.setCreatedTime(DateUtil.date(dto.getLoginTime()));
                updateRoom.setUserUpdateSeq(dto.getUserUpdateSeq());
                roomServiceI.updateByPrimaryKeySelective(updateRoom);
                //新增用户房间关联表
                this.insertRoomAccountLink(dto, roomInfo.getId());
                return;
            }
            //乱序不处理
            return;
        }
        //3.判断UserUpdateSeq
        if (dto.getUserUpdateSeq().intValue() > roomInfo.getUserUpdateSeq().intValue()){
            //乱序不处理
            return;
        }
        //用户房间关联表
        this.insertRoomAccountLink(dto, roomInfo.getId());
    }

    /**
     * 新增用户房间关联
     *
     * @param dto
     * @param roomId
     */
    private void insertRoomAccountLink(RoomLoginCallbackDTO dto, Long roomId) {
        //判断用户
        AccountInfo accountInfo = accountInfoServiceI.getById(Long.valueOf(dto.getUserAccount()), "会员信息");
        //新增关联
        roomAccountLinkServiceI.insert(RoomAccountLink.builder()
                .roomId(roomId)
//                .userUpdateSeq(dto.getUserUpdateSeq())
                .accountId(accountInfo.getId())
                .nickName(accountInfo.getNickName())
                .loginTime(DateUtil.date(dto.getLoginTime()))
                .userRole(dto.getUserRole())
                .build());
    }

    /**
     * 登出房间回调
     *
     * @param dto
     */
    @Override
    public void logoutRoomCallback(RoomLogoutCallbackDTO dto) {
        //1.判断房间是否创建
        RoomInfo roomInfo = roomServiceI.getById(Long.valueOf(dto.getRoomId()));
        if (roomInfo == null){
            //不处理回调
            return;
        }
        //2.判断roomSeq是否相等
        if (!roomInfo.getRoomSeq().equals(dto.getRoomSeq())){
            //乱序不处理
            return;
        }
        //3.判断UserUpdateSeq
        if (dto.getUserUpdateSeq().intValue() > roomInfo.getUserUpdateSeq().intValue()){
            //4.判断用户是否存在（房间用户关联是否存在）
            RoomAccountLink accountInfo = roomAccountLinkServiceI.getByRoomIdAndAccountId(roomInfo.getId(), Long.valueOf(dto.getUserAccount()));
            if (accountInfo != null && accountInfo.getSessionId().equals(dto.getSessionId())){
                //移除房间用户
                roomAccountLinkServiceI.deleteByLongId(accountInfo.getId());
            }
        }
        //不做处理

    }
}
