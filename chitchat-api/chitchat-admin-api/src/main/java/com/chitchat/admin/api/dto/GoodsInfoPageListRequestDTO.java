package com.chitchat.admin.api.dto;

import com.chitchat.common.base.BasePageRequestModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 商品列表
 *
 * Created by Js on 2022/8/7.
 **/
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("商品列表请求对象")
public class GoodsInfoPageListRequestDTO extends BasePageRequestModel {

    @ApiModelProperty(value = "商品分类", notes = "1", example = "1")
    private Long goodsCategoryId;
}
