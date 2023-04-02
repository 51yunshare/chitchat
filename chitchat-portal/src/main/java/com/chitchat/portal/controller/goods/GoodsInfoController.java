package com.chitchat.portal.controller.goods;

import com.chitchat.admin.api.dto.GoodsInfoListRequestDTO;
import com.chitchat.admin.api.dto.GoodsInfoPageListRequestDTO;
import com.chitchat.admin.api.vo.GoodsListVO;
import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.Result;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.service.goods.GoodsInfoServiceI;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by Js on 2022/9/21 .
 **/
@RestController
@RequestMapping("goods")
@Api(value = "GoodsInfoController", tags = "商品管理")
public class GoodsInfoController extends BaseController {

    @Resource
    private GoodsInfoServiceI goodsInfoServiceI;

    /**
     * 商品列表
     *
     * @param pageListRequest
     * @return
     */
    @ApiOperation(value = "商品列表-分页", response = GoodsListVO.class)
    @ApiOperationSupport(ignoreParameters = {"userInfo"})
    @PostMapping(value = "/list")
    public ResultTemplate list(@RequestBody GoodsInfoPageListRequestDTO pageListRequest) {
        return goodsInfoServiceI.getList(pageListRequest);
    }

    @ApiOperation(value = "商品列表-无分页",response = GoodsListVO.class)
    @PostMapping(value = "/listNoPage")
    public Result listNoPage(@RequestBody GoodsInfoListRequestDTO listRequestDTO) {
        return goodsInfoServiceI.getListNoPage(listRequestDTO);
    }

    /**
     * 商品分类
     *
     * @return
     */
    @ApiOperation("商品分类")
    @GetMapping(value = "/goodsCategory")
    public ResultTemplate getGoodsCategory() {
        return goodsInfoServiceI.getGoodsCategory();
    }
}
