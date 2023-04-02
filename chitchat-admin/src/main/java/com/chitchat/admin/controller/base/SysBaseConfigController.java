package com.chitchat.admin.controller.base;

import com.chitchat.admin.bean.SysBaseConfig;
import com.chitchat.admin.dto.SysBaseConfigPageListRequestDTO;
import com.chitchat.admin.service.SysBaseConfigServiceI;
import com.chitchat.admin.vo.base.SysBaseConfigListVO;
import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.util.BeanUtils;
import com.chitchat.common.util.JSONObjectUtil;
import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统配置信息
 *
 * Created by Js on 2022/12/2 .
 **/
@RestController
@RequestMapping("base/config")
@Api(value = "SysBaseConfigController", tags = "系统配置管理")
@Slf4j
@RequiredArgsConstructor
public class SysBaseConfigController extends BaseController {

    private final SysBaseConfigServiceI sysBaseConfigServiceI;

    /**
     * 系统配置列表
     *
     * @param pageListRequest
     * @return
     */
    @ApiOperation("配置列表")
    @ApiOperationSupport(ignoreParameters = {"userInfo"})
    @GetMapping(value = "/list")
    public ResultTemplate list(SysBaseConfigPageListRequestDTO pageListRequest) {
        PageInfo data = sysBaseConfigServiceI.list(pageListRequest);
        return success(data, BeanUtils.convertList(data.getList(), SysBaseConfigListVO.class));
    }
    /**
     * 获取Key信息
     *
     * @return
     */
    @ApiOperation(value = "Key获取配置信息")
    @GetMapping(value = "/getByKey")
    public ResultTemplate getByConfigKey(@RequestParam String configKey) {
        SysBaseConfig config = sysBaseConfigServiceI.getByConfigKey(configKey);
        return success(JSONObjectUtil.parseObject(config));
    }
}
