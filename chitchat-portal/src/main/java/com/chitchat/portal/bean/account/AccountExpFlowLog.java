package com.chitchat.portal.bean.account;

import com.chitchat.common.base.BaseBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户经验值记录
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountExpFlowLog extends BaseBean {

    private static final long serialVersionUID = -4056170694126704658L;

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "账号id")
    private Long accountId;

    @ApiModelProperty(value = "经验类型(1-用户等级2-游戏等级3-VIP等级4-贵族等级)")
    private Integer expType;

    @ApiModelProperty(value = "改变类型：0->增加；1->减少")
    private Integer changeType;
    /**
     * 本次经验乘数/因子(多少分钟/金币)
     *
     */
    @ApiModelProperty(value = "本次经验乘数/因子(多少分钟/金币)")
    private Integer expMultiplier;

    @ApiModelProperty(value = "积分改变数量(正/负)")
    private Integer changeCount;

    @ApiModelProperty(value = "积分来源：0->送礼 1-在房间时长 2->游戏 3->管理员修改")
    private Integer sourceType;
    //计算经验
    @ApiModelProperty(value = "经验日期")
    private Date expDate;

    @ApiModelProperty(value = "操作人员")
    private String createdUser;

    @ApiModelProperty(value = "操作备注")
    private String operateNote;
}
