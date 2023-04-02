package com.chitchat.oms.controller.order;

import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.oms.dto.OrderInfoAddRequestDTO;
import com.chitchat.oms.service.OrderServiceI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单管理
 *
 * Created by Js on 2022/9/13.
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("oms/orders")
@Api(value = "OrderController", tags = "用户订单")
public class OrderController extends BaseController {
    private final OrderServiceI orderServiceI;

    /**
     * 购买商品
     *
     * @param requestDTO
     * @return
     */
    @ApiOperation("订单生成-购买商品")
    @PostMapping(value = "/add")
    public ResultTemplate add(@RequestBody OrderInfoAddRequestDTO requestDTO){
        orderServiceI.doAdd(requestDTO);
        return success();
    }

}
