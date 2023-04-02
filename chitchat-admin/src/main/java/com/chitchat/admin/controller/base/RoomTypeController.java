package com.chitchat.admin.controller.base;

import com.alibaba.fastjson.JSONArray;
import com.chitchat.admin.api.vo.RoomTypeInfoVO;
import com.chitchat.admin.bean.RoomType;
import com.chitchat.admin.dto.RoomTypeAddRequestDTO;
import com.chitchat.admin.dto.RoomTypePageListRequestDTO;
import com.chitchat.admin.service.RoomTypeServiceI;
import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.util.BeanUtils;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 房间类型管理
 *
 * Created by Js on 2022/9/15 .
 **/
@RestController
@RequestMapping("base/roomType")
@Api(value = "RoomTypeController", tags = "房间类型管理")
@Slf4j
@RequiredArgsConstructor
public class RoomTypeController extends BaseController {

    private final RoomTypeServiceI roomTypeServiceI;

    /**
     * 房间类型列表
     *
     * @param pageListRequest
     * @return
     */
    @ApiOperation("房间类型列表")
    @GetMapping(value = "/list")
    @ApiOperationSupport(ignoreParameters = {"userInfo"})
    public ResultTemplate list(RoomTypePageListRequestDTO pageListRequest) {
        return roomTypeServiceI.getList(pageListRequest);
    }
    /**
     * 新增房间类型
     *
     * @param dto
     * @return
     */
    @ApiOperation("房间类型新增")
    @PostMapping("/add")
    public ResultTemplate doAdd(@RequestBody @Validated RoomTypeAddRequestDTO dto) {
        roomTypeServiceI.doAdd(dto);
        return this.success();
    }

    /**
     * 获取房间类型通过id
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取房间类型通过id", hidden = true)
    @GetMapping(value = "/getById")
    public RoomTypeInfoVO getRoomTypeById(@RequestParam(value = "id") Integer id) {
        RoomType roomType = roomTypeServiceI.selectByPrimaryKey(id);
        return roomType == null ? null : BeanUtils.copyProperties(roomType, RoomTypeInfoVO.class);
    }

    @ApiOperation(value = "获取房间类型下拉框", hidden = true)
    @GetMapping(value = "/getComList")
    public JSONArray getRoomTypeComList() {
        return roomTypeServiceI.getRoomTypeComList();
    }
}
