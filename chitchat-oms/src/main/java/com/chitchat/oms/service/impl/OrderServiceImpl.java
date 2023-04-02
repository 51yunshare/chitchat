package com.chitchat.oms.service.impl;

import cn.hutool.core.date.DateUtil;
import com.chitchat.admin.api.dto.GoodsSalesUpdateDTO;
import com.chitchat.admin.api.feign.FeignGoodsInfoServiceI;
import com.chitchat.admin.api.vo.GoodsDetailVO;
import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.common.base.CodeMsg;
import com.chitchat.common.base.UserDto;
import com.chitchat.common.enumerate.EnumBusinessType;
import com.chitchat.common.enumerate.EnumYesOrNo;
import com.chitchat.common.exception.ChitchatException;
import com.chitchat.common.redis.BusinessNoGenerator;
import com.chitchat.common.util.BeanUtils;
import com.chitchat.common.web.jwt.UserUtils;
import com.chitchat.oms.bean.OrderDetail;
import com.chitchat.oms.bean.OrderInfo;
import com.chitchat.oms.bean.account.AccountBackpack;
import com.chitchat.oms.dao.OrderDaoI;
import com.chitchat.oms.dto.OrderInfoAddRequestDTO;
import com.chitchat.oms.enumerate.EnumOrderSourceType;
import com.chitchat.oms.enumerate.EnumOrderStatus;
import com.chitchat.oms.enumerate.EnumPayType;
import com.chitchat.oms.listener.RabbitDelayService;
import com.chitchat.oms.service.AccountBalanceServiceI;
import com.chitchat.oms.service.OrderDetailServiceI;
import com.chitchat.oms.service.OrderServiceI;
import com.chitchat.portal.api.enumerate.EnumBackpackGoodsSource;
import com.chitchat.portal.api.enumerate.EnumBalanceFlowType;
import com.chitchat.portal.api.feign.FeignAccountBalanceServiceI;
import com.chitchat.portal.api.feign.FeignPortalAccountServiceI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Js on 2022/9/13.
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl extends BaseServicesImpl<OrderInfo, OrderDaoI> implements OrderServiceI {

    private final FeignGoodsInfoServiceI feignGoodsInfoServiceI;
    private final FeignAccountBalanceServiceI feignAccountBalanceServiceI;
//    private final FeignAccountBackpackServiceI feignAccountBackpackServiceI;
    private final FeignPortalAccountServiceI feignPortalAccountServiceI;
    private final BusinessNoGenerator businessNoGenerator;
    private final OrderDetailServiceI orderDetailServiceI;
    private final RabbitDelayService rabbitDelayService;

    private final AccountBalanceServiceI accountBalanceServiceI;

    /**
     * 生成订单
     *
     * 1.查询用户余额，判断是否充足
     * 2.校验商品信息
     * 3.生成订单、订单明细
     * 4.商品销量
     * 5.用户余额流水记录
     * 6.用户背包添加
     * 7.延迟队列（商品的使用时长）
     *
     * @param requestDTO
     */
    @Override
    @Transactional
    public void doAdd(OrderInfoAddRequestDTO requestDTO) {
        //获取用户信息
        UserDto userDto = UserUtils.getUser();
        //1.判断商品
        GoodsDetailVO goodsDetailVO = feignGoodsInfoServiceI.getDetail(requestDTO.getGoodsId());
        //2.判断账户余额
        feignAccountBalanceServiceI.checkAccountBalance(userDto.getId(), goodsDetailVO.getGoodsPrice());
        //3.if赠送判断目标用户
        if (requestDTO.getGoodsSource().getIndex() == EnumBackpackGoodsSource.赠送.getIndex()){
            feignPortalAccountServiceI.getById(requestDTO.getTargetId());
        }
        //4.生成订单、订单明细
        OrderInfo orderInfo = new OrderInfo()
                .setAccountId(userDto.getId())
                .setOrderSn(businessNoGenerator.generate(EnumBusinessType.ORDER))
                .setOrderStatus(EnumOrderStatus.PENDING_PAYMENT.getIndex())
                .setSourceType(EnumOrderSourceType.APP.getIndex())
                .setTotalNum(1)
                .setTotalAmount(goodsDetailVO.getGoodsPrice());
        baseDaoT.insert(orderInfo);
        //订单明细
        orderDetailServiceI.insert(OrderDetail.builder()
                .orderId(orderInfo.getId())
                .orderSn(orderInfo.getOrderSn())
                .goodsId(goodsDetailVO.getId())
                .goodsName(goodsDetailVO.getGoodsName())
                .goodsCategoryId(goodsDetailVO.getGoodsCategoryId())
                .goodsCategoryName(goodsDetailVO.getGoodsCategoryName())
                .goodsCover(goodsDetailVO.getGoodsCover())
                .goodsNum(1)
                .goodsPrice(goodsDetailVO.getGoodsPrice())
                .totalAmount(goodsDetailVO.getGoodsPrice())
                .build());
        //5.扣减余额并生成流水
        boolean isSuccess = accountBalanceServiceI.accountBalanceSub(userDto, orderInfo.getId(), orderInfo.getOrderSn(), goodsDetailVO.getGoodsPrice(), EnumBalanceFlowType.购买);
        if (!isSuccess){
            throw new ChitchatException(CodeMsg.SERVER_ERROR, "当前金币余额不足，扣减失败！");
        }
        //6.回写订单信息支付信息
        baseDaoT.updateByPrimaryKeySelective(new OrderInfo()
                .setId(orderInfo.getId())
                .setPayType(EnumPayType.BALANCE.getIndex())
                .setPayAmount(goodsDetailVO.getGoodsPrice())
                .setPaymentTime(DateUtil.date()));
        //7.用户背包添加商品记录
        AccountBackpack backpack = BeanUtils.copyProperties(goodsDetailVO, AccountBackpack.class);
        backpack.setGoodsId(goodsDetailVO.getId());
        backpack.setAccountId(userDto.getId());
        backpack.setActivateStatus(EnumYesOrNo.否.getIndex());
        backpack.setOverdueStatus(EnumYesOrNo.否.getIndex());
        backpack.setUsedStatus(EnumYesOrNo.否.getIndex());
        //订单信息
        backpack.setOrderId(orderInfo.getId());
        backpack.setOrderSn(orderInfo.getOrderSn());
        //过期时间
        backpack.setOverdueTime(DateUtil.offsetDay(DateUtil.date(), backpack.getDuration()));
        //判断是否购买/赠送
        backpack.setGoodsSource(EnumBackpackGoodsSource.购买.getIndex());
        if (requestDTO.getGoodsSource().getIndex() == EnumBackpackGoodsSource.赠送.getIndex()){
            backpack.setGoodsSource(EnumBackpackGoodsSource.赠送.getIndex());
            backpack.setAccountId(requestDTO.getTargetId());
            backpack.setGiveAccountId(userDto.getId());
        }
        accountBalanceServiceI.doAddAccountBackpack(backpack);
        //8.商品销量回写
        feignGoodsInfoServiceI.updateGoodsSalesById(new GoodsSalesUpdateDTO().setGoodsId(goodsDetailVO.getId()).setNum(1));

        //消息队列 backpack.getDuration() * 24 * 60 * 60 * 1000
        rabbitDelayService.sendMessage(backpack.getId().toString(), 60 * 1000);
    }
}
