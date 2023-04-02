package com.chitchat.portal.controller.account;

import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.Result;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.base.UserDto;
import com.chitchat.portal.api.dto.MemberDTO;
import com.chitchat.portal.api.vo.AccountInfoVO;
import com.chitchat.portal.dto.account.*;
import com.chitchat.portal.service.account.AccountInfoServiceI;
import com.chitchat.portal.vo.account.AccountDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 成员
 * <p>
 * Created by Js on 2022/7/27 .
 **/
@RestController
@RequestMapping("sso")
@Slf4j
@Api(value = "MemberController", tags = "会员登录注册管理")
public class AccountInfoController extends BaseController {

    @Autowired
    private AccountInfoServiceI accountInfoServiceI;

    /**
     * 登录
     *
     * @param loginRequest
     * @return
     */
    @ApiOperation("会员登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultTemplate login(@RequestBody @Validated MemberLoginRequest loginRequest) {
        return accountInfoServiceI.login(loginRequest);
    }

    /**
     * 刷新token
     *
     * @param refreshTokenRequest
     * @return
     */
    @ApiOperation("会员刷新Token")
    @RequestMapping(value = "/refreshToken", method = RequestMethod.POST)
    public ResultTemplate refreshToken(@RequestBody @Validated MemberRefreshTokenRequest refreshTokenRequest) {
        return accountInfoServiceI.refreshToken(refreshTokenRequest);
    }

    @ApiOperation("手机号判断是否注册")
    @GetMapping(value = "/verifyMobile/{mobile}")
    public ResultTemplate verifyMobile(@ApiParam("手机号") @PathVariable String mobile) {
        return accountInfoServiceI.verifyMobile(mobile);
    }

    /**
     * 会员注册之后默认下发token
     *
     * @param registerRequest
     * @return
     */
    @ApiOperation("会员注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResultTemplate doRegister(@Validated MemberRegisterRequest registerRequest) {
        return accountInfoServiceI.doRegister(registerRequest);
    }

    /**
     * 第三方授权登录/注册
     *
     * @param authLoginRequest
     * @return
     */
    @ApiOperation("第三方授权登录/注册")
    @RequestMapping(value = "/socialAuth", method = RequestMethod.POST)
    public ResultTemplate doSocialAuthLogin(@RequestBody @Validated SocialAuthLoginRequest authLoginRequest) {
        return accountInfoServiceI.doSocialAuthLogin(authLoginRequest);
    }

    @ApiOperation(value = "根据用户名获取通用用户信息", hidden = true)
    @RequestMapping(value = "/loadByUsername", method = RequestMethod.GET)
    public UserDto loadUserByUsername(@RequestParam String username) {
        UserDto userDto = accountInfoServiceI.loadUserByUsername(username);
        return userDto;
    }

    @ApiOperation(value = "根据 openid 获取会员认证信息", hidden = true)
    @GetMapping("/openid")
    public UserDto getByOpenid(@ApiParam("第三方身份标识") @RequestParam("openId") String openId, @RequestParam("identityType") Integer identityType) {
        UserDto userDto = accountInfoServiceI.getByOpenid(openId, identityType);
        return userDto;
    }

    @ApiOperation(value = "新增会员", hidden = true)
    @PostMapping("/addMember")
    public ResultTemplate addMember(@RequestBody @ApiIgnore MemberDTO member) {
        Long memberId = accountInfoServiceI.addMember(member);
        return this.success(memberId);
    }

    @ApiOperation(value = "根据手机号获取通用用户信息", hidden = true)
    @GetMapping("/loadUserByMobile")
    public UserDto loadUserByMobile(@RequestParam String mobile) {
        return accountInfoServiceI.loadUserByMobile(mobile);
    }

    /**
     * 获取当前会员信息
     *
     * @return
     */
    @ApiOperation("获取当前会员信息")
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public ResultTemplate info() {
        return accountInfoServiceI.getCurrentMember();
    }

    /**
     * 修改
     *
     * @param updateRequest
     * @return
     */
    @ApiOperation("会员编辑")
    @PostMapping("/update")
    public ResultTemplate doUpdate(@Validated MemberUpdateRequest updateRequest) {
        accountInfoServiceI.doUpdate(updateRequest);
        return this.success();
    }

    /**
     * 会员关注
     *
     * @param followRequest
     * @return
     */
    @ApiOperation("会员关注")
    @PostMapping("/follow")
    public ResultTemplate doFollow(@RequestBody @Validated MemberFollowRequest followRequest) {
        accountInfoServiceI.doFollow(followRequest);
        return this.success();
    }

    /**
     * 会员取消关注
     *
     * @param followRequest
     * @return
     */
    @ApiOperation("会员取消关注")
    @PostMapping("/cancelFollow")
    public ResultTemplate doCancelFollow(@RequestBody @Validated MemberFollowRequest followRequest) {
        accountInfoServiceI.doCancelFollow(followRequest);
        return this.success();
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @ApiOperation(value = "获取用户信息", hidden = true)
    @GetMapping(value = "/getById/{id}")
    public AccountInfoVO getDetail(@PathVariable Long id) {
        return accountInfoServiceI.getDetail(id);
    }

    /**
     * 获取会员信息
     *
     * @return
     */
    @ApiOperation(value = "获取会员信息", response = AccountDetailVO.class)
    @GetMapping(value = "/getDetail/{id}")
    public Result<AccountDetailVO> getDetailById(@PathVariable Long id) {
        return Result.success(accountInfoServiceI.getAccountDetailVO(id));
    }

    /**
     * 会员添加好友
     *
     * @param friendAddRequest
     * @return
     */
    @ApiOperation("会员添加好友")
    @PostMapping("/addFriend")
    public ResultTemplate doAddFriend(@RequestBody @Validated MemberFriendAddOrRemoveRequest friendAddRequest) {
        accountInfoServiceI.doAddFriend(friendAddRequest);
        return this.success();
    }
    /**
     * 解除好友
     *
     * @param friendAddRequest
     * @return
     */
    @ApiOperation("会员解除好友")
    @PostMapping("/removeFriend")
    public ResultTemplate doRemoveFriend(@RequestBody @Validated MemberFriendAddOrRemoveRequest friendAddRequest) {
        accountInfoServiceI.doRemoveFriend(friendAddRequest);
        return this.success();
    }
}
