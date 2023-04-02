package com.chitchat.portal.service.room.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.common.base.CodeMsg;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.base.UserDto;
import com.chitchat.common.enumerate.EnumYesOrNo;
import com.chitchat.common.exception.ChitchatException;
import com.chitchat.common.redis.RedisKey;
import com.chitchat.common.redis.RedisTemplateUtil;
import com.chitchat.common.util.BeanUtils;
import com.chitchat.common.util.EnumUtil;
import com.chitchat.common.util.JSONObjectUtil;
import com.chitchat.common.web.jwt.UserUtils;
import com.chitchat.portal.bean.account.AccountGiftLink;
import com.chitchat.portal.bean.account.AccountInfo;
import com.chitchat.portal.bean.base.GiftInfo;
import com.chitchat.portal.bean.room.*;
import com.chitchat.portal.dao.room.RoomAccountLinkDaoI;
import com.chitchat.portal.dto.base.GiftSendRequestDTO;
import com.chitchat.portal.dto.room.*;
import com.chitchat.portal.enumerate.*;
import com.chitchat.portal.service.account.AccountExpFlowLogServiceI;
import com.chitchat.portal.service.account.AccountGiftLinkServiceI;
import com.chitchat.portal.service.account.AccountInfoServiceI;
import com.chitchat.portal.service.account.BalanceInfoServiceI;
import com.chitchat.portal.service.base.GiftInfoServiceI;
import com.chitchat.portal.service.room.*;
import com.chitchat.portal.vo.room.RoomAccountLinkVO;
import com.chitchat.portal.vo.room.RoomContributionRankVO;
import com.chitchat.portal.vo.room.RoomUserBaseInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * Created by Js on 2022/8/14.
 **/
@Service
@Slf4j
public class RoomAccountLinkServiceImpl extends BaseServicesImpl<RoomAccountLink, RoomAccountLinkDaoI> implements RoomAccountLinkServiceI {

    @Autowired
    private RoomServiceI roomServiceI;
    @Autowired
    private AccountInfoServiceI accountInfoServiceI;
    @Autowired
    private RoomForbiddenServiceI roomForbiddenServiceI;
    @Autowired
    private RoomKickOutAccountLinkServiceI kickOutAccountLinkServiceI;
    @Autowired
    private RoomMicInfoServiceI roomMicInfoServiceI;
    @Autowired
    private RoomMicAccountLinkServiceI roomMicAccountLinkServiceI;
    @Autowired
    private RedisTemplateUtil redisTemplateUtil;
    @Autowired
    private AccountGiftLinkServiceI accountGiftLinkServiceI;
    @Autowired
    private GiftInfoServiceI giftInfoServiceI;
    @Autowired
    private RoomKickOutAccountLinkServiceI roomKickOutAccountLinkServiceI;
    @Autowired
    private BalanceInfoServiceI balanceInfoServiceI;
    @Autowired
    private RoomMicStreamLinkServiceI roomMicStreamLinkServiceI;
    @Autowired
    private RoomMicAccountRecordServiceI roomMicAccountRecordServiceI;
    @Autowired
    private RoomAccountRecordServiceI roomAccountRecordServiceI;
    @Autowired
    private RoomMicInviteInfoServiceI roomMicInviteInfoServiceI;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    private AccountExpFlowLogServiceI accountExpFlowLogServiceI;

    /**
     * 查询房间在线用户数量
     *
     * @param roomId
     * @return
     */
    @Override
    public int getUserNumByRoomId(Long roomId) {
        return baseDaoT.getUserNumByRoomId(roomId);
    }

    /**
     * 通过房间id和用户id查询关联关系
     *
     * @param roomId
     * @param accountId
     * @return
     */
    @Override
    public RoomAccountLink getByRoomIdAndAccountId(Long roomId, Long accountId) {
        return baseDaoT.getByRoomIdAndAccountId(roomId, accountId);
    }

    /**
     * 设置管理员
     *
     * @param dto
     */
    @Override
    @Transactional
    public void doSetRole(RoomUserRoleRequestDTO dto) {
        UserDto userDto = UserUtils.getUser();
        //判断房间信息和用户操作权限
        this.checkRoomAndUser(userDto.getId(), dto.getRoomId());
        //判断用户是否在房间并修改角色
        this.checkAccountAndUpdateRole(dto.getRoomId(), dto.getAccountId(), EnumRoomUserRole.管理员);
    }

    /**
     * 取消管理员
     *
     * @param dto
     */
    @Override
    @Transactional
    public void doCancelRole(RoomUserRoleRequestDTO dto) {
        UserDto userDto = UserUtils.getUser();
        //判断房间信息和用户操作权限
        this.checkRoomAndUser(userDto.getId(), dto.getRoomId());
        //判断用户是否在房间并修改角色
        this.checkAccountAndUpdateRole(dto.getRoomId(), dto.getAccountId(), EnumRoomUserRole.观众);
    }

    /**
     * 判断用户是否在房间并修改角色
     *
     * @param roomId
     * @param accountId
     * @param userRole
     */
    private void checkAccountAndUpdateRole(Long roomId, Long accountId, EnumRoomUserRole userRole) {
        RoomAccountLink roomAccountLink = this.checkRoomUserExist(roomId, accountId);
        baseDaoT.updateByPrimaryKeySelective(RoomAccountLink.builder()
                .id(roomAccountLink.getId())
                .userRole(userRole.getIndex())
                .build());
    }

    /**
     * 进入房间
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public RoomInfo entryRoom(AccountEntryRoomRequestDTO dto) {
        UserDto userDto = UserUtils.getUser();
        RoomInfo roomInfo = roomServiceI.getById(dto.getRoomId(), "房间信息");
        if (userDto.getId().longValue() == roomInfo.getCreatedUserId().longValue()) {
            return roomInfo;
        }
        //判断房间是否上锁
        if (roomInfo.getWhetherLock().intValue() == EnumYesOrNo.是.getIndex()) {
            if (StrUtil.isBlank(dto.getRoomPwd()) || !dto.getRoomPwd().equals(roomInfo.getRoomPwd())) {
                throw new ChitchatException(CodeMsg.BIND_ERROR, "The room is locked, please enter the correct room password !");
            }
        }
        //判断当前用户是否被禁止
        RoomForbidden roomForbidden = roomForbiddenServiceI.getByRoomIdAndAccountId(dto.getRoomId(), userDto.getId());
        if (roomForbidden != null) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, userDto.getNickName() + " No admittance!");
        }
        //判断是否被剔除房间用户
        RoomKickOutAccountLink kickOutAccountLink = kickOutAccountLinkServiceI.getByRoomIdAndAccountId(dto.getRoomId(), userDto.getId());
        if (kickOutAccountLink != null) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, userDto.getNickName() + " No admittance " + kickOutAccountLink.getEnumKickOutDuration().getName());
        }
        //判断房间人数上限
        if (roomInfo.getLimitUserNum().intValue() <= roomInfo.getRoomUserNum().intValue()) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, "Room number is full");
        }
        //查询原来的房间用户关联
        RoomAccountLink accountLink = baseDaoT.getByRoomIdAndAccountId(dto.getRoomId(), userDto.getId());
        if (accountLink == null) {
            //新增关联
            baseDaoT.insert(RoomAccountLink.builder()
                    .roomId(dto.getRoomId())
                    .accountId(userDto.getId())
                    .nickName(userDto.getNickName())
                    .loginTime(DateUtil.date())
                    .userRole(EnumRoomUserRole.观众.getIndex())
                    .build());
            //房间人数和热度
            this.roomUserNumAndHotNum(dto.getRoomId());
        } else if (accountLink.getDeleted().intValue() == 1) {//已经在房间里面，不需要做其他操作
            accountLink.setLoginTime(DateUtil.date());
            accountLink.setDeleted(EnumYesOrNo.否.getIndex());
            baseDaoT.updateByPrimaryKeySelective(accountLink);
            //房间人数和热度
            this.roomUserNumAndHotNum(dto.getRoomId());
        }
        return roomServiceI.getById(dto.getRoomId());
    }

    /**
     * 房间人数和热度
     *
     * @param roomId
     */
    private void roomUserNumAndHotNum(Long roomId) {
        //todo 房间人数+1
        roomServiceI.updateRoomUserNum(roomId);
        redisTemplateUtil.incr(RedisKey.getRoomOnlineNum(roomId), 1);
        //todo 热度值
        Long hotNum = redisTemplateUtil.incr(RedisKey.getRoomHotNum(roomId));
        roomServiceI.updateByPrimaryKeySelective(new RoomInfo().setId(roomId).setHotNum(hotNum.intValue()));
    }

    /**
     * 获取房间热度值
     *
     * @param roomId
     * @return
     */
    private Integer getHotNum(Long roomId) {
        return redisTemplateUtil.get(RedisKey.getRoomHotNum(roomId), Integer.class) == null ? 0 : redisTemplateUtil.get(RedisKey.getRoomHotNum(roomId), Integer.class);
    }

    /**
     * 观众退出房间
     *
     * @param roomId
     * @return
     */
    @Override
    @Transactional
    public void quitRoom(Long roomId) {
        UserDto userDto = UserUtils.getUser();
        roomServiceI.getById(roomId, "房间信息");
        RoomAccountLink roomAccountLink = baseDaoT.getByRoomIdAndAccountId(roomId, userDto.getId());
        //退出房间业务：已经退出的不需要处理业务
        if (roomAccountLink != null && roomAccountLink.getDeleted().intValue() == 0) {
            //用户退出房间
            this.accountQuitRoom(roomAccountLink, EnumUserLogoutReason.正常退出, userDto);
            //todo 房间人数-1
            roomServiceI.updateRoomUserNum(roomId);
            /*if (redisTemplateUtil.hasKey(RedisKey.getRoomOnlineNum(roomId))) {
                redisTemplateUtil.decr(RedisKey.getRoomOnlineNum(roomId), 1);
            }*/
            redisTemplateUtil.decrRejectNegative(RedisKey.getRoomOnlineNum(roomId), 1);
            //房间热度值下降
            this.updateRoomHotNumDecline(roomId);
            //如果房主退出房间，删除房间信息
            if (roomAccountLink.getUserRole().intValue() == EnumRoomUserRole.主播.getIndex()) {
                //销毁房间
                RoomInfo room = new RoomInfo().setId(roomId);
                room.setDeleted(EnumYesOrNo.是.getIndex());
                room.setDestroyTime(DateUtil.date());
                roomServiceI.updateByPrimaryKeySelective(room);
                log.info("销毁房间，参数：{}，操作人：【{}】", roomId, userDto);
                //处理房间用户信息
                CompletableFuture.runAsync(() -> {
                    List<RoomAccountLink> roomAccountLinks = baseDaoT.listRoomAccountLinkByRoomId(roomId);
                    roomAccountLinks.forEach(vo -> {
                        if (vo.getAccountId().intValue() != userDto.getId().intValue()) {
                            this.accountQuitRoom(vo, EnumUserLogoutReason.房主销毁房间, userDto);
                        }
                    });
                }, threadPoolExecutor);
            }
        }
    }

    /**
     * 用户退出房间
     *
     * @param roomAccountLink
     * @param logoutReason
     * @param operateUser
     */
    private void accountQuitRoom(RoomAccountLink roomAccountLink, EnumUserLogoutReason logoutReason, UserDto operateUser) {
        //用户退出房间
        RoomAccountLink accountLink = RoomAccountLink.builder().id(roomAccountLink.getId()).build();
        accountLink.setDeleted(EnumYesOrNo.是.getIndex());
        accountLink.setLogoutTime(DateUtil.date());
        baseDaoT.updateByPrimaryKeySelective(accountLink);
        //添加用户进出房间记录
        RoomAccountRecord roomAccountRecord = BeanUtils.copyProperties(roomAccountLink, RoomAccountRecord.class);
        roomAccountRecord.setId(null);
        roomAccountRecord.setLogoutTime(DateUtil.date());
        roomAccountRecord.setRoomAccountId(roomAccountLink.getId());
        roomAccountRecord.setReason(logoutReason.getIndex());
        roomAccountRecord.setOperatorId(operateUser.getId());
        roomAccountRecord.setOperatorName(operateUser.getNickName());
        //在房间总时长
        roomAccountRecord.setOnlineTime((int) DateUtil.between(roomAccountRecord.getLoginTime(), roomAccountRecord.getLogoutTime(), DateUnit.MINUTE));
        roomAccountRecordServiceI.insert(roomAccountRecord);
        //当前用户是否上麦,如果上麦删除上麦信息
        RoomMicAccountLink roomMicAccountLink = roomMicAccountLinkServiceI.getByRoomIdAndAccountId(roomAccountLink.getRoomId(), roomAccountLink.getAccountId());
        if (roomMicAccountLink != null) {
            //删除用户上麦信息(流信息)、回写麦位状态、新增用户上下麦记录
            this.insertAccountMicRecord(operateUser, roomMicAccountLink);
        }
        //todo 用户经验值刷新更新等级信息
        CompletableFuture.runAsync(() -> {
            //获取用户在线时长(只计算进入房间当天的时间，跨天按照当天12点算)
            int roomDuration = getRoomDuration(roomAccountLink);
            accountExpFlowLogServiceI.userMemberLevelExpHandler(roomAccountLink.getAccountId(), EnumLevelType.MEMBER,
                    EnumExpSourceType.ROOM_DURATION, roomDuration, DateUtil.formatDate(roomAccountLink.getLoginTime()));
        }, threadPoolExecutor);
    }

    /**
     * 获取用户在线时长
     *
     * @param roomAccountLink
     * @return
     */
    private int getRoomDuration(RoomAccountLink roomAccountLink) {
        Date loginTime = roomAccountLink.getLoginTime();
        Date currentDate = DateUtil.date();
        if (!DateUtil.formatDate(currentDate).equals(DateUtil.formatDate(loginTime))){//不是同一天
            return (int) DateUtil.between(loginTime, DateUtil.endOfDay(loginTime), DateUnit.MINUTE);
        }
        //同一天
        return (int) DateUtil.between(loginTime, currentDate, DateUnit.MINUTE);
    }

    /**
     * 用户上麦
     *
     * @param dto
     */
    @Override
    @Transactional
    public JSONObject accountUpMic(RoomUserUpMicRequestDTO dto) {
        final UserDto userDto = UserUtils.getUser();
        //房间判断
        RoomInfo roomInfo = roomServiceI.getById(dto.getRoomId(), "房间信息");
        //判断房间上麦方式，邀请制需要查询是否在邀请记录里面
        if (roomInfo.getMicWay().intValue() == EnumRoomMicWay.邀请.getIndex()) {
            //查询邀请记录
            RoomMicInviteInfo roomMicInviteInfo = roomMicInviteInfoServiceI.getByRoomAndAccountId(dto.getRoomId(), userDto.getId());
            if (roomMicInviteInfo == null) {
                throw new ChitchatException(CodeMsg.BIND_ERROR, "当前房间连麦方式是需要邀请的");
            }
        }
        //用户是否在房间
        RoomAccountLink roomAccountLink = this.checkRoomUserExist(dto.getRoomId(), userDto.getId());
        //麦位信息
        RoomMicInfo micInfo = roomMicInfoServiceI.getById(dto.getMicId());
        if (micInfo == null || micInfo.getRoomId().longValue() != dto.getRoomId().longValue()) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, "The room mic does not exist !");
        }
        if (micInfo.getMicStatus().intValue() == EnumYesOrNo.是.getIndex()) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, "The room mic is occupied !");
        }
        if (micInfo.getWhetherLock().intValue() == EnumYesOrNo.是.getIndex()) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, "The room mic is locked !");
        }
        //主麦位只能是房主或者管理员
        if (micInfo.getOrderNum().intValue() == 0 && roomAccountLink.getUserRole().intValue() == EnumRoomUserRole.观众.getIndex()) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, "The main mic only the owner or administrator");
        }
        //判断用户是否已经上麦
        checkUserIfMic(dto.getRoomId(), userDto.getId(), "The user has connect mic !");
        //上麦记录
        this.insertMicAccountLink(dto.getRoomId(), dto.getEnumMicStatus(), userDto.getId(), roomAccountLink.getId(), dto.getMicId());
        //修改麦位状态（非空闲状态）
        roomMicInfoServiceI.updateByPrimaryKeySelective(RoomMicInfo.builder()
                .id(dto.getMicId())
                .micStatus(EnumYesOrNo.是.getIndex())
                .build());
        //用户邀请上麦记录修改
        roomMicInviteInfoServiceI.updateInviteStatusByRoomIdAndAccountId(dto.getRoomId(), userDto.getId());
        //生成麦位流信息
        RoomMicStreamLink micStreamLink = new RoomMicStreamLink()
                .setRoomId(dto.getRoomId())
                .setMicId(dto.getMicId())
                .setAccountId(userDto.getId());
        roomMicStreamLinkServiceI.insert(micStreamLink);
        //todo 热度值
        Long hotNum = redisTemplateUtil.incr(RedisKey.getRoomHotNum(dto.getRoomId()));
        //热度值写入数据库
        roomServiceI.updateByPrimaryKeySelective(new RoomInfo().setId(dto.getRoomId()).setHotNum(hotNum.intValue()));
        return new JSONObject().fluentPut("micStreamId", micStreamLink.getId());
    }

    /**
     * 上麦记录
     *
     * @param roomId
     * @param userMicStatus
     * @param accountId
     * @param roomAccountLinkId
     * @param micId
     */
    private void insertMicAccountLink(Long roomId, EnumYesOrNo userMicStatus, Long accountId, Long roomAccountLinkId, Long micId) {
        roomMicAccountLinkServiceI.insert(new RoomMicAccountLink()
                .setAccountId(accountId)
                .setRoomAccountLinkId(roomAccountLinkId)
                .setRoomId(roomId)
                .setAccountMicStatus(userMicStatus == null ? EnumYesOrNo.是.getIndex() : userMicStatus.getIndex())
                .setMuteMicStatus(EnumYesOrNo.否.getIndex())
                .setMicId(micId));
    }

    /**
     * 用户下麦
     *
     * @param dto
     */
    @Override
    @Transactional
    public void accountOffMic(RoomUserOffMicRequestDTO dto) {
        UserDto userDto = UserUtils.getUser();
        roomServiceI.getById(dto.getRoomId(), "房间信息");
        //判断用户上麦信息
        RoomMicAccountLink roomMicAccountLink = this.checkAccountMicInfo(dto, userDto);
        //todo 删除用户上麦信息？
        /*RoomMicAccountLink micAccountLink = new RoomMicAccountLink()
                .setId(roomMicAccountLink.getId())
                .setOffMicTime(DateUtil.date())
                .setOffMicOperatorId(userDto.getId())
                .setOffMicOperatorName(userDto.getNickName());
        micAccountLink.setDeleted(1);
        roomMicAccountLinkServiceI.updateByPrimaryKeySelective(micAccountLink);*/
        //删除用户上麦信息(流信息)、回写麦位状态、新增用户上下麦记录
        this.insertAccountMicRecord(userDto, roomMicAccountLink);

        //房间热度值
        this.updateRoomHotNumDecline(dto.getRoomId());
    }

    /**
     * 判断用户上麦信息
     *
     * @param dto
     * @param userDto
     * @return
     */
    private RoomMicAccountLink checkAccountMicInfo(RoomUserOffMicRequestDTO dto, UserDto userDto) {
        RoomMicAccountLink roomMicAccountLink = roomMicAccountLinkServiceI.getByRoomIdAndAccountId(dto.getRoomId(), userDto.getId());
        if (roomMicAccountLink == null || roomMicAccountLink.getMicId().longValue() != dto.getMicId()) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, "没有上麦");
        }
        if (userDto.getId().longValue() != roomMicAccountLink.getAccountId().longValue()) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, "操作失败，没有权限");
        }
        return roomMicAccountLink;
    }

    /**
     * 用户闭麦(修改用户麦状态)
     *
     * @param dto
     */
    @Override
    @Transactional
    public void accountClosedMic(RoomUserOffMicRequestDTO dto) {
        UserDto userDto = UserUtils.getUser();
        //判断用户上麦信息
        RoomMicAccountLink roomMicAccountLink = this.checkAccountMicInfo(dto, userDto);
        roomMicAccountLinkServiceI.updateByPrimaryKeySelective(new RoomMicAccountLink()
                .setId(roomMicAccountLink.getId())
                .setAccountMicStatus(EnumYesOrNo.否.getIndex()));
    }

    /**
     * 用户开麦
     *
     * @param dto
     */
    @Override
    @Transactional
    public void accountOpenMic(RoomUserOffMicRequestDTO dto) {
        UserDto userDto = UserUtils.getUser();
        //判断用户上麦信息
        RoomMicAccountLink roomMicAccountLink = this.checkAccountMicInfo(dto, userDto);
        roomMicAccountLinkServiceI.updateByPrimaryKeySelective(new RoomMicAccountLink()
                .setId(roomMicAccountLink.getId())
                .setAccountMicStatus(EnumYesOrNo.是.getIndex()));
    }

    /**
     * 获取会员信息
     *
     * @param accountId
     * @param roomId
     * @return
     */
    @Override
    public ResultTemplate getAccountInfo(Long accountId, Long roomId) {
        UserDto userDto = UserUtils.getUser();
        //房间用户信息
        RoomUserBaseInfo roomUserBaseInfo = baseDaoT.getRoomUserInfoByRoomIdAndAccountId(roomId, accountId);
        if (roomUserBaseInfo == null) {
            throw new ChitchatException(CodeMsg.NULL_ERROR, "The room user does not exist !");
        }
        //房间贡献值
        Long roomContributionNum = redisTemplateUtil.get(RedisKey.getUserRoomContributionNum(accountId, roomId), Long.class);
        roomUserBaseInfo.setRoomContributionNum(roomContributionNum == null ? 0 : roomContributionNum);
        //房间收到贡献值
        Long roomReceivingContributionNum = redisTemplateUtil.get(RedisKey.getUserRoomReceivingContributionNum(accountId, roomId), Long.class);
        roomUserBaseInfo.setRoomReceivingContributionNum(roomReceivingContributionNum == null ? 0 : roomReceivingContributionNum);
        //关注状态
        Boolean isFollow = redisTemplateUtil.sIsMember(RedisKey.getUserFollowing(userDto.getId(), EnumUserFollowType.用户.getIndex()), accountId);
        roomUserBaseInfo.setFollowStatus(isFollow ? 1 : 0);
        //上麦状态
        RoomMicAccountLink micAccountLink = roomMicAccountLinkServiceI.getByRoomIdAndAccountId(roomId, accountId);
        roomUserBaseInfo.setUpMicStatus(micAccountLink == null ? 0 : 1);
        return new ResultTemplate(JSONObjectUtil.parseObject(roomUserBaseInfo));
    }

    /**
     * 邀请用户上麦
     *
     * @param dto
     */
    @Override
    @Transactional
    public void accountInviteUpMic(RoomUserInviteUpMicRequestDTO dto) {
        UserDto userDto = UserUtils.getUser();
        //房间判断
        roomServiceI.getById(dto.getRoomId(), "房间信息");
        //用户是否在房间
        RoomAccountLink roomAccountLink = this.checkRoomUserExist(dto.getRoomId(), dto.getAccountId());
        //判断用户是否在麦上
        this.checkUserIfMic(dto.getRoomId(), dto.getAccountId(), "The user has connect mic !");
        //添加邀请记录
        RoomMicInviteInfo inviteInfo = BeanUtils.copyProperties(dto, RoomMicInviteInfo.class);
        inviteInfo.setRoomAccountLinkId(roomAccountLink.getId());
        inviteInfo.setOperateStatus(EnumYesOrNo.否.getIndex());
        inviteInfo.setCreator(userDto.getId().toString());
        roomMicInviteInfoServiceI.insert(inviteInfo);
    }

    /**
     * 用户是否在房间
     *
     * @param roomId
     * @param accountId
     * @return
     */
    private RoomAccountLink checkRoomUserExist(Long roomId, Long accountId) {
        RoomAccountLink roomAccountLink = baseDaoT.getByRoomIdAndAccountId(roomId, accountId);
        if (roomAccountLink == null || roomAccountLink.getDeleted().intValue() == 1) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, "The room user does not exist !");
        }
        return roomAccountLink;
    }

    /**
     * 被邀请用户同意上麦
     *
     * @param dto
     */
    @Override
    @Transactional
    public JSONObject accountAgreeUpMic(RoomUserAgreeUpMicRequestDTO dto) {
        UserDto userDto = UserUtils.getUser();
        //房间判断
        roomServiceI.getById(dto.getRoomId(), "房间信息");
        //用户是否在房间
        RoomAccountLink roomAccountLink = this.checkRoomUserExist(dto.getRoomId(), userDto.getId());
        //查询邀请记录
        RoomMicInviteInfo roomMicInviteInfo = roomMicInviteInfoServiceI.getByRoomAndAccountId(dto.getRoomId(), userDto.getId());
        if (roomMicInviteInfo == null) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, "You do not have invited connect mic !");
        }
        //查询麦位是否有空麦
        RoomMicInfo minFreeMic = roomMicInfoServiceI.getMinFreeMicByRoomId(dto.getRoomId());
        if (minFreeMic == null) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, "The room mic is full");
        }
        //上麦记录
        this.insertMicAccountLink(dto.getRoomId(), EnumYesOrNo.是, userDto.getId(), roomAccountLink.getId(), minFreeMic.getId());
        //修改麦位状态（非空闲状态）
        roomMicInfoServiceI.updateByPrimaryKeySelective(RoomMicInfo.builder()
                .id(minFreeMic.getId())
                .micStatus(EnumYesOrNo.是.getIndex())
                .build());
        //用户邀请上麦记录修改
        roomMicInviteInfoServiceI.updateInviteStatusByRoomIdAndAccountId(dto.getRoomId(), userDto.getId());
        //生成麦位流信息
        RoomMicStreamLink micStreamLink = new RoomMicStreamLink()
                .setRoomId(dto.getRoomId())
                .setMicId(minFreeMic.getId())
                .setAccountId(userDto.getId());
        roomMicStreamLinkServiceI.insert(micStreamLink);
        //todo 热度值
        Long hotNum = redisTemplateUtil.incr(RedisKey.getRoomHotNum(dto.getRoomId()));
        //热度值写入数据库
        roomServiceI.updateByPrimaryKeySelective(new RoomInfo().setId(dto.getRoomId()).setHotNum(hotNum.intValue()));
        return new JSONObject().fluentPut("micStreamId", micStreamLink.getId());
    }

    /**
     * 房间在线用户列表
     *
     * @param pageListRequest
     * @return
     */
    @Override
    public ResultTemplate listRoomUser(RoomUserPageListRequestDTO pageListRequest) {
        PageHelper.startPage(pageListRequest.getCp(), pageListRequest.getRows());
        //所有用户
        if (pageListRequest.getEnumRoomUserType() == null || pageListRequest.getEnumRoomUserType().getIndex() == EnumRoomUserType.房间所有用户.getIndex()) {
            PageInfo<RoomAccountLinkVO> data = new PageInfo(baseDaoT.listRoomUser(pageListRequest));
            return new ResultTemplate(data, JSONObjectUtil.parseArray(data.getList()));
        }
        //麦上用户
        PageInfo<RoomAccountLinkVO> data = new PageInfo(roomMicAccountLinkServiceI.getList(pageListRequest));
        return new ResultTemplate(data, JSONObjectUtil.parseArray(data.getList()));
    }

    /**
     * 房间在线用户列表-无分页
     *
     * @param listRequestDTO
     * @return
     */
    @Override
    public JSONArray listOnlineRoomUser(RoomOnlineUserListRequestDTO listRequestDTO) {
        //所有用户
        RoomUserPageListRequestDTO pageListRequestDTO = BeanUtils.copyProperties(listRequestDTO, RoomUserPageListRequestDTO.class);
        if (pageListRequestDTO.getEnumRoomUserType() == null || pageListRequestDTO.getEnumRoomUserType().getIndex() == EnumRoomUserType.房间所有用户.getIndex()) {
            List<RoomAccountLinkVO> roomAccountLinkVOS = baseDaoT.listRoomUser(pageListRequestDTO);
            //分组处理
            Map<Integer, List<RoomAccountLinkVO>> vMap = roomAccountLinkVOS.stream().collect(Collectors.groupingBy(o -> o.getUserRole()));
            JSONArray result = new JSONArray();
            for (EnumRoomUserRole roomUserRole : EnumRoomUserRole.values()) {
                result.add(new JSONObject()
                        .fluentPut("userRole", roomUserRole.getIndex())
                        .fluentPut("enumRoomUserRole", roomUserRole.getName())
                        .fluentPut("userList", vMap.get(roomUserRole.getIndex())));
            }
            return result;
        }
        //麦上用户
        return JSONObjectUtil.parseArray(roomMicAccountLinkServiceI.getList(pageListRequestDTO));
    }

    /**
     * 发送礼物
     *
     * @param dto
     */
    @Override
    @Transactional
    public void doSendGift(GiftSendRequestDTO dto) {
        final UserDto userDto = UserUtils.getUser();
        //礼物
        GiftInfo giftInfo = giftInfoServiceI.getById(dto.getId());
        if (giftInfo == null) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, "礼物信息不存在");
        }
        //发送对象
        AccountInfo accountInfo = accountInfoServiceI.getById(dto.getTargetId(), "用户");
        if (dto.getRoomId() != null) {
            RoomInfo roomInfo = roomServiceI.getById(dto.getRoomId(), "房间");
            RoomAccountLink roomAccountLink = this.checkRoomUserExist(roomInfo.getId(), accountInfo.getId());
        }
        //todo 账号余额扣减-金币
        final BigDecimal giftAmount = giftInfo.getGiftPrice().multiply(new BigDecimal(dto.getGiftNum()));
        boolean subSuccess = balanceInfoServiceI.accountBalanceAndSub(userDto.getId(), giftAmount);
        if (!subSuccess) {
            throw new ChitchatException(CodeMsg.OPERATE_ERROR, "当前金币余额扣减失败");
        }
        //新增
        AccountGiftLink accountGiftLink = AccountGiftLink.builder()
                .giftId(giftInfo.getId())
                .giftPrice(giftInfo.getGiftPrice())
                .giftAmount(giftAmount)
                .giftNum(dto.getGiftNum())
                .roomId(dto.getRoomId())
                .targetId(accountInfo.getId())
                .accountId(userDto.getId())
                .build();
        accountGiftLinkServiceI.insert(accountGiftLink);
        CompletableFuture.runAsync(() ->{
            //todo 用户个人的贡献值
            redisTemplateUtil.incr(RedisKey.getUserContributionNum(userDto.getId()), giftAmount.longValue());
            //todo 接受礼物的用户的收礼情况
            redisTemplateUtil.incr(RedisKey.getUserReceivingContributionNum(accountInfo.getId()), giftAmount.longValue());
            //todo 用户在房间的贡献值
            if (dto.getRoomId() != null) {
                //用户在房间贡献值
                redisTemplateUtil.incr(RedisKey.getUserRoomContributionNum(userDto.getId(), dto.getRoomId()), giftAmount.longValue());
                //房间贡献值
                redisTemplateUtil.incr(RedisKey.getRoomContributionNum(dto.getRoomId()), giftAmount.longValue());
                //用户在房间收到的礼物值
                redisTemplateUtil.incr(RedisKey.getUserRoomReceivingContributionNum(accountInfo.getId(), dto.getRoomId()), giftAmount.longValue());
            }
            //todo 房间热度值
            Long hotNum = redisTemplateUtil.incr(RedisKey.getRoomHotNum(dto.getRoomId()), 5);
            //热度值写入数据库
            roomServiceI.updateByPrimaryKeySelective(new RoomInfo().setId(dto.getRoomId()).setHotNum(hotNum.intValue()));
        }, threadPoolExecutor);
        //todo 用户等级值 用户经验值刷新更新等级信、记录用户每日累计消费金币
        CompletableFuture.runAsync(() -> {
            accountExpFlowLogServiceI.userMemberLevelExpHandler(userDto.getId(), EnumLevelType.MEMBER,
                    EnumExpSourceType.GIVE_GIFT, giftAmount.intValue(), DateUtil.today());
        }, threadPoolExecutor);
    }

    /**
     * 房间贡献值排行榜
     *
     * @param roomId
     * @param type
     * @return
     */
    @Override
    public List<RoomContributionRankVO> getContributionRank(Long roomId, Integer type) {
        //房间榜单类型
        EnumRoomRankType enumRoomRankType = EnumUtil.valueOf(EnumRoomRankType.class, type);
        if (enumRoomRankType == null) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, "榜单参数有误");
        }
        //房间判断
        roomServiceI.getById(roomId, "房间");
        return accountGiftLinkServiceI.getContributionRank(roomId, type);
    }

    /**
     * 管理员锁麦/解麦操作
     *
     * @param dto
     */
    @Override
    @Transactional
    public void doAdminLockMic(RoomAdminLockMicRequestDTO dto) {
        UserDto userDto = UserUtils.getUser();
        roomServiceI.getById(dto.getRoomId(), "房间信息");
        RoomAccountLink roomAccountLink = baseDaoT.getByRoomIdAndAccountId(dto.getRoomId(), userDto.getId());
        if (roomAccountLink == null || roomAccountLink.getDeleted().intValue() == 1
                || roomAccountLink.getUserRole().intValue() == EnumRoomUserRole.观众.getIndex()) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, "没有锁麦权限操作");
        }
        RoomMicInfo micInfo = roomMicInfoServiceI.getById(dto.getMicId());
        if (micInfo == null || micInfo.getRoomId().longValue() != dto.getRoomId().longValue()
                || micInfo.getMicStatus().intValue() == EnumYesOrNo.是.getIndex()) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, "The room mic does not exist !");
        }
        roomMicInfoServiceI.updateByPrimaryKeySelective(RoomMicInfo.builder()
                .id(micInfo.getId())
                .whetherLock(dto.getLockMicType())
                .build());
        //锁麦日志
        log.info("管理员麦位操作：{}，参数：{}，操作人：{}-{}", dto.getLockMicTypeName(), JSON.toJSON(dto), userDto.getId(), userDto.getNickName());
    }

    /**
     * 管理员强制下麦
     *
     * @param requestDTO
     */
    @Override
    @Transactional
    public void doAdminOffMic(RoomAdminMicRequestDTO requestDTO) {
        UserDto userDto = UserUtils.getUser();
        //判断房间信息和用户操作权限
        this.checkRoomAndUser(userDto.getId(), requestDTO.getRoomId());
//        roomMicAccountLinkServiceI.getById(requestDTO.getAccountMicId(), "上麦位信息");
        RoomMicAccountLink roomMicAccountLink = roomMicAccountLinkServiceI.getByRoomIdAndAccountId(requestDTO.getRoomId(), requestDTO.getAccountId());
        if (roomMicAccountLink == null) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, "The user is not connect mic !");
        }
        //todo 删除用户上麦信息？
        //删除用户上麦信息(流信息)、回写麦位状态、新增用户上下麦记录
        this.insertAccountMicRecord(userDto, roomMicAccountLink);
        //房间热度值
        this.updateRoomHotNumDecline(requestDTO.getRoomId());
    }

    /**
     * 房间热度值下降
     *
     * @param roomId
     */
    private void updateRoomHotNumDecline(Long roomId) {
        //todo 热度值
        /*Long hotNum = null;
        if (redisTemplateUtil.hasKey(RedisKey.getRoomHotNum(roomId))) {
            hotNum = redisTemplateUtil.decr(RedisKey.getRoomHotNum(roomId), 1);
        }*/
        Long hotNum = redisTemplateUtil.decrRejectNegative(RedisKey.getRoomHotNum(roomId), 1);
        //热度值写入数据库
        roomServiceI.updateByPrimaryKeySelective(new RoomInfo().setId(roomId).setHotNum(hotNum == null ? 0 : hotNum.intValue()));
    }

    /**
     * 删除用户上麦信息(流信息)、回写麦位状态、新增用户上/下麦记录
     *
     * @param operateUser
     * @param roomMicAccountLink
     */
    private void insertAccountMicRecord(UserDto operateUser, RoomMicAccountLink roomMicAccountLink) {
        //删除用户上麦信息
        roomMicAccountLinkServiceI.deleteByLongId(roomMicAccountLink.getId());
        //删除麦位流信息-通过房间id、麦位id和用户id
        roomMicStreamLinkServiceI.deletedByRoomIdAndMicIdAndAccountId(roomMicAccountLink.getRoomId(), roomMicAccountLink.getMicId(), roomMicAccountLink.getAccountId());
        //修改麦位状态（空闲状态）
        roomMicInfoServiceI.updateByPrimaryKeySelective(RoomMicInfo.builder()
                .id(roomMicAccountLink.getMicId())
                .micStatus(EnumYesOrNo.否.getIndex())
                .build());
        //新增用户上下麦记录-后续可能统计用户在麦时长
        roomMicAccountRecordServiceI.insert(new RoomMicAccountRecord()
                .setAccountId(roomMicAccountLink.getAccountId())
                .setRoomAccountId(roomMicAccountLink.getRoomAccountLinkId())
                .setRoomId(roomMicAccountLink.getRoomId())
                .setMicId(roomMicAccountLink.getMicId())
                .setOnMicTime(roomMicAccountLink.getCreatedTime())
                .setOffMicOperatorId(operateUser.getId())
                .setOffMicOperatorName(operateUser.getNickName()));
    }

    /**
     * 管理员操作用户麦位是否静音
     *
     * @param dto
     */
    @Override
    @Transactional
    public void doAdminMuteMic(RoomAdminMuteMicRequestDTO dto) {
        UserDto userDto = UserUtils.getUser();
        //判断房间信息和用户操作权限
        this.checkRoomAndUser(userDto.getId(), dto.getRoomId());
        RoomMicAccountLink roomMicAccountLink = roomMicAccountLinkServiceI.getByRoomIdAndAccountId(dto.getRoomId(), dto.getAccountId());
        if (roomMicAccountLink == null) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, "The user is not connect mic !");
        }
        //静音
        roomMicAccountLinkServiceI.updateByPrimaryKeySelective(new RoomMicAccountLink()
                .setId(roomMicAccountLink.getId())
                .setMuteMicStatus(dto.getLockMicType()));
        //静音日志
        log.info("管理员静音操作：{}，参数：{}，操作人：{}-{}", dto.getLockMicTypeName(), JSON.toJSON(dto), userDto.getId(), userDto.getNickName());
    }

    /**
     * 管理员踢人操作
     * 剔出房间、在线人数-1
     *
     * @param dto
     */
    @Override
    @Transactional
    public void doAdminKickOut(RoomAdminKickOutRequestDTO dto) {
        UserDto userDto = UserUtils.getUser();
        //判断房间信息和用户操作权限
        this.checkRoomAndUser(userDto.getId(), dto.getRoomId());
        RoomAccountLink roomAccountLink = this.checkRoomUserExist(dto.getRoomId(), dto.getAccountId());
        //todo 剔出房间
        //用户退出房间
        this.accountQuitRoom(roomAccountLink, EnumUserLogoutReason.调用后台接口被踢出, userDto);
        //todo 房间在线人数-1
        roomServiceI.updateRoomUserNum(dto.getRoomId());
        /*if (redisTemplateUtil.hasKey(RedisKey.getRoomOnlineNum(dto.getRoomId()))) {
            redisTemplateUtil.decr(RedisKey.getRoomOnlineNum(dto.getRoomId()), 1);
        }*/
        redisTemplateUtil.decrRejectNegative(RedisKey.getRoomOnlineNum(dto.getRoomId()), 1);
        //热度 -1
        this.updateRoomHotNumDecline(dto.getRoomId());
        //踢人记录
        roomKickOutAccountLinkServiceI.insert(RoomKickOutAccountLink.builder()
                .accountId(dto.getAccountId())
                .roomId(dto.getRoomId())
                .kickOutDuration(dto.getKickOutDuration())
                .kickOutEndTime(EnumKickOutDuration.getKickOutEndTime(dto.getEnumKickOutDuration()))
                .createdUserId(userDto.getId())
                .createdUserName(userDto.getNickName())
                .build());
        //踢人日志
        log.info("管理员踢人操作，参数：{}，操作人：{}-{}", JSON.toJSON(dto), userDto.getId(), userDto.getNickName());
    }

    /**
     * 管理员邀人上麦-发送消息
     *
     * @param dto
     */
    @Override
    @Transactional
    public void doAdminInviteMic(RoomAdminInviteMicRequestDTO dto) {
        UserDto userDto = UserUtils.getUser();
        //判断房间信息和用户操作权限
        RoomInfo roomInfo = this.checkRoomAndUser(userDto.getId(), dto.getRoomId());
        RoomAccountLink roomAccountLink = this.checkRoomUserExist(dto.getRoomId(), dto.getAccountId());
        //判断用户是否在麦上
        checkUserIfMic(dto.getRoomId(), dto.getAccountId(), "The user has connect mic !");
        //添加邀请记录
        RoomMicInviteInfo inviteInfo = BeanUtils.copyProperties(dto, RoomMicInviteInfo.class);
        inviteInfo.setRoomAccountLinkId(roomAccountLink.getId());
        inviteInfo.setOperateStatus(EnumYesOrNo.否.getIndex());
        inviteInfo.setCreator(userDto.getId().toString());
        roomMicInviteInfoServiceI.insert(inviteInfo);
        //邀请
        CompletableFuture.runAsync(() -> {
            //发送要邀请
            roomServiceI.doSendCustomCommand(new RoomSendCustomRequestDTO()
                    .setRoomId(dto.getRoomId())
                    .setToUserId(CollUtil.newHashSet(dto.getAccountId()))
                    .setMessageContent(userDto.getNickName() + " invites you to take the mic !"));
        }, threadPoolExecutor);

    }

    /**
     * 判断用户是否连麦
     *
     * @param roomId
     * @param accountId
     */
    private void checkUserIfMic(Long roomId, Long accountId, String msg) {
        RoomMicAccountLink roomMicAccountLink = roomMicAccountLinkServiceI.getByRoomIdAndAccountId(roomId, accountId);
        if (roomMicAccountLink != null) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, msg);
        }
    }

    /**
     * 管理员换麦
     *
     * @param dto
     */
    @Override
    @Transactional
    public void doAdminSwitchMic(RoomAdminSwitchMicRequestDTO dto) {
        UserDto userDto = UserUtils.getUser();
        //判断房间信息和用户操作权限
        RoomInfo roomInfo = this.checkRoomAndUser(userDto.getId(), dto.getRoomId());
        //判断麦位是否空闲
        RoomMicInfo micInfo = roomMicInfoServiceI.getById(dto.getMicId());
        if (micInfo == null || micInfo.getRoomId().longValue() != dto.getRoomId().longValue()) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, "麦位信息有误！");
        }
        if (micInfo.getMicStatus().intValue() == EnumYesOrNo.是.getIndex()) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, "麦位信息非空闲状态！");
        }
        RoomMicAccountLink micAccountLink = roomMicAccountLinkServiceI.getByRoomIdAndAccountId(dto.getRoomId(), userDto.getId());
        if (micAccountLink == null) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, "The user is not connect mic !");
        }
        if (micAccountLink.getMicId().longValue() != dto.getMicId().longValue()) {
            //原来麦位状态修改-空闲
            roomMicInfoServiceI.updateByPrimaryKeySelective(RoomMicInfo.builder()
                    .id(micAccountLink.getMicId())
                    .micStatus(EnumYesOrNo.否.getIndex())
                    .build());
            //新的麦位状态修改-非空闲
            roomMicInfoServiceI.updateByPrimaryKeySelective(RoomMicInfo.builder()
                    .id(dto.getMicId())
                    .micStatus(EnumYesOrNo.是.getIndex())
                    .build());
            //换麦
            roomMicAccountLinkServiceI.updateByPrimaryKeySelective(new RoomMicAccountLink()
                    .setId(micAccountLink.getId())
                    .setMicId(dto.getMicId()));
            //管理员换麦日志
            log.info("管理员换麦操作，参数：{}，操作人：{}-{}", JSON.toJSON(dto), userDto.getId(), userDto.getNickName());
        }
    }

    /**
     * 判断房间信息和用户操作权限
     *
     * @param userId
     * @param roomId
     */
    private RoomInfo checkRoomAndUser(Long userId, Long roomId) {
        RoomInfo roomInfo = roomServiceI.getById(roomId, "房间信息");
        RoomAccountLink roomAccountLink = baseDaoT.getByRoomIdAndAccountId(roomId, userId);
        if (roomAccountLink == null || roomAccountLink.getDeleted().intValue() == 1
                || roomAccountLink.getUserRole().intValue() == EnumRoomUserRole.观众.getIndex()) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, "没有权限操作");
        }
        return roomInfo;
    }
}
