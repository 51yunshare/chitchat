package com.chitchat.portal.dto.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户访客信息新增请求
 *
 * Created by Js on 2022/8/27 .
 */
@Data
@ApiModel(value = "访客新增请求体")
@Accessors(chain = true)
public class AccountVisitorsInfoAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 访问账号id
     */
    @ApiModelProperty(value = "访问账号id", required = true, example = "111")
    @NotNull(message = "访问账号id不能为空")
    private Long accountId;
}
