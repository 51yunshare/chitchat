package com.chitchat.admin.service;


import com.chitchat.admin.bean.SysMenu;
import com.chitchat.admin.vo.sys.RouteVO;
import com.chitchat.common.base.BaseServicesI;

import java.util.List;

/**
 * 系统菜单权限
 *
 * Created by Js on 2022/8/10.
 */
public interface SysMenuServiceI extends BaseServicesI<SysMenu> {

    /**
     * 用户权限集合
     *
     * @param userId
     * @return
     */
    List<SysMenu> listMenuByUserId(Long userId);

    /**
     * 路由列表
     *
     * @return
     */
    List<RouteVO> listRoutes();
}
