package com.chitchat.admin.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 商品列表
 *
 * Created by Js on 2022/8/7.
 **/
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("商品列表的请求对象")
public class GoodsInfoListRequestDTO implements Serializable {

    private static final long serialVersionUID = -8208071711676635225L;
    //搜索关键字
    @ApiModelProperty(value = "关键词搜素", example = "小小")
    private String likeName;
    @ApiModelProperty(value = "商品分类", notes = "1", example = "1")
    private Long goodsCategoryId;
}
