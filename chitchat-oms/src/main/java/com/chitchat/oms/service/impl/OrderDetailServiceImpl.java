package com.chitchat.oms.service.impl;

import com.chitchat.admin.api.feign.FeignGoodsInfoServiceI;
import com.chitchat.admin.api.vo.GoodsDetailVO;
import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.common.base.UserDto;
import com.chitchat.common.enumerate.EnumBusinessType;
import com.chitchat.common.redis.BusinessNoGenerator;
import com.chitchat.common.web.jwt.UserUtils;
import com.chitchat.oms.bean.OrderDetail;
import com.chitchat.oms.bean.OrderInfo;
import com.chitchat.oms.dao.OrderDetailDaoI;
import com.chitchat.oms.dto.OrderInfoAddRequestDTO;
import com.chitchat.oms.enumerate.EnumOrderSourceType;
import com.chitchat.oms.enumerate.EnumOrderStatus;
import com.chitchat.oms.service.OrderDetailServiceI;
import com.chitchat.portal.api.feign.FeignAccountBalanceServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Js on 2022/9/13.
 **/
@Service
public class OrderDetailServiceImpl extends BaseServicesImpl<OrderDetail, OrderDetailDaoI> implements OrderDetailServiceI {

}
