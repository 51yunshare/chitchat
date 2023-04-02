package com.chitchat.portal.dto.index;

import com.chitchat.admin.api.enumerate.EnumRankStatType;
import com.chitchat.admin.api.enumerate.EnumRankType;
import com.chitchat.common.util.EnumUtil;
import com.chitchat.common.validation.EnumValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 排行榜
 *
 * Created by Js on 2022/8/7.
 **/
@Data
@ApiModel(value = "排行榜-请求体")
public class HomeRankListRequest implements Serializable {

    private static final long serialVersionUID = -8338282185563095368L;
    /**
     * 统计类型()
     */
    @ApiModelProperty(value = "榜单统计类型参数(1-房间贡献值 2-送礼者 3-收礼者)", required = true, example = "1")
    @NotNull(message = "榜单统计类型参数不能为空")
    @EnumValue(intValues = {1,2,3}, message = "榜单统计类型参数参数有误")
    private Integer statType;
    @ApiModelProperty(hidden = true)
    private EnumRankStatType enumRankStatType;
    /**
     * 排行榜类型
     */
    @ApiModelProperty(value = "排行榜类型日期参数(1-日榜 2-周榜 3-月榜)", required = true, example = "1")
    @NotNull(message = "排行榜类型日期参数不能为空")
    @EnumValue(intValues = {1,2,3}, message = "排行榜类型日期参数有误")
    private Integer rankType;
    @ApiModelProperty(hidden = true)
    private EnumRankType enumRankType;

    public void setRankType(Integer rankType) {
        this.rankType = rankType;
        this.enumRankType = EnumUtil.valueOf(EnumRankType.class, rankType);
    }

    public void setStatType(Integer statType) {
        this.statType = statType;
        this.enumRankStatType = EnumUtil.valueOf(EnumRankStatType.class, statType);
    }
}
