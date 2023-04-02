package com.chitchat.portal.bean.account;

import com.chitchat.common.base.BaseBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AccountLevel extends BaseBean {

    private static final long serialVersionUID = -343753752594909044L;

    private Long id;

    @ApiModelProperty(value = "等级类型(1-用户等级2-游戏等级3-VIP等级4-会员等级)")
    private Integer type;

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

    @ApiModelProperty(value = "每次评价获取的成长值")
    private Integer commentGrowthPoint;

    @ApiModelProperty(value = "是否有签到特权")
    private Integer privilegeSignIn;

    @ApiModelProperty(value = "是否有评论获奖励特权")
    private Integer privilegeComment;

    @ApiModelProperty(value = "是否有专享活动特权")
    private Integer privilegePromotion;

    @ApiModelProperty(value = "是否有会员价格特权")
    private Integer privilegeMemberPrice;

}
