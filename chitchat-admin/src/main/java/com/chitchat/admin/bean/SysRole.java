package com.chitchat.admin.bean;

import com.chitchat.common.base.BaseBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色
 */
@Data
public class SysRole extends BaseBean {

    private static final long serialVersionUID = 7760532481061184682L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色编号")
    private String code;

    @ApiModelProperty(value = "角色状态（0-正常 1-停用）")
    private Integer status;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    private String memo;

}
