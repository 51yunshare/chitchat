package com.chitchat.portal.service.room.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.common.base.CodeMsg;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.base.UserDto;
import com.chitchat.common.constant.NotifyConstant;
import com.chitchat.common.constant.SystemConstants;
import com.chitchat.common.enumerate.EnumBusinessType;
import com.chitchat.common.enumerate.EnumYesOrNo;
import com.chitchat.common.exception.ChitchatException;
import com.chitchat.common.redis.BusinessNoGenerator;
import com.chitchat.common.redis.RedisTemplateStringUtil;
import com.chitchat.common.util.BeanUtils;
import com.chitchat.common.web.jwt.UserUtils;
import com.chitchat.portal.bean.room.RoomGameAccountLink;
import com.chitchat.portal.bean.room.RoomGameInfo;
import com.chitchat.portal.dao.room.RoomGameInfoDaoI;
import com.chitchat.portal.dto.room.RoomGameAddRequestDTO;
import com.chitchat.portal.dto.room.RoomGameQuitRequestDTO;
import com.chitchat.portal.dto.room.RoomGameRequestDTO;
import com.chitchat.portal.enumerate.EnumRoomUserRole;
import com.chitchat.portal.redis.NotifyMessage;
import com.chitchat.portal.service.room.RoomGameAccountLinkServiceI;
import com.chitchat.portal.service.room.RoomGameInfoServiceI;
import com.chitchat.portal.service.zego.ZegoServiceI;
import com.chitchat.portal.vo.account.AccountBaseInfoVO;
import com.chitchat.portal.vo.room.RoomGameInfoVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * 游戏房间
 * <p>
 * Created by Js on 2022/10/29 .
 **/
@Service
public class RoomGameInfoServiceImpl extends BaseServicesImpl<RoomGameInfo, RoomGameInfoDaoI> implements RoomGameInfoServiceI {

    @Resource
    private RoomGameAccountLinkServiceI roomGameAccountLinkServiceI;
    @Resource
    private ZegoServiceI zegoServiceI;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;
    @Resource
    private RedisTemplateStringUtil redisTemplateStringUtil;
    @Resource
    private BusinessNoGenerator businessNoGenerator;

    /**
     * 参加游戏
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public synchronized RoomGameInfoVO join(RoomGameRequestDTO dto) {
        UserDto userDto = UserUtils.getUser();
        //根据不同游戏类型操作不一样
        switch (dto.getEnumGameType()) {
            case BALL_GAME://球赛轮盘
                break;
            default://骰子、老虎机
                //获取对应游戏类型的房间信息
                RoomGameInfo roomGameInfo = baseDaoT.getFreeGameInfo(dto);
                if (roomGameInfo == null) {
                    //todo 自动创建房间并加入到房间-房间号redis生成
                    return this.insertAddGameRoom(userDto, RoomGameInfo.builder()
                            .roomNo(businessNoGenerator.generate(EnumBusinessType.GAME_ROOM))
                            .gameType(dto.getGameType())
                            .gameUserNum(dto.getGameUserNum())
                            .build());
                }
                //加入游戏房间
                boolean joinSuccess = joinGameRoom(userDto, roomGameInfo);
                //加入之后重新查询房间信息
                RoomGameInfo roomGamePO = baseDaoT.getByRoomNo(roomGameInfo.getRoomNo());
                RoomGameInfoVO roomGameInfoVO = BeanUtils.copyProperties(roomGamePO, RoomGameInfoVO.class);
                //获取房间用户信息
                List<AccountBaseInfoVO> accountList = roomGameAccountLinkServiceI.getGameAccountListByRoomId(roomGameInfo.getId(), null);
                roomGameInfoVO.setAccountList(accountList);
                //下发游戏规则
                /*CompletableFuture.runAsync(()->{
                    String msg = new JSONObject().fluentPut("gameRule", "游戏规则").toJSONString();
                    zegoServiceI.doSendCustomCommand(SystemConstants.SERVER_USER_ID, roomGameInfo.getRoomNo(), msg,null);
                }, threadPoolExecutor);*/
                //todo 下发房间消息
                if (joinSuccess) {
                    this.sendGameRoomMsg(roomGameInfoVO.getRoomNo(), "in",
                            accountList.stream().filter(account -> account.getId().longValue() == userDto.getId().longValue()).collect(Collectors.toList()).get(0));
                }
                return roomGameInfoVO;
        }
        return null;
    }

    /**
     * 下发房间消息
     *
     * @param roomNo
     * @param inOrOutGameType
     * @param accountList
     */
    private void sendGameRoomMsg(String roomNo, String inOrOutGameType, AccountBaseInfoVO accountList) {
        redisTemplateStringUtil.send(NotifyConstant.REDIS_CHANNEL, JSON.toJSONString(NotifyMessage.builder()
                .fromUserId(SystemConstants.SERVER_USER_ID)
                .targetId(roomNo)
                .content(new JSONObject()
                        .fluentPut("inOrOutGameType", inOrOutGameType)
                        .fluentPut("account", accountList)
                        .fluentPut("roomNo", roomNo))
                .build()));
    }

    /**
     * 创建游戏
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public RoomGameInfoVO addGameRoom(RoomGameAddRequestDTO dto) {
        UserDto userDto = UserUtils.getUser();
        //判断游戏房间号是否唯一
        RoomGameInfo roomGamePO = baseDaoT.getByRoomNo(dto.getRoomNo());
        if (roomGamePO != null) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, "Duplicate room number !");
        }
        RoomGameInfo roomGameInfo = BeanUtils.copyProperties(dto, RoomGameInfo.class);
        RoomGameInfoVO roomGameInfoVO = this.insertAddGameRoom(userDto, roomGameInfo);
        return roomGameInfoVO;
    }

    /**
     * 新增游戏房间
     *
     * @param userDto
     * @param roomGameInfo
     * @return
     */
    private RoomGameInfoVO insertAddGameRoom(UserDto userDto, RoomGameInfo roomGameInfo) {
        roomGameInfo.setRoomUserNum(0);
        roomGameInfo.setLimitUserNum(roomGameInfo.getGameUserNum());
        roomGameInfo.setCreator(userDto.getId().toString());
        baseDaoT.insert(roomGameInfo);
        //新增关联关系
        joinGameRoom(userDto, roomGameInfo);
        RoomGameInfoVO roomGameInfoVO = BeanUtils.copyProperties(roomGameInfo, RoomGameInfoVO.class);
        //获取房间用户信息
        List<AccountBaseInfoVO> accountList = roomGameAccountLinkServiceI.getGameAccountListByRoomId(roomGameInfo.getId(), null);
        roomGameInfoVO.setAccountList(accountList);
        return roomGameInfoVO;
    }

    /**
     * 加入游戏
     *
     * @param userDto
     * @param roomGameInfo
     */
    private boolean joinGameRoom(UserDto userDto, RoomGameInfo roomGameInfo) {
        //判断是否已经进入游戏房间
        RoomGameAccountLink gameAccountLink = roomGameAccountLinkServiceI.getByRoomIdAndAccountId(roomGameInfo.getId(), userDto.getId());
        if (gameAccountLink == null) {
            roomGameAccountLinkServiceI.insert(RoomGameAccountLink.builder()
                    .accountId(userDto.getId())
                    .roomId(roomGameInfo.getId())
                    .roomNo(roomGameInfo.getRoomNo())
                    .loginTime(DateUtil.date())
                    .nickName(userDto.getNickName())
                    .userRole(EnumRoomUserRole.主播.getIndex())
                    .build());
            //房间人数+1
            baseDaoT.updateByPrimaryKeySelective(RoomGameInfo.builder()
                    .id(roomGameInfo.getId())
                    .roomUserNum(roomGameInfo.getRoomUserNum() + 1).build());
            return true;
        }
        return false;
    }

    /**
     * 退出游戏
     *
     * @param dto
     */
    @Override
    @Transactional
    public void doQuitGame(RoomGameQuitRequestDTO dto) {
        UserDto userDto = UserUtils.getUser();
        RoomGameInfo roomGameInfoPO = baseDaoT.getByRoomNo(dto.getRoomNo());
        if (roomGameInfoPO == null){
            throw new ChitchatException(CodeMsg.GAME_ROOM_NOT_EXISTS);
        }
        //移除游戏房间关联关系
        RoomGameAccountLink gameAccountLink = roomGameAccountLinkServiceI.getByRoomIdAndAccountId(roomGameInfoPO.getId(), userDto.getId());
        //获取当前用户信息
        List<AccountBaseInfoVO> accountBaseInfoVOList = roomGameAccountLinkServiceI.getGameAccountListByRoomId(roomGameInfoPO.getId(), userDto.getId());
        if (gameAccountLink != null) {
            //假删-用于查询用户最近游戏记录
            RoomGameAccountLink gameAccountLinkOld = RoomGameAccountLink.builder()
                    .id(gameAccountLink.getId())
                    .logoutTime(DateUtil.date())
                    .build();
            gameAccountLinkOld.setDeleted(EnumYesOrNo.是.getIndex());
            roomGameAccountLinkServiceI.updateByPrimaryKeySelective(gameAccountLinkOld);
            //房间人数-1
            baseDaoT.updateByPrimaryKeySelective(RoomGameInfo.builder()
                    .id(roomGameInfoPO.getId())
                    .roomUserNum(roomGameInfoPO.getRoomUserNum().intValue() <= 1 ? 0 : roomGameInfoPO.getRoomUserNum() - 1).build());
            //最后一个人退出房间-销毁房间
            if (roomGameInfoPO.getRoomUserNum().intValue() - 1 <= 0){
                RoomGameInfo roomGameInfo = RoomGameInfo.builder()
                        .id(roomGameInfoPO.getId())
                        .roomNo(roomGameInfoPO.getRoomNo() + "_" + roomGameInfoPO.getId())
                        .destroyTime(DateUtil.date())
                        .build();
                roomGameInfo.setDeleted(1);
                baseDaoT.updateByPrimaryKeySelective(roomGameInfo);
            }
            //todo 房间下发消息
            this.sendGameRoomMsg(roomGameInfoPO.getRoomNo(), "out", accountBaseInfoVOList.get(0));
        }
    }

    /**
     * 获取游戏规则
     *
     * @return
     */
    @Override
    public ResultTemplate getGameRule() {
        return null;
    }

    /**
     * 获取游戏房间信息
     *
     * @param roomNo
     * @return
     */
    @Override
    public RoomGameInfoVO getGameRoomDetail(String roomNo) {
        //获取指定游戏房间信息
        if (StrUtil.isNotBlank(roomNo)) {
            RoomGameInfo roomGameInfoPO = baseDaoT.getByRoomNo(roomNo);
            if (roomGameInfoPO == null) {
                throw new ChitchatException(CodeMsg.GAME_ROOM_NOT_EXISTS);
            }
            RoomGameInfoVO roomGameInfoVO = BeanUtils.copyProperties(roomGameInfoPO, RoomGameInfoVO.class);
            //获取房间用户信息
            List<AccountBaseInfoVO> accountList = roomGameAccountLinkServiceI.getGameAccountListByRoomId(roomGameInfoPO.getId(), null);
            roomGameInfoVO.setAccountList(accountList);
            return roomGameInfoVO;
        }
        //获取当前用户所在游戏房间
        UserDto userDto = UserUtils.getUser();
        RoomGameAccountLink gameAccountLink = roomGameAccountLinkServiceI.getByRoomIdAndAccountId(null, userDto.getId());
        if (gameAccountLink != null){
            RoomGameInfo roomGameInfo = baseDaoT.getById(gameAccountLink.getRoomId());
            if (roomGameInfo != null && roomGameInfo.getDeleted().intValue() != 1){
                RoomGameInfoVO roomGameInfoVO = BeanUtils.copyProperties(roomGameInfo, RoomGameInfoVO.class);
                //获取房间用户信息
                List<AccountBaseInfoVO> accountList = roomGameAccountLinkServiceI.getGameAccountListByRoomId(roomGameInfo.getId(), null);
                roomGameInfoVO.setAccountList(accountList);
                return roomGameInfoVO;
            }
        }
        return null;
    }
}
