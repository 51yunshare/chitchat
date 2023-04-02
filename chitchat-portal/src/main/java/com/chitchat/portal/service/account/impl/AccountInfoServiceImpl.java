package com.chitchat.portal.service.account.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chitchat.auth.api.feign.FeignAuthServiceI;
import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.common.base.CodeMsg;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.base.UserDto;
import com.chitchat.common.constant.AuthConstant;
import com.chitchat.common.enumerate.EnumUserSource;
import com.chitchat.common.enumerate.EnumUserStatus;
import com.chitchat.common.enumerate.EnumYesOrNo;
import com.chitchat.common.exception.ChitchatException;
import com.chitchat.common.oss.OssKey;
import com.chitchat.common.oss.service.OssServiceI;
import com.chitchat.common.redis.RedisKey;
import com.chitchat.common.redis.RedisTemplateStringUtil;
import com.chitchat.common.redis.RedisTemplateUtil;
import com.chitchat.common.util.BeanUtils;
import com.chitchat.common.util.JSONObjectUtil;
import com.chitchat.common.util.ServletUtils;
import com.chitchat.common.web.jwt.UserUtils;
import com.chitchat.portal.api.dto.MemberDTO;
import com.chitchat.portal.api.vo.AccountInfoVO;
import com.chitchat.portal.bean.account.*;
import com.chitchat.portal.bean.room.RoomInfo;
import com.chitchat.portal.dao.account.AccountInfoDaoI;
import com.chitchat.portal.dto.account.*;
import com.chitchat.portal.dto.index.HomeSearchPageListRequest;
import com.chitchat.portal.enumerate.EnumLoginType;
import com.chitchat.portal.enumerate.EnumUserFollowType;
import com.chitchat.portal.service.account.*;
import com.chitchat.portal.service.room.RoomGameAccountLinkServiceI;
import com.chitchat.portal.service.room.RoomServiceI;
import com.chitchat.portal.vo.account.AccountDetailVO;
import com.chitchat.portal.vo.account.AccountFollowLinkVO;
import com.chitchat.portal.vo.account.AccountFriendsLinkVO;
import com.chitchat.portal.vo.account.AccountVisitorsInfoVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Js on 2022/7/31.
 **/
@Service
public class AccountInfoServiceImpl extends BaseServicesImpl<AccountInfo, AccountInfoDaoI> implements AccountInfoServiceI {

    @Autowired
    private RedisTemplateStringUtil redisTemplateStringUtil;
    @Autowired
    private RedisTemplateUtil redisTemplateUtil;

    @Autowired
    private FeignAuthServiceI feignAuthServiceI;
    @Lazy
    @Autowired
    private RoomServiceI roomServiceI;
    @Autowired
    private OssServiceI ossServiceI;
    @Autowired
    private AccountFollowLinkServiceI accountFollowLinkServiceI;
    @Autowired
    private AccountLoginLogServiceI accountLoginLogServiceI;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    private RoomGameAccountLinkServiceI roomGameAccountLinkServiceI;
    @Autowired
    private AccountGiftLinkServiceI accountGiftLinkServiceI;
    @Lazy
    @Autowired
    private AccountVisitorsInfoServiceI accountVisitorsInfoServiceI;
    @Autowired
    private AccountFriendsLinkServiceI accountFriendsLinkServiceI;

    /**
     * 用户默认等级
     */
    private final static long ACCOUNT_LEVEL = 1;
    /**
     * 用户初始等级经验
     */
    private final static int ACCOUNT_LEVEL_EXP = 0;


    /**
     * 登录
     *
     * @param loginRequest
     * @return
     */
    @Override
    public ResultTemplate login(MemberLoginRequest loginRequest) {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", AuthConstant.PORTAL_CLIENT_ID);
        params.put("client_secret", AuthConstant.PORTAL_CLIENT_SECRET);
        params.put("grant_type", "password");
//        params.put("username", loginRequest.getUsername());
        params.put("password", loginRequest.getPassword());
        //判断用户是否注册
        AccountInfo accountInfo = baseDaoT.getByMobileOrUsername(loginRequest.getUsername());
        if (accountInfo == null){
            throw new ChitchatException(CodeMsg.USER_NOT_EXISTS);
        }
        params.put("username", accountInfo.getUsername());
        ResultTemplate resultTemplate = feignAuthServiceI.getAccessToken(params);
        //异步刷登录日志
        loginLog(loginRequest.getUsername(), loginRequest.getPassword(), accountInfo, resultTemplate);
        //最后登录时间
        baseDaoT.updateByPrimaryKeySelective(AccountInfo.builder().id(accountInfo.getId()).lastLoginTime(DateUtil.date()).build());
        return resultTemplate;
    }

    /**
     * 登录日志
     *
     * @param username
     * @param pwd
     * @param accountInfo
     * @param resultTemplate
     */
    private void loginLog(String username, String pwd, AccountInfo accountInfo, ResultTemplate resultTemplate) {
        // 获取原请求线程的参数
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        CompletableFuture.runAsync(() -> {
            // 请求参数传递给子线程
            RequestContextHolder.setRequestAttributes(attributes);
            AccountLoginLog accountLoginLog = new AccountLoginLog()
                    .setLoginIp(ServletUtils.getRealIp(ServletUtils.getRequest()))
                    .setLoginType(EnumLoginType.PC.getIndex())
                    .setUsername(username)
//                    .setPassword(pwd)
                    .setUserAgent(ServletUtils.getUserAgent())
                    .setAccountId(accountInfo.getId())
                    .setLoginResult(JSONObjectUtil.parseObject(resultTemplate));
            accountLoginLogServiceI.insert(accountLoginLog);
        }, threadPoolExecutor);
    }

    /**
     * 第三方授权登录/注册
     *
     * @param authLoginRequest
     * @return
     */
    @Override
    public ResultTemplate doSocialAuthLogin(SocialAuthLoginRequest authLoginRequest) {
        //判断第三方用户是否存在
        AccountInfo accountInfo = baseDaoT.loadUserByOpenId(authLoginRequest.getIdentifier(), authLoginRequest.getIdentityType());
        //新增第三方用户
        if (accountInfo == null){
            AccountInfo account = AccountInfo.builder()
                    .status(EnumYesOrNo.是.getIndex())
                    .nickName(StrUtil.isBlank(authLoginRequest.getNickName()) ? "talk_" + DateUtil.current() : authLoginRequest.getNickName())
                    .gender(0)
                    .sourceType(EnumUserSource.APP.getIndex())
                    .accountLevelId(ACCOUNT_LEVEL)
                    .accountLevelExp(ACCOUNT_LEVEL_EXP)
                    .accountGameLevelId(ACCOUNT_LEVEL)
                    .accountGameLevelExp(ACCOUNT_LEVEL_EXP)
                    .vipLevelId(ACCOUNT_LEVEL)
                    .kingLevelId(ACCOUNT_LEVEL)
                    .build();
            baseDaoT.insert(account);
            baseDaoT.insertAccountLoginAuth(AccountLoginAuthInfo.builder()
                    .accountId(account.getId())
                    .identifier(authLoginRequest.getIdentifier())
                    .bindFlag(EnumYesOrNo.是.getIndex())
                    .identityType(authLoginRequest.getIdentityType())
                    .build());
            accountInfo = account;
        }
        Map<String, String> params = new HashMap<>();
        params.put("client_id", AuthConstant.PORTAL_CLIENT_ID);
        params.put("client_secret", AuthConstant.PORTAL_CLIENT_SECRET);
        params.put("grant_type", "social");
        params.put("identifier", authLoginRequest.getIdentifier());
        params.put("identity_type", authLoginRequest.getIdentityType().toString());
        //登录
        ResultTemplate resultAccessToken = feignAuthServiceI.getAccessToken(params);
        //最后登录时间
        baseDaoT.updateByPrimaryKeySelective(AccountInfo.builder().id(accountInfo.getId()).lastLoginTime(DateUtil.date()).build());
        return resultAccessToken;
    }

    /**
     * 根据用户名获取通用用户信息
     *
     * @param username
     * @return
     */
    @Override
    public UserDto loadUserByUsername(String username) {
        AccountInfo accountInfo = baseDaoT.loadUserByUsername(username);
        if (accountInfo != null) {
            UserDto userDto = BeanUtils.copyProperties(accountInfo, UserDto.class);
            userDto.setRoles(CollUtil.toList("前台会员"));
            return userDto;
        }
        return null;
    }

    /**
     * 通过openId获取会员信息
     *
     * @param openid
     * @param identityType
     * @return
     */
    @Override
    public UserDto getByOpenid(String openid, Integer identityType) {
        AccountInfo accountInfo = baseDaoT.loadUserByOpenId(openid, identityType);
        if (accountInfo != null) {
            UserDto userDto = BeanUtils.copyProperties(accountInfo, UserDto.class);
            userDto.setRoles(CollUtil.toList("前台会员"));
            return userDto;
        }
        return null;
    }

    /**
     * 会员新增
     *
     * @param member
     * @return
     */
    @Override
    @Transactional
    public Long addMember(MemberDTO member) {
        AccountInfo account = BeanUtils.copyProperties(member, AccountInfo.class);
        account.setSourceType(EnumUserSource.APP.getIndex());
        account.setAccountLevelId(ACCOUNT_LEVEL);
        account.setAccountLevelExp(ACCOUNT_LEVEL_EXP);
        account.setAccountGameLevelId(ACCOUNT_LEVEL);
        account.setAccountGameLevelExp(ACCOUNT_LEVEL_EXP);
        account.setVipLevelId(ACCOUNT_LEVEL);
        account.setKingLevelId(ACCOUNT_LEVEL);
        baseDaoT.insert(account);
        //新增第三方登录扩展表
        if (StrUtil.isNotBlank(member.getIdentifier())) {
            baseDaoT.insertAccountLoginAuth(AccountLoginAuthInfo.builder()
                    .accountId(account.getId())
                    .identifier(member.getIdentifier())
                    .bindFlag(EnumYesOrNo.是.getIndex())
                    .identityType(member.getAppType())
                    .build());
        }
        return account.getId();
    }

    /**
     * 手机号判断是否注册
     *
     * @param mobile
     * @return
     */
    @Override
    public ResultTemplate verifyMobile(String mobile) {
        AccountInfo account = baseDaoT.getByMobile(mobile);
        return new ResultTemplate(account != null);
    }

    /**
     * 会员编辑
     *
     * @param updateRequest
     */
    @Override
    @Transactional
    public void doUpdate(MemberUpdateRequest updateRequest) {
        //用户头像上传
        if(updateRequest.getIconFile() != null){
            String uploadPath = ossServiceI.upload(updateRequest.getIconFile(), OssKey.getImgPath(updateRequest.getIconFile().getOriginalFilename()));
            updateRequest.setIcon(uploadPath);
        }
        AccountInfo accountInfo = BeanUtils.copyProperties(updateRequest, AccountInfo.class);
        accountInfo.setId(UserUtils.getUserId());
        baseDaoT.updateByPrimaryKeySelective(accountInfo);
    }

    /**
     * 根据手机号获取通用用户信息
     *
     * @param mobile
     * @return
     */
    @Override
    public UserDto loadUserByMobile(String mobile) {
        AccountInfo accountInfo = baseDaoT.getByMobile(mobile);
        if (accountInfo != null) {
            UserDto userDto = BeanUtils.copyProperties(accountInfo, UserDto.class);
            userDto.setRoles(CollUtil.toList("前台会员"));
            return userDto;
        }
        return null;
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
     * 会员关注
     *
     * @param followRequest
     */
    @Override
    @Transactional
    public void doFollow(MemberFollowRequest followRequest) {
        final Long currentUserId = UserUtils.getUserId();
        //判断对应参数
        if (followRequest.getEnumUserFollowType().getIndex() == EnumUserFollowType.房间.getIndex()) {
            this.followRoom(followRequest, currentUserId);
        } else {
            if (followRequest.getTargetId().longValue() == currentUserId.longValue()) {
                //自己不能关注自己
                return;
            }
            this.followUser(followRequest, currentUserId);
            //用户互粉关系(用于后期查询用户之间是否互粉)
//            redisTemplateUtil.set(RedisKey.getMutualFans(currentUserId, followRequest.getTargetId()), 1);
        }
        //2.写redis,关注者、粉丝者添加
        //关注
        redisTemplateUtil.sAdd(RedisKey.getUserFollowing(currentUserId, followRequest.getEnumUserFollowType().getIndex()), followRequest.getTargetId());
        //粉丝
        redisTemplateUtil.sAdd(RedisKey.getUserFollower(followRequest.getTargetId(), followRequest.getEnumUserFollowType().getIndex()), currentUserId);
        /*//2.写redis,粉丝数+1、关注数+1
        //目标(用户/房间)粉丝数+1
        redisTemplateUtil.incr(RedisKey.getFansNum(followRequest.getTargetId(), followRequest.getEnumUserFollowType().getIndex()), 1);
        //用户关注数+1
        redisTemplateUtil.incr(RedisKey.getFollowNum(currentUserId, followRequest.getEnumUserFollowType().getIndex()), 1);*/

    }

    /**
     * 关注用户
     *
     * @param followRequest
     * @param currentUserId
     */
    private void followUser(MemberFollowRequest followRequest, Long currentUserId) {
        AccountInfo acc = baseDaoT.getById(followRequest.getTargetId());
        if (acc == null) {
            throw new ChitchatException(CodeMsg.NULL_ERROR, "关注用户信息不存在");
        }
        //查询是否关注过
        AccountFollowLink followLinkPO = accountFollowLinkServiceI.getFollowByTargetIdAndAccountId(followRequest.getFollowType(),
                followRequest.getTargetId(), currentUserId);
        //查询用户是否互关
        AccountFollowLink fans = baseDaoT.getFansByUserId(followRequest.getTargetId(), currentUserId, EnumUserFollowType.用户.getIndex());
        //以前关注过，取消关注了重新关注
        if (followLinkPO != null && followLinkPO.getFollowStatus().intValue() == 0){
            baseDaoT.updateAccountFollowLink(AccountFollowLink.builder()
                    .id(followLinkPO.getId())
                    .followStatus(EnumYesOrNo.是.getIndex())
                    .friend(fans != null ? EnumYesOrNo.是.getIndex() : null)
                    .build());
        }
        //没有关注过
        if (followLinkPO == null) {
            //1.写数据库
            AccountFollowLink accountFollowLink = AccountFollowLink.builder()
                    .followType(followRequest.getEnumUserFollowType().getIndex())
                    .accountId(currentUserId)
                    .targetId(followRequest.getTargetId())
                    .followStatus(EnumYesOrNo.是.getIndex())
                    .friend(fans != null ? EnumYesOrNo.是.getIndex() : EnumYesOrNo.否.getIndex())
                    .build();
            baseDaoT.insertAccountFollowLink(accountFollowLink);
        }
        //回写粉丝互关
        if (fans != null){
            baseDaoT.updateAccountFollowLink(AccountFollowLink.builder()
                    .id(fans.getId())
                    .friend(EnumYesOrNo.是.getIndex())
                    .build());
        }
    }

    /**
     * 关注房间
     *
     * @param followRequest
     * @param currentUserId
     */
    private void followRoom(MemberFollowRequest followRequest, Long currentUserId) {
        roomServiceI.getById(followRequest.getTargetId(), "关注房间信息");
        //查询是否关注过
        AccountFollowLink followLinkPO = accountFollowLinkServiceI.getFollowByTargetIdAndAccountId(followRequest.getFollowType(),
                followRequest.getTargetId(), currentUserId);
        //1.写数据库
        if (followLinkPO == null){
            AccountFollowLink accountFollowLink = AccountFollowLink.builder()
                    .followType(followRequest.getEnumUserFollowType().getIndex())
                    .accountId(currentUserId)
                    .targetId(followRequest.getTargetId())
                    .followStatus(EnumYesOrNo.是.getIndex())
                    .friend(EnumYesOrNo.否.getIndex())
                    .build();
            baseDaoT.insertAccountFollowLink(accountFollowLink);
        }
        //如果取消关注，重新关注
        if (followLinkPO.getFollowStatus().intValue() == 0){
            baseDaoT.updateAccountFollowLink(AccountFollowLink.builder()
                    .id(followLinkPO.getId())
                    .followStatus(EnumYesOrNo.是.getIndex())
                    .build());
        }
        //热度+1
//            roomServiceI.updateByPrimaryKeySelective(new RoomInfo()
//                    .setId(followRequest.getTargetId())
//                    .setHotNum(roomInfo.getHotNum() == null ? 1 : roomInfo.getHotNum() + 1));
    }

    /**
     * 会员取消关注
     *
     * @param followRequest
     */
    @Override
    @Transactional
    public void doCancelFollow(MemberFollowRequest followRequest) {
        final Long currentUserId = UserUtils.getUserId();
        if (followRequest.getEnumUserFollowType().getIndex() == EnumUserFollowType.房间.getIndex()) {
            RoomInfo roomInfo = roomServiceI.getById(followRequest.getTargetId(), "关注房间信息");
            AccountFollowLink fans = baseDaoT.getFansByUserId(currentUserId, followRequest.getTargetId(), EnumUserFollowType.房间.getIndex());
            //删除关注关系
            if (fans != null){
                accountFollowLinkServiceI.deleteByLongId(fans.getId());
            }
            /*if (fans != null) {
                baseDaoT.updateAccountFollowLink(AccountFollowLink.builder()
                        .id(fans.getId())
                        .followStatus(EnumYesOrNo.否.getIndex())
                        .friend(EnumYesOrNo.否.getIndex())
                        .build());
            }*/
            //热度-1
//            roomServiceI.updateByPrimaryKeySelective(new RoomInfo()
//                    .setId(followRequest.getTargetId())
//                    .setHotNum(roomInfo.getHotNum() == null ? 0 : roomInfo.getHotNum().intValue() == 0 ? 0 : roomInfo.getHotNum() - 1));
        }else {
            //目标用户的
            AccountFollowLink fans = baseDaoT.getFansByUserId(followRequest.getTargetId(), currentUserId, EnumUserFollowType.用户.getIndex());
            if (fans != null) {//取消互粉
                baseDaoT.updateAccountFollowLink(AccountFollowLink.builder()
                        .id(fans.getId())
                        .friend(EnumYesOrNo.否.getIndex())
                        .build());
            }
            //当前用户的关注关系
            AccountFollowLink fansUser = baseDaoT.getFansByUserId(currentUserId, followRequest.getTargetId(), EnumUserFollowType.用户.getIndex());//删除关注关系
            //删除关注关系
            if (fansUser != null) {
                accountFollowLinkServiceI.deleteByLongId(fansUser.getId());
            }
            /*if (fansUser != null) {
                baseDaoT.updateAccountFollowLink(AccountFollowLink.builder()
                        .id(fansUser.getId())
                        .followStatus(EnumYesOrNo.否.getIndex())
                        .friend(EnumYesOrNo.否.getIndex())
                        .build());
            }*/
            //删除redis用户互粉关系
//            redisTemplateUtil.del(RedisKey.getMutualFans(currentUserId, followRequest.getTargetId()));
        }
        //2.写redis,粉丝数-1、关注数-1
        //目标粉丝数-1
        redisTemplateUtil.sRemove(RedisKey.getUserFollower(followRequest.getTargetId(), followRequest.getEnumUserFollowType().getIndex()), currentUserId);
//        redisTemplateUtil.decrRejectNegative(RedisKey.getFansNum(followRequest.getTargetId(), followRequest.getEnumUserFollowType().getIndex()), 1);
//        if (redisTemplateUtil.hasKey(RedisKey.getFansNum(followRequest.getTargetId(), followRequest.getEnumUserFollowType().getIndex()))) {
//            redisTemplateUtil.decr(RedisKey.getFansNum(followRequest.getTargetId(), followRequest.getEnumUserFollowType().getIndex()), 1);
//        }
        //用户关注数-1
//        if (redisTemplateUtil.hasKey(RedisKey.getFollowNum(currentUserId, followRequest.getEnumUserFollowType().getIndex()))) {
//            redisTemplateUtil.decr(RedisKey.getFollowNum(currentUserId, followRequest.getEnumUserFollowType().getIndex()), 1);
//        }
        redisTemplateUtil.sRemove(RedisKey.getUserFollowing(currentUserId, followRequest.getEnumUserFollowType().getIndex()), followRequest.getTargetId());
//        redisTemplateUtil.decrRejectNegative(RedisKey.getFollowNum(currentUserId, followRequest.getEnumUserFollowType().getIndex()), 1);

    }

    /**
     * 会员注册
     *
     * @param registerRequest
     * @return
     */
    @Override
//    @Transactional
    public ResultTemplate doRegister(MemberRegisterRequest registerRequest) {
        //校验手机号是否重复
        AccountInfo accountUsername = baseDaoT.getByMobileOrUsername(registerRequest.getMobile());
        if (accountUsername != null) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, "手机号账号已存在，请直接登录！");
        }
        //新增用户信息
        this.insertAccount(registerRequest);
        //注册完成默认登录
        return login(new MemberLoginRequest()
                .setPassword(registerRequest.getPassword())
                .setUsername(registerRequest.getMobile()));
//        return new ResultTemplate(account.getId());
    }

    /**
     * 新增用户信息
     *
     * @param registerRequest
     */
    private void insertAccount(MemberRegisterRequest registerRequest) {
        //用户头像上传
        if(registerRequest.getIconFile() != null){
            String uploadPath = ossServiceI.upload(registerRequest.getIconFile(), OssKey.getImgPath(registerRequest.getIconFile().getOriginalFilename()));
            registerRequest.setIcon(uploadPath);
        }
        //新增用户
        AccountInfo account = BeanUtils.copyProperties(registerRequest, AccountInfo.class);
        account.setNickName(StrUtil.isBlank(account.getNickName()) ? "talk_" + DateUtil.current() : account.getNickName());
        account.setGender(account.getGender() == null ? 0 : account.getGender());
        account.setUsername(registerRequest.getMobile());
        account.setPassword(BCrypt.hashpw(account.getPassword()));
        account.setSourceType(EnumUserSource.APP.getIndex());
        account.setStatus(EnumUserStatus.ENABLE.getIndex());
        account.setAccountLevelId(ACCOUNT_LEVEL);
        account.setAccountLevelExp(ACCOUNT_LEVEL_EXP);
        account.setAccountGameLevelId(ACCOUNT_LEVEL);
        account.setAccountGameLevelExp(ACCOUNT_LEVEL_EXP);
        account.setVipLevelId(ACCOUNT_LEVEL);
        account.setKingLevelId(ACCOUNT_LEVEL);
        baseDaoT.insert(account);
    }


    /**
     * 获取当前会员信息
     *
     * @return
     */
    @Override
    public ResultTemplate getCurrentMember() {
        //获取Token信息
        UserDto userDto = UserUtils.getUser();
        //获取会员信息
        AccountDetailVO infoVO = this.getDetailVO(userDto.getId(), null);
        return new ResultTemplate(JSONObjectUtil.parseObject(infoVO));
    }

    /**
     * 获取会员详细信息
     *
     * @param accountId
     * @return
     */
    @Override
    public AccountDetailVO getAccountDetailVO(Long accountId) {
        UserDto userDto = UserUtils.getUser();
        AccountDetailVO infoVO = this.getDetailVO(accountId, userDto.getId());
        //是否互关
        infoVO.setFollow(redisTemplateUtil.sIsMember(RedisKey.getUserFollowing(userDto.getId(), EnumUserFollowType.用户.getIndex()), accountId));
//        infoVO.setFollow(redisTemplateUtil.hasKey(RedisKey.getMutualFans(UserUtils.getUserId(), accountId)));
        //最近玩的游戏类型和所有收到的礼物
        this.getRecentGameAndReceivedGift(accountId, infoVO);
        return infoVO;
    }

    /**
     * 会员添加好友
     *
     * @param dto
     */
    @Override
    @Transactional
    public void doAddFriend(MemberFriendAddOrRemoveRequest dto) {
        UserDto userDto = UserUtils.getUser();
        //判断好友信息
        this.checkAccountInfo(dto.getFriendAccountId());
        //判断是否存在好友关联
        AccountFriendsLink accountFriendsLink = accountFriendsLinkServiceI.getByAccountIdAndFriendId(userDto.getId(), dto.getFriendAccountId());
        if (accountFriendsLink != null) {//已经存在更新好友时间
            accountFriendsLinkServiceI.updateByPrimaryKeySelective(accountFriendsLink.setFriendStatus(1).setFriendTime(DateUtil.date()));
        }else {
            accountFriendsLinkServiceI.insert(new AccountFriendsLink()
                    .setAccountId(userDto.getId())
                    .setFriendStatus(EnumYesOrNo.是.getIndex())
                    .setFriendAccountId(dto.getFriendAccountId())
                    .setFriendTime(DateUtil.date()));
        }
        //更新好友
//        redisTemplateUtil.incr(RedisKey.getFriendsNum(userDto.getId()));
        redisTemplateUtil.sAdd(RedisKey.getFriendsNum(userDto.getId()), dto.getFriendAccountId());
    }

    /**
     * 会员解除好友
     *
     * @param dto
     */
    @Override
    @Transactional
    public void doRemoveFriend(MemberFriendAddOrRemoveRequest dto) {
        UserDto userDto = UserUtils.getUser();
        //判断好友信息
        this.checkAccountInfo(dto.getFriendAccountId());
        //判断是否存在好友关联
        AccountFriendsLink accountFriendsLink = accountFriendsLinkServiceI.getByAccountIdAndFriendId(userDto.getId(), dto.getFriendAccountId());
        if (accountFriendsLink != null) {//已经存在解除好友时间
            accountFriendsLinkServiceI.updateByPrimaryKeySelective(accountFriendsLink.setFriendStatus(0).setCancelTime(DateUtil.date()));
            //更新好友数量
//            redisTemplateUtil.decr(RedisKey.getFriendsNum(userDto.getId()), 1);
//            redisTemplateUtil.decrRejectNegative(RedisKey.getFriendsNum(userDto.getId()), 1);
            redisTemplateUtil.sRemove(RedisKey.getFriendsNum(userDto.getId()), dto.getFriendAccountId());
        }
    }

    /**
     * 最近玩的游戏类型和所有收到的礼物
     *
     * @param accountId
     * @param infoVO
     */
    private void getRecentGameAndReceivedGift(Long accountId, AccountDetailVO infoVO) {
        //最近玩的游戏类型
        List<AccountDetailVO.AccountRecentGameVO> gameInfos = roomGameAccountLinkServiceI.getRecentGameByAccountId(accountId);
        infoVO.setRecentGameList(gameInfos);
        //所有收到的礼物
        List<AccountDetailVO.ReceivedGiftInfo> receivedGiftList = accountGiftLinkServiceI.getReceivedGiftByAccountId(accountId);
        infoVO.setReceivedGiftInfoList(receivedGiftList);
    }

    /**
     * 会员详细信息
     *
     * @param accountId
     * @return
     */
    private AccountDetailVO getDetailVO(Long accountId, Long currentUserId) {
        AccountInfo accountInfo = this.checkAccountInfo(accountId);
        AccountDetailVO infoVO = BeanUtils.copyProperties(accountInfo, AccountDetailVO.class);
        //todo 访客数、粉丝数、关注数、朋友数、等级。。。
        //关注数量
        Long followNum = redisTemplateUtil.sSize(RedisKey.getUserFollowing(accountId, EnumUserFollowType.用户.getIndex()));
        infoVO.setFollowingNum(followNum == null ? 0 : followNum.intValue());
        //粉丝数量
        Long fansNum = redisTemplateUtil.sSize(RedisKey.getUserFollower(accountId, EnumUserFollowType.用户.getIndex()));
        infoVO.setFollowerNum(fansNum == null ? 0 : fansNum.intValue());
        //朋友数量
        Long friendsNum = redisTemplateUtil.sSize(RedisKey.getFriendsNum(accountId));
        infoVO.setFriends(friendsNum == null ? 0 : friendsNum.intValue());
        //访客数量
        if (currentUserId != null && currentUserId.longValue() != accountId.longValue()){//不是自己账号，访客数+1
            accountVisitorsInfoServiceI.doAdd(new AccountVisitorsInfoAddDTO().setAccountId(accountId));
        }
        String visitorsNum = redisTemplateStringUtil.getKey(RedisKey.getVisitorsNum(accountInfo.getId()));
        infoVO.setVisitors(StrUtil.isBlank(visitorsNum) ? 0 : Integer.valueOf(visitorsNum));
        return infoVO;
    }

    /**
     * 我的粉丝列表
     *
     * @param dto
     * @return
     */
    @Override
    public ResultTemplate followerList(AccountCenterPageListRequestDTO dto) {
        UserDto userDto = UserUtils.getUser();
        dto.setFollowType(EnumUserFollowType.用户.getIndex());
        dto.setTargetId(userDto.getId());
        PageHelper.startPage(dto.getCp(), dto.getRows());
        PageInfo<AccountFollowLinkVO> vo = new PageInfo(accountFollowLinkServiceI.getFollowerList(dto));
        //todo 判断是否互关
        JSONArray result = new JSONArray();
        if (ObjectUtil.length(vo.getList()) > 0) {
            for (AccountFollowLinkVO linkVO : vo.getList()) {
                JSONObject ob = JSONObjectUtil.parseObject(linkVO);
                Boolean isFollow = redisTemplateUtil.sIsMember(RedisKey.getUserFollowing(userDto.getId(), EnumUserFollowType.用户.getIndex()), linkVO.getAccountId());
                ob.put("followStatus", isFollow ? 1 : 0);
                result.add(ob);
            }
        }
        return new ResultTemplate(vo, result);
    }

    /**
     * 我关注列表
     *
     * @param dto
     * @return
     */
    @Override
    public ResultTemplate followingList(AccountCenterPageListRequestDTO dto) {
        UserDto userDto = UserUtils.getUser();
        dto.setFollowType(EnumUserFollowType.用户.getIndex());
        dto.setAccountId(userDto.getId());
        PageHelper.startPage(dto.getCp(), dto.getRows());
        PageInfo<AccountFollowLinkVO> vo = new PageInfo(accountFollowLinkServiceI.followingList(dto));
        return new ResultTemplate(vo, vo.getList());
    }

    /**
     * 访客列表
     *
     * @param dto
     * @return
     */
    @Override
    public ResultTemplate visitorsList(AccountCenterPageListRequestDTO dto) {
        UserDto userDto = UserUtils.getUser();
        PageInfo<AccountVisitorsInfoVO> data = accountVisitorsInfoServiceI.list(new VisitorsInfoPageListRequestDTO().setAccountId(userDto.getId()));
        //todo 当前用户是否关注
        JSONArray result = new JSONArray();
        if (ObjectUtil.length(data.getList()) > 0) {
            data.getList().forEach(vo ->{
                JSONObject ob = JSONObjectUtil.parseObject(vo);
                Boolean isFollow = redisTemplateUtil.sIsMember(RedisKey.getUserFollowing(userDto.getId(), EnumUserFollowType.用户.getIndex()), vo.getAccountId());
                ob.put("followStatus", isFollow ? 1 : 0);
                result.add(ob);
            });
        }
        return new ResultTemplate(data, result);
    }

    /**
     * 朋友列表
     *
     * @param pageListRequestDTO
     * @return
     */
    @Override
    public ResultTemplate friendsList(AccountCenterPageListRequestDTO pageListRequestDTO) {
        UserDto userDto = UserUtils.getUser();
        PageInfo<AccountFriendsLinkVO> data = accountFriendsLinkServiceI.list(new MemberFriendsPageListRequestDTO().setAccountId(userDto.getId()));
        //todo 当前用户是否关注
        JSONArray result = new JSONArray();
        if (ObjectUtil.length(data.getList()) > 0) {
            data.getList().forEach(vo ->{
                JSONObject ob = JSONObjectUtil.parseObject(BeanUtils.copyProperties(vo, AccountFollowLinkVO.class));
                Boolean isFollow = redisTemplateUtil.sIsMember(RedisKey.getUserFollowing(userDto.getId(), EnumUserFollowType.用户.getIndex()), vo.getAccountId());
                ob.put("followStatus", isFollow ? 1 : 0);
                result.add(ob);
            });
        }
        return new ResultTemplate(data, result);
    }

    /**
     * 获取用户信息
     *
     * @param id
     * @return
     */
    @Override
    public AccountInfoVO getDetail(Long id) {
        AccountInfo info = checkAccountInfo(id);
        return BeanUtils.copyProperties(info, AccountInfoVO.class);
    }

    /**
     * 判断用户信息
     *
     * @param accountId
     * @return
     */
    private AccountInfo checkAccountInfo(Long accountId) {
        AccountInfo info = baseDaoT.getById(accountId);
        if (info == null){
            throw new ChitchatException(CodeMsg.NULL_ERROR, "用户信息不存在");
        }
        return info;
    }

    /**
     * 刷新token
     *
     * @param dto
     * @return
     */
    @Override
    public ResultTemplate refreshToken(MemberRefreshTokenRequest dto) {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", AuthConstant.PORTAL_CLIENT_ID);
        params.put("client_secret", AuthConstant.PORTAL_CLIENT_SECRET);
        params.put("grant_type", "refresh_token");
        params.put("refresh_token", dto.getRefreshToken());
        ResultTemplate resultTemplate = feignAuthServiceI.getAccessToken(params);
        return resultTemplate;
    }

}
