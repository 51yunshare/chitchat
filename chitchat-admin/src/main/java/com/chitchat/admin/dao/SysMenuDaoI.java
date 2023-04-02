package com.chitchat.admin.dao;

import com.chitchat.admin.bean.SysMenu;
import com.chitchat.admin.vo.sys.SysMenuVO;
import com.chitchat.common.base.BaseDaoI;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMenuDaoI extends BaseDaoI<SysMenu> {

    /**
     * 用户权限集合
     *
     * @param userId
     * @return
     */
    List<SysMenu> listMenuByUserId(@Param("userId") Long userId);

    /**
     * 所有菜单权限
     *
     * @return
     */
    List<SysMenuVO> listRoutes();
}
