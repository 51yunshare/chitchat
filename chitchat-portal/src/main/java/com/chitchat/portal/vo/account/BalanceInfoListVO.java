package com.chitchat.portal.vo.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 余额
 *
 */
@Data
@ApiModel(value = "余额信息")
public class BalanceInfoListVO implements Serializable {
    private static final long serialVersionUID = 437453309720493241L;

    @ApiModelProperty(value = "账号id")
    private Long accountId;

    @ApiModelProperty(value = "金币余额")
    private BigDecimal coinsBalance;

    @ApiModelProperty(value = "钻石余额")
    private BigDecimal diamondsBalance;
}
