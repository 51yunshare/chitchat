package com.chitchat.portal.controller.base;

import com.chitchat.common.base.BaseController;
import com.chitchat.portal.service.base.FlagsServiceI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 国旗信息
 * <p>
 * Created by Js on 2022/8/26 .
 **/
@Controller
@RequestMapping("base/flags")
@Api(value = "FlagsController", tags = "国旗信息")
@Slf4j
@RequiredArgsConstructor
public class FlagsController extends BaseController {

    private final FlagsServiceI flagsServiceI;
    /**
     * 国旗下载
     *
     * @return
     */
    @ApiOperation(value = "国旗下载")
    @GetMapping(value = "/flagsDown")
    public void getFlagsDown() {
        flagsServiceI.flagsDown();
    }
}
