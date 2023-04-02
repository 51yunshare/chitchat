package com.chitchat.admin.controller.base;

import com.chitchat.admin.dto.GiftAddRequestDTO;
import com.chitchat.admin.dto.GiftPageListRequestDTO;
import com.chitchat.admin.service.GiftInfoServiceI;
import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.ResultTemplate;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 礼物信息
 * <p>
 * Created by Js on 2022/8/26 .
 **/
@RestController
@RequestMapping("base/gift")
@Api(value = "GiftInfoController", tags = "礼物信息管理")
@Slf4j
public class GiftInfoController extends BaseController {

    @Autowired
    private GiftInfoServiceI giftInfoServiceI;

    /**
     * 礼物列表
     *
     * @param pageListRequest
     * @return
     */
    @ApiOperation("礼物列表")
    @GetMapping(value = "/list")
    @ApiOperationSupport(ignoreParameters = {"userInfo"})
    public ResultTemplate list(GiftPageListRequestDTO pageListRequest) {
        return giftInfoServiceI.getList(pageListRequest);
    }
    /**
     * 新增礼物
     *
     * @param dto
     * @return
     */
    @ApiOperation("新增礼物")
    @PostMapping("/add")
    public ResultTemplate doAdd(@Validated GiftAddRequestDTO dto) {
        giftInfoServiceI.doAdd(dto);
        return this.success();
    }
}
