package com.chitchat.admin.bean;

import com.chitchat.admin.enumerate.EnumMenuType;
import com.chitchat.common.base.BaseBean;
import com.chitchat.common.util.EnumUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysMenu extends BaseBean {

    private static final long serialVersionUID = -7304481898025625459L;

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "父级菜单")
    private Long parentId;

    @ApiModelProperty(value = "菜单类型(1:目录;2:菜单;3:按钮)")
    private Integer type;
    private EnumMenuType enumMenuType;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "菜单权限")
    private String permission;

    @ApiModelProperty(value = "按菜单编码排序")
    private Integer sort;

    @ApiModelProperty(value = "路由路径(浏览器地址栏路径)")
    private String path;

    @ApiModelProperty(value = "组件路径(vue页面完整路径，省略.vue后缀)")
    private String component;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "状态(1:禁用;0:开启)")
    private Integer showStatus;

    public void setType(Integer type) {
        this.type = type;
        this.enumMenuType = type == null ? null : EnumUtil.valueOf(EnumMenuType.class, type);
    }
}
