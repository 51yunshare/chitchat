package com.chitchat.oms.dao;

import com.chitchat.common.base.BaseDaoI;
import com.chitchat.oms.bean.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDaoI extends BaseDaoI<OrderInfo> {
}
