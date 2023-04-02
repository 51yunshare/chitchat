package com.chitchat.portal.service.account;

import com.chitchat.common.base.BaseServicesI;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.base.UserDto;
import com.chitchat.portal.api.dto.MemberDTO;
import com.chitchat.portal.api.vo.AccountInfoVO;
import com.chitchat.portal.bean.account.AccountInfo;
import com.chitchat.portal.dto.index.HomeSearchPageListRequest;
import com.chitchat.portal.dto.account.*;
import com.chitchat.portal.vo.account.AccountDetailVO;
import com.github.pagehelper.PageInfo;

public interface AccountInfoServiceI extends BaseServicesI<AccountInfo> {

    /**
     * 登录
     *
     * @param loginRequest
     * @return
     */
    ResultTemplate login(MemberLoginRequest loginRequest);

    /**
     * 第三方授权登录/注册
     *
     * @param authLoginRequest
     * @return
     */
    ResultTemplate doSocialAuthLogin(SocialAuthLoginRequest authLoginRequest);

    /**
     * 根据用户名获取通用用户信息
     *
     * @param username
     * @return
     */
    UserDto loadUserByUsername(String username);

    /**
     * 会员注册
     *
     * @param registerRequest
     * @return
     */
    ResultTemplate doRegister(MemberRegisterRequest registerRequest);

    /**
     * 获取当前会员信息
     *
     * @return
     */
    ResultTemplate getCurrentMember();

    /**
     * 通过openId获取会员信息
     *
     * @param openid
     * @param identityType
     * @return
     */
    UserDto getByOpenid(String openid, Integer identityType);

    /**
     * 会员新增
     *
     * @param member
     * @return
     */
    Long addMember(MemberDTO member);

    /**
     * 手机号判断是否注册
     *
     * @param mobile
     * @return
     */
    ResultTemplate verifyMobile(String mobile);

    /**
     * 会员编辑
     *
     * @param updateRequest
     */
    void doUpdate(MemberUpdateRequest updateRequest);

    /**
     * 根据手机号获取通用用户信息
     *
     * @param mobile
     * @return
     */
    UserDto loadUserByMobile(String mobile);

    /**
     * 搜索
     *
     * @return
     * @param pageListRequest
     */
    PageInfo selectBySearch(HomeSearchPageListRequest pageListRequest);

    /**
     * 会员关注
     *
     * @param followRequest
     */
    void doFollow(MemberFollowRequest followRequest);

    /**
     * 会员取消关注
     *
     * @param followRequest
     */
    void doCancelFollow(MemberFollowRequest followRequest);

    /**
     * 我的粉丝列表
     *
     * @param pageListRequestDTO
     * @return
     */
    ResultTemplate followerList(AccountCenterPageListRequestDTO pageListRequestDTO);

    /**
     * 我关注列表
     *
     * @param pageListRequestDTO
     * @return
     */
    ResultTemplate followingList(AccountCenterPageListRequestDTO pageListRequestDTO);

    /**
     * 访客列表
     *
     * @param pageListRequestDTO
     * @return
     */
    ResultTemplate visitorsList(AccountCenterPageListRequestDTO pageListRequestDTO);

    /**
     * 朋友列表
     *
     * @param pageListRequestDTO
     * @return
     */
    ResultTemplate friendsList(AccountCenterPageListRequestDTO pageListRequestDTO);

    /**
     * 获取用户信息
     *
     * @param id
     * @return
     */
    AccountInfoVO getDetail(Long id);

    /**
     * 刷新token
     *
     * @param dto
     * @return
     */
    ResultTemplate refreshToken(MemberRefreshTokenRequest dto);

    /**
     * 获取会员详细信息
     *
     * @param accountId
     * @return
     */
    AccountDetailVO getAccountDetailVO(Long accountId);

    /**
     * 会员添加好友
     *
     * @param friendAddRequest
     */
    void doAddFriend(MemberFriendAddOrRemoveRequest friendAddRequest);

    /**
     * 会员解除好友
     *
     * @param friendAddRequest
     */
    void doRemoveFriend(MemberFriendAddOrRemoveRequest friendAddRequest);
}
