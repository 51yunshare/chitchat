package com.chitchat.portal.dto.index;

import com.chitchat.common.base.BasePageRequestModel;
import com.chitchat.common.util.EnumUtil;
import com.chitchat.common.validation.EnumValue;
import com.chitchat.portal.enumerate.EnumHomeSearchType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 搜索
 *
 * Created by Js on 2022/8/7.
 **/
@Data
@ApiModel
public class HomeSearchPageListRequest extends BasePageRequestModel {
    /**
     * 搜索类型、房间名称/用户名
     */
    @ApiModelProperty(value = "搜索类型参数(1-Room 2-User)")
    @NotNull(message = "搜索类型参数不能为空")
    @EnumValue(intValues = {1,2}, message = "搜索类型参数有误")
    private Integer type;
    @ApiModelProperty(hidden = true)
    private EnumHomeSearchType homeSearchType;

    public void setType(Integer type) {
        this.type = type;
        this.homeSearchType = EnumUtil.valueOf(EnumHomeSearchType.class, type);
    }
}
