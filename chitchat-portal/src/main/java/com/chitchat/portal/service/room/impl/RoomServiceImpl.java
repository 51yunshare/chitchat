package com.chitchat.portal.service.room.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chitchat.admin.api.feign.FeignRoomTypeServiceI;
import com.chitchat.admin.api.vo.RoomTypeInfoVO;
import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.common.base.CodeMsg;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.base.UserDto;
import com.chitchat.common.enumerate.EnumYesOrNo;
import com.chitchat.common.exception.ChitchatException;
import com.chitchat.common.oss.OssKey;
import com.chitchat.common.oss.service.OssServiceI;
import com.chitchat.common.redis.RedisKey;
import com.chitchat.common.redis.RedisTemplateUtil;
import com.chitchat.common.util.BeanUtils;
import com.chitchat.common.util.JSONObjectUtil;
import com.chitchat.common.web.jwt.UserUtils;
import com.chitchat.portal.bean.account.AccountInfo;
import com.chitchat.portal.bean.base.TagInfo;
import com.chitchat.portal.bean.room.RoomAccountLink;
import com.chitchat.portal.bean.room.RoomInfo;
import com.chitchat.portal.bean.room.RoomKickOutAccountLink;
import com.chitchat.portal.dao.room.RoomDaoI;
import com.chitchat.portal.dto.index.HomeRankListRequest;
import com.chitchat.portal.dto.index.HomeSearchPageListRequest;
import com.chitchat.portal.dto.room.*;
import com.chitchat.portal.enumerate.EnumKickOutDuration;
import com.chitchat.portal.enumerate.EnumRoomUserRole;
import com.chitchat.portal.enumerate.EnumZegoRequestEvent;
import com.chitchat.portal.service.account.AccountInfoServiceI;
import com.chitchat.portal.service.base.TagInfoServiceI;
import com.chitchat.portal.service.room.*;
import com.chitchat.portal.service.zego.ZegoServiceI;
import com.chitchat.portal.vo.account.AccountBaseInfoVO;
import com.chitchat.portal.vo.index.RankVO;
import com.chitchat.portal.vo.room.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 *
 * Created by Js on 2022/7/29 .
 **/
@Service
@Slf4j
public class RoomServiceImpl extends BaseServicesImpl<RoomInfo, RoomDaoI> implements RoomServiceI {

    @Lazy
    @Autowired
    private RoomAccountLinkServiceI roomAccountLinkServiceI;
    @Autowired
    private AccountInfoServiceI accountInfoServiceI;
    @Autowired
    private TagInfoServiceI tagInfoServiceI;
    @Autowired
    private ZegoServiceI zegoServiceI;
    @Autowired
    private RoomKickOutAccountLinkServiceI roomKickOutAccountLinkServiceI;
    @Autowired
    private OssServiceI ossServiceI;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    private RoomMicInfoServiceI roomMicInfoServiceI;
    @Autowired
    private RedisTemplateUtil redisTemplateUtil;
    @Autowired
    private FeignRoomTypeServiceI feignRoomTypeServiceI;
    @Autowired
    private RoomPopularServiceI roomPopularServiceI;

    /**
     * 房间列表-分页
     *
     * @param pageListRequest
     * @return
     */
    @Override
    public ResultTemplate getList(RoomPageListRequest pageListRequest) {
        PageInfo<RoomInfo> data = list(pageListRequest);
        return new ResultTemplate(data, JSONObjectUtil.parseArray(BeanUtils.convertList(data.getList(), MyRoomPageListVO.class)));
    }


    /**
     * 新建房间
     *
     * todo 房间名称需要对敏感词汇过滤
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public JSONObject doAddRoom(RoomAddRequestDTO dto) {
        // todo 判断用户是否只有一个房间
        final UserDto userDto = UserUtils.getUser();
        //判断房间类型
        RoomTypeInfoVO roomTypeInfoVO = feignRoomTypeServiceI.getById(dto.getRoomType());
        if (roomTypeInfoVO == null){
            throw new ChitchatException(CodeMsg.BIND_ERROR, "The room type error!");
        }
        //上传文件
        if (dto.getImgFile() != null) {
            String uploadPath = ossServiceI.upload(dto.getImgFile(), OssKey.getImgPath(dto.getImgFile().getOriginalFilename()));
            dto.setRoomImg(uploadPath);
        }
        RoomInfo room = BeanUtils.copyProperties(dto, RoomInfo.class);
        room.setRoomTag(ObjectUtil.length(dto.getRoomTag()) > 0 ? JSONObjectUtil.parseArray(dto.getRoomTag()) : null);
        room.setCreatedUserId(userDto.getId());
        room.setCreatedUserName(userDto.getNickName());
        room.setRoomCountry(userDto.getCountry());
        room.setRoomUserNum(1);
        room.setHotNum(1);
        room.setContributionNum(BigDecimal.ZERO);
        room.setWhetherLock(0);
        room.setMicNum(9);
        room.setRoomTypeName(roomTypeInfoVO.getTypeName());
        room.setLimitUserNum(roomTypeInfoVO.getLimitUserNum());
//        room.setRoomType(room.getRoomType() == null ? EnumRoomType.普通.getIndex() : room.getRoomType());
        baseDaoT.insert(room);
        //麦位信息初始化
        roomMicInfoServiceI.init(room.getId(), room.getMicNum());
        //标签处理
        if (ObjectUtil.length(dto.getRoomTag()) > 0){
            dto.getRoomTag().forEach(tag ->{
                TagInfo tagInfo = tagInfoServiceI.getByName(tag);
                if (tagInfo == null){
                    tagInfoServiceI.insert(new TagInfo().setTagName(tag));
                }
            });
        }
        //房间用户关联关系
        roomAccountLinkServiceI.insert(RoomAccountLink.builder()
                .roomId(room.getId())
                .accountId(userDto.getId())
                .nickName(userDto.getNickName())
                .loginTime(DateUtil.date())
                .userRole(EnumRoomUserRole.主播.getIndex())
                .build());
        //房间在线人数+1
        redisTemplateUtil.incr(RedisKey.getRoomHotNum(room.getId()));
        redisTemplateUtil.incr(RedisKey.getRoomOnlineNum(room.getId()), 1);
        return JSONObjectUtil.parseObject(BeanUtils.copyProperties(room, RoomInfoVO.class));
    }

    /**
     * 房间编辑
     *
     * @param dto
     */
    @Override
    @Transactional
    public void doUpdateRoom(RoomUpdateRequestDTO dto) {
        UserDto userDto = UserUtils.getUser();
        RoomInfo roomInfo = this.getById(dto.getId(), "The room info");
        //判断当前用户的权限
        if (userDto.getId().longValue() != roomInfo.getCreatedUserId().longValue()){
            throw new ChitchatException(CodeMsg.OPERATE_ERROR, "No permission to operate !");
        }
        //上传文件
        if (dto.getImgFile() != null) {
            String uploadPath = ossServiceI.upload(dto.getImgFile(), OssKey.getImgPath(dto.getImgFile().getName()));
            dto.setRoomImg(uploadPath);
        }
        RoomInfo room = BeanUtils.copyProperties(dto, RoomInfo.class);
        room.setRoomTag(ObjectUtil.length(dto.getRoomTag()) > 0 ? JSONObjectUtil.parseArray(dto.getRoomTag()) : roomInfo.getRoomTag());
        baseDaoT.updateByPrimaryKeySelective(room);
        //标签处理
        if (ObjectUtil.length(dto.getRoomTag()) > 0){
            dto.getRoomTag().forEach(tag ->{
                TagInfo tagInfo = tagInfoServiceI.getByName(tag);
                if (tagInfo == null){
                    tagInfoServiceI.insert(new TagInfo().setTagName(tag));
                }
            });
        }
    }

    /**
     * 房间上/解锁
     *
     * @param dto
     */
    @Override
    @Transactional
    public void doLockRoom(RoomUpdateLockRequestDTO dto) {
        UserDto userDto = UserUtils.getUser();
        //房间校验
        RoomInfo roomInfo = this.getById(dto.getId(), "The room info ");
        if(dto.getWhetherLock().intValue() == EnumYesOrNo.是.getIndex() && StrUtil.isBlank(dto.getRoomPwd())){
            throw new ChitchatException(CodeMsg.BIND_ERROR, "Please set the room password !");
        }
        //判断房间密码是不是六位数字
        if (dto.getWhetherLock().intValue() == EnumYesOrNo.是.getIndex() && !NumberUtil.isInteger(dto.getRoomPwd())){
            throw new ChitchatException(CodeMsg.BIND_ERROR, "The room password is digits !");
        }
        if (dto.getRoomPwd().length() != 6){
            throw new ChitchatException(CodeMsg.BIND_ERROR, "The room password is six digits !");
        }
        RoomInfo newRoom = BeanUtils.copyProperties(dto, RoomInfo.class);
        newRoom.setRoomPwd(dto.getWhetherLock().intValue() == EnumYesOrNo.否.getIndex() ? null : dto.getRoomPwd());
        baseDaoT.updateLockRoom(newRoom);

        //日志
        log.info("房间是否上锁修改，参数：{}，操作人：【{}-{}】", dto, userDto.getId(), userDto.getNickName());
    }

    /**
     * 获取房间信息
     *
     * @param id
     * @return
     */
    @Override
    public RoomDetailVO getDetail(Long id) {
        //房间信息
        RoomInfo roomInfo = this.getById(id, "房间信息");
        RoomDetailVO vo = BeanUtils.copyProperties(roomInfo, RoomDetailVO.class);
        //封装房间详情
        this.pkgRoomDetailVO(id, roomInfo.getCreatedUserId(), vo);
        return vo;
    }

    /**
     * 封装房间详情
     *
     * @param roomId
     * @param createdUserId
     * @param vo
     */
    private void pkgRoomDetailVO(Long roomId, Long createdUserId, RoomDetailVO vo) {
        // 获取原请求线程的参数
//        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        // 房主信息
        CompletableFuture<Void> roomOwnerInfo = CompletableFuture.runAsync(() -> {
            // 请求参数传递给子线程
//            RequestContextHolder.setRequestAttributes(attributes);
            AccountInfo accountInfo = accountInfoServiceI.getById(createdUserId);
            if (accountInfo != null) {
                vo.setRoomOwner(BeanUtils.copyProperties(accountInfo, AccountBaseInfoVO.class));
            }
        }, threadPoolExecutor);

        // 房间麦位
        // 房间在麦情况([麦位,用户(基本信息，是否开麦)]))
        CompletableFuture<Void> micAccountInfo = CompletableFuture.runAsync(() -> {
//            RequestContextHolder.setRequestAttributes(attributes);
            //获取多有麦位和麦位用户信息
            List<RoomMicAccountInfoVO> micInfoList = roomMicInfoServiceI.listAccountMicByRoomId(roomId);
            vo.setMainMicInfo(micInfoList.get(0));
            micInfoList.remove(0);
            vo.setMicAccountInfo(micInfoList);
        }, threadPoolExecutor);

        CompletableFuture.allOf(roomOwnerInfo, micAccountInfo).join();
        //房间送礼总值
        vo.setContributionNum(redisTemplateUtil.get(RedisKey.getRoomContributionNum(roomId), Long.class) == null ? Long.valueOf(0) : redisTemplateUtil.get(RedisKey.getRoomContributionNum(roomId), Long.class));
        //热度值
        vo.setHotNum(redisTemplateUtil.get(RedisKey.getRoomHotNum(roomId), Long.class) == null ? Long.valueOf(0) : redisTemplateUtil.get(RedisKey.getRoomHotNum(roomId), Long.class));
    }

    /**
     * 销毁房间
     *
     * @param roomId
     */
    @Override
    @Transactional
    public void doDestroyRoom(Long roomId) {
        UserDto userDto = UserUtils.getUser();
        RoomInfo roomInfo = baseDaoT.getById(roomId);
        //只有房主可以销毁房间
        if (roomInfo.getCreatedUserId().longValue() != userDto.getId().longValue()){
            throw new ChitchatException(CodeMsg.BIND_ERROR, "Only the owner can destroy the room !");
        }
        if (roomInfo != null){
            //判断房间用户下线
            /*int num = roomAccountLinkServiceI.getUserNumByRoomId(roomId);
            if (num > 0){
                throw new ChitchatException(CodeMsg.BIND_ERROR, "房间还有用户，不能销毁！");
            }*/
            //删除-假删
            RoomInfo room = new RoomInfo().setId(roomId);
            room.setDeleted(EnumYesOrNo.是.getIndex());
            room.setDestroyTime(DateUtil.date());
            baseDaoT.updateByPrimaryKeySelective(room);
            log.info("销毁房间，参数：{}，操作人：【{}-{}】", roomId, userDto.getId(),userDto.getNickName());
        }
    }

    /**
     * Zego—获取房间人员数量
     *
     * @param roomId
     * @return
     */
    @Override
    public ResultTemplate getRoomUserNumList(Long roomId) {
        //判断房间信息
        this.checkRoomInfo(roomId);
        JSONArray result = zegoServiceI.getRoomUserNumByRoomId(roomId);
        return new ResultTemplate(result);
    }

    /**
     * 判断房间信息
     *
     * @param roomId
     * @return
     */
    private RoomInfo checkRoomInfo(Long roomId){
        RoomInfo roomInfo = baseDaoT.getById(roomId);
        if (roomInfo == null){
            throw new ChitchatException(CodeMsg.NULL_ERROR, "房间信息不存在");
        }
        return roomInfo;
    }

    /**
     * Zego—房间人员列表
     *
     * @param requestDTO
     * @return
     */
    @Override
    public ResultTemplate getRoomUserList(RoomUserListRequestDTO requestDTO) {
        //判断房间信息
        this.checkRoomInfo(requestDTO.getRoomId());
        JSONObject result = zegoServiceI.getRoomUserList(requestDTO);
        //用户其他信息获取？
        return new ResultTemplate(result);
    }

    /**
     * Zego—房间踢人操作
     *
     * @param requestDTO
     */
    @Override
    public void doRoomKickOutUser(RoomKickOutUserRequestDTO requestDTO) {
        //判断房间信息
        RoomInfo roomInfo = this.checkRoomInfo(requestDTO.getRoomId());
        //判断用户是否存在？

        //踢人
        zegoServiceI.doRoomKickOutUser(requestDTO);
        //踢人记录
        if (ObjectUtil.length(requestDTO.getAccountId()) > 0) {
            requestDTO.getAccountId().forEach(accountId ->{
                roomKickOutAccountLinkServiceI.insert(RoomKickOutAccountLink.builder()
                        .accountId(Long.valueOf(accountId))
                        .roomId(requestDTO.getRoomId())
                        .kickOutDuration(requestDTO.getKickOutDuration())
                        .kickOutEndTime(EnumKickOutDuration.getKickOutEndTime(requestDTO.getEnumKickOutDuration()))
                        .build());
            });
        }
        //todo 房间在线人数-1
        baseDaoT.updateRoomUserNum(requestDTO.getRoomId());
        /*if (redisTemplateUtil.hasKey(RedisKey.getRoomOnlineNum(requestDTO.getRoomId()))) {
            redisTemplateUtil.decr(RedisKey.getRoomOnlineNum(requestDTO.getRoomId()), 1);
        }*/
        redisTemplateUtil.decrRejectNegative(RedisKey.getRoomOnlineNum(requestDTO.getRoomId()), 1);
    }

    /**
     * Zego—房间推送广播消息
     *
     * @param requestDTO
     */
    @Override
    public JSONObject doSendBroadcastMessage(RoomSendBroadcastRequestDTO requestDTO) {
        //判断房间信息
        this.checkRoomInfo(requestDTO.getRoomId());
        JSONObject result = zegoServiceI.doSendBroadcastMessage(requestDTO, EnumZegoRequestEvent.推送广播消息);
        return result;
    }

    /**
     * Zego—房间推送弹幕消息
     *
     * @param requestDTO
     */
    @Override
    public JSONObject doSendBarrageMessage(RoomSendBroadcastRequestDTO requestDTO) {
        //判断房间信息
        this.checkRoomInfo(requestDTO.getRoomId());
        JSONObject result = zegoServiceI.doSendBroadcastMessage(requestDTO, EnumZegoRequestEvent.推送弹幕消息);
        return result;
    }

    /**
     * Zego—房间推送自定义消息
     *
     * @param requestDTO
     */
    @Override
    public JSONObject doSendCustomCommand(RoomSendCustomRequestDTO requestDTO) {
        //判断房间信息
        this.checkRoomInfo(requestDTO.getRoomId());
        JSONObject result = zegoServiceI.doSendCustomCommand(requestDTO);
        return result;
    }

    /**
     * 最近我加入房间列表
     *
     * @param pageListRequest
     * @return
     */
    @Override
    public ResultTemplate getMyJoinRoomList(RoomPageListRequest pageListRequest) {
        pageListRequest.setUserInfo(UserUtils.getUser());
        PageHelper.startPage(pageListRequest.getCp(), pageListRequest.getRows());
        PageInfo<MyRoomPageListVO> data = new PageInfo(baseDaoT.getMyJoinRoomList(pageListRequest));
        return new ResultTemplate(data, data.getList());
    }

    /**
     * 我关注的房间列表
     *
     * @param pageListRequest
     * @return
     */
    @Override
    public ResultTemplate myFollowingList(RoomPageListRequest pageListRequest) {
        pageListRequest.setUserInfo(UserUtils.getUser());
        PageHelper.startPage(pageListRequest.getCp(), pageListRequest.getRows());
        PageInfo<MyRoomPageListVO> data = new PageInfo(baseDaoT.getMyFollowingList(pageListRequest));
        return new ResultTemplate(data, data.getList());
    }

    /**
     * 搜索
     *
     * @param pageListRequest
     * @return
     */
    @Override
    public PageInfo selectBySearch(HomeSearchPageListRequest pageListRequest) {
        PageHelper.startPage(pageListRequest.getCp(), pageListRequest.getRows());
        return new PageInfo(baseDaoT.selectBySearch(pageListRequest));
    }

    /**
     * Zego—增加房间流
     *
     * @param requestDTO
     */
    @Override
    public void doAddStream(RoomAddStreamRequestDTO requestDTO) {
        //判断房间信息
        this.checkRoomInfo(requestDTO.getRoomId());
        zegoServiceI.doAddStream(requestDTO);
    }

    /**
     * Zego—删除房间流
     *
     * @param requestDTO
     */
    @Override
    public void doDeleteStream(RoomDeletedStreamRequestDTO requestDTO) {
        //判断房间信息
        this.checkRoomInfo(requestDTO.getRoomId());
        zegoServiceI.doDeleteStream(requestDTO);
    }

    /**
     * Zego—获取简易流列表
     *
     * @param roomId
     * @return
     */
    @Override
    public ResultTemplate getSimpleStreamList(Long roomId) {
        //判断房间信息
        this.checkRoomInfo(roomId);
        JSONObject result = zegoServiceI.getSimpleStreamList(roomId);
        return new ResultTemplate(result);
    }

    /**
     * Zego—关闭房间
     *
     * @param roomId
     */
    @Override
    public void doCloseRoom(Long roomId) {
        //判断房间信息
        this.checkRoomInfo(roomId);
        zegoServiceI.doCloseRoom(roomId);
    }

    /**
     * 推荐房间列表
     * todo 推荐规则 热度
     * 推荐列表按照热度值排序。推荐逻辑额外有一个是支持配置固定房间(某一个一个房间，ID纬度)在固定排序的位置，比如新手房间配置固定在第二个
     * todo 推荐是否可以不要分页，查出来的总房间 - 推荐配置的房间
     *
     * @param pageListRequest
     * @return
     */
    @Override
    public ResultTemplate getPopularRoomList(RoomPageListRequest pageListRequest) {
        pageListRequest.setUserInfo(UserUtils.getUser());
        PageHelper.startPage(pageListRequest.getCp(), pageListRequest.getRows());
        PageInfo<MyRoomPageListVO> data = new PageInfo(baseDaoT.getPopularRoomList(pageListRequest));
        List<MyRoomPageListVO> resultList = data.getList();
        this.insertConfigPopularRoom(data, resultList);
        return new ResultTemplate(data, resultList.stream().distinct().collect(Collectors.toList()));
    }

    /**
     * 插入固定推荐房间信息
     *
     * @param data
     * @param resultList
     */
    private void insertConfigPopularRoom(PageInfo<MyRoomPageListVO> data, List<MyRoomPageListVO> resultList) {
        //判断是否有配置推荐房间
        List<RoomPopularListVO> roomPopularConfig = roomPopularServiceI.getAllPopularList();
        if (ObjectUtil.length(roomPopularConfig) > 0){
            roomPopularConfig.forEach(popular ->{
                //判断推荐的排序是不是当前页的，如果是当前页插入
                if (popular.getRecommendSort().intValue() <= data.getPageNum() * data.getPageSize()
                        && popular.getRecommendSort().intValue() > (data.getPageNum() - 1 ) * data.getPageSize()){
                    int sortIndex = popular.getRecommendSort().intValue() - (data.getPageNum() - 1) * data.getPageSize();
                    resultList.add(sortIndex - 1, BeanUtils.copyProperties(popular, MyRoomPageListVO.class));
                }
            });
        }
    }

    /**
     * 修改房间用户人数
     *
     * @param roomId
     */
    @Override
    public void updateRoomUserNum(Long roomId) {
        baseDaoT.updateRoomUserNum(roomId);
    }

    /**
     * 我的房间信息
     *
     * @return
     */
    @Override
    public JSONObject getMyRoomInfo() {
        UserDto userDto = UserUtils.getUser();
        RoomInfo roomInfo = baseDaoT.getMyRoomInfo(userDto.getId());
        if (roomInfo == null){
            return new JSONObject();
        }
        MyRoomPageListVO myRoomPageListVO = BeanUtils.copyProperties(roomInfo, MyRoomPageListVO.class);
//        myRoomPageListVO.setHotNum(getHotNum(roomInfo.getId()));
        return JSONObjectUtil.parseObject(myRoomPageListVO);
    }

    /**
     * 获取房间类型
     *
     * @return
     */
    @Override
    public JSONArray getRoomType() {
        return feignRoomTypeServiceI.getComList();
    }

    /**
     * 获取房间热度值
     *
     * @param roomId
     * @return
     */
    private Integer getHotNum(Long roomId){
        return redisTemplateUtil.get(RedisKey.getRoomHotNum(roomId), Integer.class) == null ? 0 : redisTemplateUtil.get(RedisKey.getRoomHotNum(roomId), Integer.class);
    }

    /**
     * 排行榜
     *
     * @param listRequest
     * @return
     */
    @Override
    public List<RankVO> listRank(HomeRankListRequest listRequest) {
        return baseDaoT.listRank(listRequest);
    }
}
