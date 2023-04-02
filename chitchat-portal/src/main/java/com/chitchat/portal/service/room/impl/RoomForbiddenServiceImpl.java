package com.chitchat.portal.service.room.impl;

import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.common.base.CodeMsg;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.base.UserDto;
import com.chitchat.common.exception.ChitchatException;
import com.chitchat.common.util.BeanUtils;
import com.chitchat.common.web.jwt.UserUtils;
import com.chitchat.portal.bean.room.RoomForbidden;
import com.chitchat.portal.dao.room.RoomForbiddenDaoI;
import com.chitchat.portal.dto.room.RoomForbiddenAddRequestDTO;
import com.chitchat.portal.dto.room.RoomForbiddenPageListRequest;
import com.chitchat.portal.service.account.AccountInfoServiceI;
import com.chitchat.portal.service.room.RoomForbiddenServiceI;
import com.chitchat.portal.service.room.RoomServiceI;
import com.chitchat.portal.vo.room.RoomForbiddenVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 房间禁止用户表 Service实现
 *
 * Created by Js on 2022/8/26 .
 */
@Service
@Slf4j
public class RoomForbiddenServiceImpl extends BaseServicesImpl<RoomForbidden, RoomForbiddenDaoI> implements RoomForbiddenServiceI {

    @Autowired
    private RoomServiceI roomServiceI;
    @Autowired
    private AccountInfoServiceI accountInfoServiceI;

    /**
     * 获取禁止用户列表
     *
     * @param pageListRequest
     * @return
     */
    @Override
    public ResultTemplate getList(RoomForbiddenPageListRequest pageListRequest) {
        PageHelper.startPage(pageListRequest.getCp(), pageListRequest.getRows());
        PageInfo<RoomForbiddenVO> data = new PageInfo(baseDaoT.list(pageListRequest));
        return new ResultTemplate(data, data.getList());
    }

    /**
     * 房间禁用户
     *
     * @param roomDTO
     */
    @Override
    @Transactional
    public void doForbidUser(RoomForbiddenAddRequestDTO roomDTO) {
        //查询房间和用户信息
        roomServiceI.getById(roomDTO.getRoomId(), "房间信息");
        accountInfoServiceI.getById(roomDTO.getAccountId(), "用户信息");
        RoomForbidden forbiddenUser = BeanUtils.copyProperties(roomDTO, RoomForbidden.class);
        UserDto userDto = UserUtils.getUser();
        forbiddenUser.setCreatedUserId(userDto.getId());
        forbiddenUser.setCreatedUserName(userDto.getNickName());
        baseDaoT.insert(forbiddenUser);
    }

    /**
     * 房间移除被禁用户
     *
     * @param roomId
     * @param id
     */
    @Override
    @Transactional
    public void doRemoveForbidUser(Long roomId, Long id) {
        UserDto userDto = UserUtils.getUser();
        RoomForbidden forbidden = baseDaoT.getById(id);
        if (forbidden == null || roomId.longValue() != forbidden.getRoomId().longValue()){
            throw new ChitchatException(CodeMsg.NULL_ERROR, "用户被禁不存在！");
        }
        baseDaoT.deleteByLongId(id);
        log.debug("房间移除被禁用户记录，参数：{}，操作人：【{}-{}】", id, userDto.getId(), userDto.getNickName());
    }

    /**
     * 查询禁止房间用户信息通过用户Id和房间id
     *
     * @param roomId
     * @param accountId
     * @return
     */
    @Override
    public RoomForbidden getByRoomIdAndAccountId(Long roomId, Long accountId) {
        return baseDaoT.getByRoomIdAndAccountId(roomId, accountId);
    }
}
