package com.chitchat.admin.bean;

import com.chitchat.common.base.BaseBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 排行榜信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RankInfo extends BaseBean {

    private static final long serialVersionUID = 7950224837231650261L;

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "排行榜统计类型(房间贡献值/送礼/收礼)")
    private Integer rankStatType;

    @ApiModelProperty(value = "排行榜类型(日榜/周榜/月榜)")
    private Integer rankType;

    @ApiModelProperty(value = "排行榜统计日期")
    private String rankTime;

    @ApiModelProperty(value = "房间/用户id")
    private Long targetId;

    @ApiModelProperty(value = "排行榜值")
    private Long rankScore;
}
