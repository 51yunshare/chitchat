package com.chitchat.oms.bean.account;

import com.chitchat.common.base.BaseBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 余额
 *
 */
@Data
public class AccountBalanceInfo extends BaseBean {

    private static final long serialVersionUID = 1892077803006915617L;

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "账号id")
    private Long accountId;

    @ApiModelProperty(value = "金币余额")
    private BigDecimal coinsBalance;

    @ApiModelProperty(value = "钻石余额")
    private BigDecimal diamondsBalance;
}
