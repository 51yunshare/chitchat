package com.chitchat.admin.bean;

import com.chitchat.common.base.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统配置信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "系统配置信息")
public class SysBaseConfig extends BaseBean {

    private static final long serialVersionUID = -2308519797500752489L;

    @ApiModelProperty(value = "参数主键")
    private Long id;

    @ApiModelProperty(value = "参数名称")
    private String configName;

    @ApiModelProperty(value = "参数键名")
    private String configKey;

    @ApiModelProperty(value = "参数键值")
    private String configValue;

    @ApiModelProperty(value = "系统内置(1-是 0-否)")
    private Integer configType;

    @ApiModelProperty(value = "创建者")
    private String creator;
}
