package com.chitchat.admin.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.chitchat.admin.bean.SysMenu;
import com.chitchat.admin.dao.SysMenuDaoI;
import com.chitchat.admin.enumerate.EnumMenuType;
import com.chitchat.admin.service.SysMenuServiceI;
import com.chitchat.admin.vo.sys.RouteVO;
import com.chitchat.admin.vo.sys.SysMenuVO;
import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.common.constant.SystemConstants;
import com.chitchat.common.enumerate.EnumYesOrNo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 后台菜单权限serviceImpl
 *
 * Created by Js on 2022/8/10.
 */
@Service
public class SysMenuServiceImpl extends BaseServicesImpl<SysMenu, SysMenuDaoI> implements SysMenuServiceI {

    /**
     * 用户权限集合
     *
     * @param userId
     * @return
     */
    @Override
    public List<SysMenu> listMenuByUserId(Long userId) {
        return baseDaoT.listMenuByUserId(userId);
    }

    /**
     * 路由列表
     *
     * @return
     */
    @Override
    public List<RouteVO> listRoutes() {
        List<SysMenuVO> menuList = baseDaoT.listRoutes();
        List<RouteVO> list = recurRoutes(SystemConstants.ROOT_MENU_ID, menuList);
        return list;
    }

    /**
     * 递归生成菜单路由层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private List<RouteVO> recurRoutes(Long parentId, List<SysMenuVO> menuList) {
        List<RouteVO> list = new ArrayList<>();
        Optional.ofNullable(menuList).ifPresent(menus -> menus.stream().filter(menu -> menu.getParentId().equals(parentId))
                .forEach(menu -> {
                    if (!menu.getEnumMenuType().equals(EnumMenuType.BUTTON)) {
                        RouteVO routeVO = new RouteVO();
                        EnumMenuType menuTypeEnum = menu.getEnumMenuType();
                        if (EnumMenuType.MENU.equals(menuTypeEnum)) {
                            routeVO.setName(menu.getPath()); //  根据name路由跳转 this.$router.push({name:xxx})
                        }
                        routeVO.setPath(menu.getPath()); // 根据path路由跳转 this.$router.push({path:xxx})
//                    routeVO.setRedirect(menu.getRedirect());
                        routeVO.setComponent(menu.getComponent());

                        RouteVO.Meta meta = new RouteVO.Meta();
                        meta.setTitle(menu.getMenuName());
                        meta.setIcon(menu.getIcon());
                        meta.setRoles(menu.getRoles());
                        meta.setHidden(EnumYesOrNo.是.getIndex() == menu.getShowStatus().intValue());
                        meta.setKeepAlive(true);

                        routeVO.setMeta(meta);
                        List<RouteVO> children = recurRoutes(menu.getId(), menuList);
                        // 含有子节点的目录设置为可见
                        boolean alwaysShow = CollectionUtil.isNotEmpty(children) && children.stream().anyMatch(item -> item.getMeta().getHidden().equals(false));
                        meta.setAlwaysShow(alwaysShow);
                        routeVO.setChildren(children);
                        list.add(routeVO);
                    }
                }));
        return list;
    }
}
