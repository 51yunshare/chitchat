package com.chitchat.portal.dto.account;

import com.chitchat.common.base.BasePageRequestModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 背包列表
 *
 * Created by Js on 2022/8/7.
 **/
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("背包列表请求对象")
public class BackpackPageListRequestDTO extends BasePageRequestModel {

    @ApiModelProperty(value = "商品分类id", notes = "商品分类", example = "1", dataType = "Long")
    private Long goodsCategoryId;

    @ApiModelProperty(value = "是否装备(1-是 0-否)", notes = "是否使用", example = "1", dataType = "Integer")
    private Integer usedStatus;

    @ApiModelProperty(value = "是否过期(1-是 0-否)", notes = "是否过期", example = "1", dataType = "Integer")
    private Integer overdueStatus;
}
