package com.chitchat.admin.vo.base;

import com.chitchat.common.enumerate.EnumYesOrNo;
import com.chitchat.common.util.EnumUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统配置信息
 */
@Data
@ApiModel(value = "系统配置信息")
public class SysBaseConfigListVO implements Serializable {

    private static final long serialVersionUID = 788338039148894998L;

    @ApiModelProperty(value = "参数主键")
    private Long id;

    @ApiModelProperty(value = "参数名称")
    private String configName;

    @ApiModelProperty(value = "参数键名")
    private String configKey;

    @ApiModelProperty(value = "参数键值")
    private String configValue;

    @ApiModelProperty(value = "参数类型-系统内置(1-是 0-否)")
    private Integer configType;
    @ApiModelProperty(value = "参数类型名称")
    private EnumYesOrNo enumConfigType;

    @ApiModelProperty(value = "创建者")
    private String creator;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    public void setConfigType(Integer configType) {
        this.configType = configType;
        this.enumConfigType = configType == null ? null : EnumUtil.valueOf(EnumYesOrNo.class, configType);
    }
}
