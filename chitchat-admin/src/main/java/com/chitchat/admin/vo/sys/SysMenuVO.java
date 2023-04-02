package com.chitchat.admin.vo.sys;

import com.chitchat.admin.enumerate.EnumMenuType;
import com.chitchat.common.util.EnumUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SysMenuVO implements Serializable {

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
    /**
     * 最后修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifiedTime;

    private Integer deleted;

    //角色
    private List<String> roles;
}
