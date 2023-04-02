package com.chitchat.admin.controller.sys;

import com.chitchat.admin.dto.SysUserAddRequestDTO;
import com.chitchat.admin.dto.SysUserLoginRequest;
import com.chitchat.admin.dto.SysUserPageListRequest;
import com.chitchat.admin.dto.SysUserUpdateRequestDTO;
import com.chitchat.admin.service.SysUserInfoServiceI;
import com.chitchat.admin.vo.sys.LoginUserVO;
import com.chitchat.admin.vo.sys.UserDetailVO;
import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.Result;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.base.UserDto;
import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "后台用户")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class SysUserController extends BaseController {

    private final SysUserInfoServiceI sysUserInfoServiceI;

    @ApiOperation(value = "根据用户名获取通用用户信息", hidden = true)
    @RequestMapping(value = "/loadByUsername", method = RequestMethod.GET)
    public UserDto loadUserByUsername(@RequestParam String username) {
        UserDto userDto = sysUserInfoServiceI.loadUserByUsername(username);
        return userDto;
    }
    /**
     * 登录
     *
     * @param loginRequest
     * @return
     */
    @ApiOperation("后台登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultTemplate login(@RequestBody @Validated SysUserLoginRequest loginRequest) {
        return sysUserInfoServiceI.login(loginRequest);
    }

    /**
     * 当前用户信息
     *
     * @return
     */
    @ApiOperation("当前用户信息")
    @GetMapping(value = "/me")
    public Result<LoginUserVO> getLoginInfo() {
        LoginUserVO vo = sysUserInfoServiceI.getLoginInfo();
        return Result.success(vo);
    }

    /**
     * 用户列表
     *
     * @return
     */
    @ApiOperation("用户列表")
    @ApiOperationSupport(ignoreParameters = {"userInfo"})
    @GetMapping(value = "/user/list")
    public ResultTemplate listPages(SysUserPageListRequest pageListRequest) {
        PageInfo data = sysUserInfoServiceI.list(pageListRequest);
        return success(data, data.getList());
    }

    /**
     * 用户详情
     *
     * @return
     */
    @ApiOperation("用户详情")
    @GetMapping(value = "/user/{id}")
    public Result<UserDetailVO> getUserDetail(@PathVariable(value = "id") Long id) {
        return Result.success(sysUserInfoServiceI.getDetail(id));
    }

    /**
     * 用户新增
     *
     * @return
     */
    @ApiOperation("用户新增")
    @PostMapping(value = "/user/add")
    public ResultTemplate doAdd(@RequestBody @Validated SysUserAddRequestDTO addRequestDTO) {
        sysUserInfoServiceI.doAdd(addRequestDTO);
        return this.success();
    }

    @ApiOperation(value = "修改用户")
    @PutMapping(value = "/user/update")
    public ResultTemplate doUpdate(@RequestBody @Validated SysUserUpdateRequestDTO updateRequestDTO) {
        sysUserInfoServiceI.doUpdate(updateRequestDTO);
        return success();
    }

    @ApiOperation(value = "删除用户")
//    @RequirePerms(value = "sys:user:delete")
    @DeleteMapping("/user/{ids}")
    public ResultTemplate deleteUsers(@ApiParam("用户ID，多个以英文逗号(,)分割") @PathVariable String ids) {
        sysUserInfoServiceI.deleteUsers(ids);
        return success();
    }

    @ApiOperation(value = "修改用户密码")
    @PatchMapping(value = "/user/{userId}/password")
    public ResultTemplate updateUserPassword(@ApiParam("用户ID") @PathVariable Long userId, @RequestParam String password) {
        sysUserInfoServiceI.updateUserPassword(userId, password);
        return success();
    }

    @ApiOperation(value = "修改用户状态")
    @PatchMapping(value = "/user/{userId}/status")
    public ResultTemplate updateUserStatus(@ApiParam("用户ID") @PathVariable Long userId, @RequestParam Integer status) {
        sysUserInfoServiceI.updateUserStatus(userId, status);
        return success();
    }
}
