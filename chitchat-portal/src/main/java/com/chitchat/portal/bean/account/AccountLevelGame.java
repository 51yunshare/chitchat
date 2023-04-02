package com.chitchat.portal.bean.account;

import com.chitchat.common.base.BaseBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AccountLevelGame extends BaseBean {

    private static final long serialVersionUID = 9153341959100056746L;

    private Long id;

    @ApiModelProperty(value = "等级名称")
    private String levelName;

    @ApiModelProperty(value = "初始成长值")
    private Integer startGrowthPoint;

    @ApiModelProperty(value = "截止成长值")
    private Integer growthPoint;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "是否为默认等级：0->不是；1->是")
    private Boolean defaultStatus;

}
