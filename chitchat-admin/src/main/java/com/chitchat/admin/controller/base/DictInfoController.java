package com.chitchat.admin.controller.base;

import com.chitchat.admin.api.enumerate.EnumDictType;
import com.chitchat.admin.api.vo.DictInfoVO;
import com.chitchat.admin.bean.DictInfo;
import com.chitchat.admin.service.DictInfoServiceI;
import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.util.BeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Js on 2022/8/20.
 **/
@Api(tags = "字典管理接口")
@RestController
@RequestMapping("/base/dict")
public class DictInfoController extends BaseController {
    @Autowired
    private DictInfoServiceI dictInfoServiceI;

    /**
     * 根据Type值获取字典列表数据
     * 父子封装
     *
     * @param type
     */
    @ApiOperation("根据Type值获取字典列表数据")
    @GetMapping(value = "/getDictByType/{type}")
    public ResultTemplate getDictByType(@PathVariable(value = "type") Integer type) {
        return success(dictInfoServiceI.getDictByType(type));
    }

    /**
     * 获取字典类型
     *
     */
    @ApiOperation("获取字典类型")
    @GetMapping(value = "/getDictType")
    public ResultTemplate getDictInfoByType() {
        return success(EnumDictType.getMapInfo());
    }

    /**
     * 根据Id值获取字典数据
     *
     * @param dictId
     * @return
     */
    @ApiOperation("根据Id值获取字典数据")
    @GetMapping(value = "/getDictById")
    public DictInfoVO getDictById(@RequestParam Long dictId) {
        DictInfo dictInfo = dictInfoServiceI.getById(dictId, "字典信息");
        return BeanUtils.copyProperties(dictInfo, DictInfoVO.class);
    }
}
