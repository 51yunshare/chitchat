package com.chitchat.portal.controller.account;

import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.dto.account.AccountCenterPageListRequestDTO;
import com.chitchat.portal.service.account.AccountInfoServiceI;
import com.chitchat.portal.vo.account.AccountFollowLinkVO;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 账号中心
 * <p>
 * Created by Js on 2022/9/19 .
 **/
@RestController
@RequestMapping("personal/center")
@Api(value = "AccountCenterController", tags = "会员中心管理")
public class AccountCenterController extends BaseController {
    /********* 访客列表、粉丝列表、关注列表、朋友列表 *********/

    @Resource
    private AccountInfoServiceI accountInfoServiceI;

    /**
     * 我的粉丝列表
     *
     * @param pageListRequestDTO
     * @return
     */
    @ApiOperation(value = "粉丝列表", response = AccountFollowLinkVO.class)
    @ApiOperationSupport(ignoreParameters = {"userInfo"})
    @GetMapping(value = "/followerList")
    public ResultTemplate followerList(AccountCenterPageListRequestDTO pageListRequestDTO) {
        return accountInfoServiceI.followerList(pageListRequestDTO);
    }

    /**
     * 我关注列表
     *
     * @param pageListRequestDTO
     * @return
     */
    @ApiOperation(value = "关注列表", response = AccountFollowLinkVO.class)
    @ApiOperationSupport(ignoreParameters = {"userInfo"})
    @GetMapping(value = "/followingList")
    public ResultTemplate followingList(AccountCenterPageListRequestDTO pageListRequestDTO) {
        return accountInfoServiceI.followingList(pageListRequestDTO);
    }

    /**
     * 访客列表
     *
     * @param pageListRequestDTO
     * @return
     */
    @ApiOperation(value = "访客列表", response = AccountFollowLinkVO.class)
    @ApiOperationSupport(ignoreParameters = {"userInfo"})
    @GetMapping(value = "/visitorsList")
    public ResultTemplate visitorsList(AccountCenterPageListRequestDTO pageListRequestDTO) {
        return accountInfoServiceI.visitorsList(pageListRequestDTO);
    }

    /**
     * 朋友列表
     *
     * @param pageListRequestDTO
     * @return
     */
    @ApiOperation(value = "朋友列表", response = AccountFollowLinkVO.class)
    @ApiOperationSupport(ignoreParameters = {"userInfo"})
    @GetMapping(value = "/friendsList")
    public ResultTemplate friendsList(AccountCenterPageListRequestDTO pageListRequestDTO) {
        return accountInfoServiceI.friendsList(pageListRequestDTO);
    }

    /**
     * 个人中心列表
     *
     * @param pageListRequestDTO
     * @return
     */
    @ApiOperation(value = "个人中心列表(粉丝/关注/访客/朋友)", response = AccountFollowLinkVO.class)
    @ApiOperationSupport(ignoreParameters = {"userInfo"})
    @GetMapping(value = "/centerList")
    public ResultTemplate centerList(@Validated AccountCenterPageListRequestDTO pageListRequestDTO) {
        switch (pageListRequestDTO.getEnumAccountCenterType()){
            case FOLLOWER:
                return accountInfoServiceI.followerList(pageListRequestDTO);
            case FOLLOWING:
                return accountInfoServiceI.followingList(pageListRequestDTO);
            case VISITORS:
                return accountInfoServiceI.visitorsList(pageListRequestDTO);
            default:
                return accountInfoServiceI.friendsList(pageListRequestDTO);
        }
    }
}
