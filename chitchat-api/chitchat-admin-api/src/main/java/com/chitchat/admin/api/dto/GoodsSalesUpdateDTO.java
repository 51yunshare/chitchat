package com.chitchat.admin.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by Js on 2022/9/29 .
 **/
@Data
@Accessors(chain = true)
public class GoodsSalesUpdateDTO implements Serializable {
    private static final long serialVersionUID = -176787851049938984L;
    /**
     * 商品id
     */
    private Long goodsId;
    /**
     * 新增销量
     */
    private Integer num;
}
