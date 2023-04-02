package com.chitchat.portal.controller.base;

import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.dto.base.GiftPageListRequestDTO;
import com.chitchat.portal.service.base.GiftInfoServiceI;
import com.chitchat.portal.vo.base.GiftInfoVO;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 礼物信息
 * <p>
 * Created by Js on 2022/8/26 .
 **/
@Controller
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
    @ApiOperation(value = "礼物列表",response = GiftInfoVO.class)
    @GetMapping(value = "/list")
    @ApiOperationSupport(ignoreParameters = {"userInfo"})
    @ResponseBody
    public ResultTemplate list(GiftPageListRequestDTO pageListRequest) {
        return giftInfoServiceI.getList(pageListRequest);
    }

    /**
     * 礼物类型
     *
     * @return
     */
    @ApiOperation("礼物类型")
    @GetMapping(value = "/giftType")
    @ResponseBody
    public ResultTemplate getGiftType() {
        return giftInfoServiceI.getGiftType();
    }

    /**
     * 获取礼物版本
     *
     * @return
     */
    @ApiOperation(value = "获取礼物版本")
    @GetMapping(value = "/version")
    @ResponseBody
    public ResultTemplate getGiftVersion() {
        return this.success(giftInfoServiceI.getGiftVersion());
    }

    /**
     * 礼物下载
     *
     * @return
     */
    @ApiOperation(value = "礼物下载")
    @GetMapping(value = "/giftDown")
    public void getGiftDown() {
        giftInfoServiceI.downGift();
    }
}
