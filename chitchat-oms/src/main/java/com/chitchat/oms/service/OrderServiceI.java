package com.chitchat.oms.service;

import com.chitchat.common.base.BaseServicesI;
import com.chitchat.oms.bean.OrderInfo;
import com.chitchat.oms.dto.OrderInfoAddRequestDTO;

public interface OrderServiceI extends BaseServicesI<OrderInfo> {

    /**
     * 生成订单
     *
     * @param requestDTO
     */
    void doAdd(OrderInfoAddRequestDTO requestDTO);
}
