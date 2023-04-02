package com.chitchat.admin.dto;

import com.chitchat.admin.api.enumerate.EnumRankType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Js on 2022/11/11 .
 **/
@Data
@ApiModel(value = "排行榜初始化")
public class RankInfoInitRequestDTO implements Serializable {
    private static final long serialVersionUID = -3518780975575634830L;
    /**
     * 排行榜类型
     */
    @ApiModelProperty(value = "排行榜类型日期参数(1-日榜 2-周榜 3-月榜)", required = true, example = "1")
    @NotNull(message = "排行榜类型日期参数不能为空")
    private EnumRankType enumRankType;
}
