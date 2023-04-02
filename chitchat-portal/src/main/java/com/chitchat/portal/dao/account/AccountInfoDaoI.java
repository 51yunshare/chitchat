package com.chitchat.portal.dao.account;

import com.chitchat.common.base.BaseDaoI;
import com.chitchat.portal.bean.account.AccountFollowLink;
import com.chitchat.portal.bean.account.AccountInfo;
import com.chitchat.portal.bean.account.AccountLoginAuthInfo;
import com.chitchat.portal.dto.index.HomeSearchPageListRequest;
import com.chitchat.portal.vo.index.SearchUserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountInfoDaoI extends BaseDaoI<AccountInfo> {

    /**
     * 获取用户信息
     *
     * @param username
     * @return
     */
    AccountInfo loadUserByUsername(@Param("username") String username);

    /**
     * 获取用户信息通过手机号
     *
     * @param mobile
     * @return
     */
    AccountInfo getByMobile(@Param("mobile") String mobile);

    /**
     * 通过openId获取会员信息
     *
     * @param openid
     * @param identityType
     * @return
     */
    AccountInfo loadUserByOpenId(@Param("openid") String openid, @Param("identityType") Integer identityType);

    /**
     * 新增第三方扩展表
     *
     * @param accountLoginAuthInfo
     */
    void insertAccountLoginAuth(AccountLoginAuthInfo accountLoginAuthInfo);

    /**
     * 首页搜索用户
     *
     * @param pageListRequest
     * @return
     */
    List<SearchUserVO> selectBySearch(HomeSearchPageListRequest pageListRequest);

    /**
     * 查询用户关注表通过用户id
     *
     * @param uerId
     * @param targetId
     * @param type
     * @return
     */
    AccountFollowLink getFansByUserId(@Param("userId") Long uerId, @Param("targetId") Long targetId,
                                      @Param("type") int type);

    /**
     * 新增用户关注关系
     *
     * @param accountFollowLink
     */
    void insertAccountFollowLink(AccountFollowLink accountFollowLink);

    /**
     * 修改用户关注关系
     *
     * @param accountFollowLink
     */
    void updateAccountFollowLink(AccountFollowLink accountFollowLink);

    /**
     * 获取用户信息-手机号或者用户登录名
     *
     * @param username
     * @return
     */
    AccountInfo getByMobileOrUsername(@Param("username") String username);
}
