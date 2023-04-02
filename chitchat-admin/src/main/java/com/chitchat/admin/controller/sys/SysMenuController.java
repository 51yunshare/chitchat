package com.chitchat.admin.controller.sys;

import com.chitchat.admin.service.SysMenuServiceI;
import com.chitchat.admin.vo.sys.RouteVO;
import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Js on 2022/10/19 .
 **/
@Api(tags = "后台菜单权限")
@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor
public class SysMenuController extends BaseController {

    private final SysMenuServiceI sysMenuServiceI;

    @ApiOperation(value = "路由列表")
    @GetMapping("/routes")
    public Result listRoutes() {
        List<RouteVO> routeList = sysMenuServiceI.listRoutes();
        return Result.success(routeList);
    }
}
