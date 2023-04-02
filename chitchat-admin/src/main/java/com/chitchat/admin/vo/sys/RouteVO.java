package com.chitchat.admin.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 菜单路由
 */
@Data
@ApiModel(value = "菜单路由-返回体")
public class RouteVO {

    @ApiModelProperty(value = "路由路径(浏览器地址栏路径)")
    private String path;

    @ApiModelProperty(value = "组件路径(vue页面完整路径，省略.vue后缀)")
    private String component;

    @ApiModelProperty(value = "菜单名称")
    private String name;
    @ApiModelProperty(value = "元数据信息")
    private Meta meta;

    @Data
    @ApiModel(value = "元数据")
    public static class Meta {

        @ApiModelProperty(value = "标题")
        private String title;

        @ApiModelProperty(value = "图标")
        private String icon;

        @ApiModelProperty(value = "是否隐藏")
        private Boolean hidden;

        /**
         * 如果设置为 true，目录没有子节点也会显示
         */
        private Boolean alwaysShow;

        @ApiModelProperty(value = "角色")
        private List<String> roles;
        /**
         * 页面缓存开启状态
         */
        private Boolean keepAlive;
    }

    @ApiModelProperty(value = "子级菜单")
    private List<RouteVO> children;
}
