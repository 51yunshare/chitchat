package com.chitchat.admin.controller.sys;

import com.chitchat.admin.service.SysRoleServiceI;
import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.ResultTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Js on 2022/10/19 .
 **/
@Api(tags = "后台菜单权限")
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class SysRolesController extends BaseController {

    private final SysRoleServiceI sysRoleServiceI;

    @ApiOperation(value = "用户角色下拉")
    @GetMapping("/selectList")
    public ResultTemplate selectList() {
        return this.success(sysRoleServiceI.selectList());
    }
}
