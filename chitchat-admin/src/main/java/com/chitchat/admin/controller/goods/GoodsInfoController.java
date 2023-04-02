package com.chitchat.admin.controller.goods;

import com.chitchat.admin.api.dto.GoodsInfoListRequestDTO;
import com.chitchat.admin.api.dto.GoodsInfoPageListRequestDTO;
import com.chitchat.admin.api.dto.GoodsSalesUpdateDTO;
import com.chitchat.admin.api.vo.GoodsDetailVO;
import com.chitchat.admin.api.vo.GoodsListVO;
import com.chitchat.admin.dto.GoodsInfoAddRequestDTO;
import com.chitchat.admin.service.GoodsInfoServiceI;
import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.Result;
import com.chitchat.common.base.ResultTemplate;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品管理
 *
 * Created by Js on 2022/9/20.
 **/
@RestController
@RequestMapping("goods/goodsInfo")
@Api(value = "GoodsInfoController", tags = "商品管理")
public class GoodsInfoController extends BaseController {

    @Resource
    private GoodsInfoServiceI goodsInfoServiceI;

    /**
     * 商品列表-分页
     *
     * @param pageListRequest
     * @return
     */
    @ApiOperation("商品列表-分页")
    @PostMapping(value = "/list")
    @ApiOperationSupport(ignoreParameters = {"userInfo"})
    public ResultTemplate list(@RequestBody GoodsInfoPageListRequestDTO pageListRequest) {
        return goodsInfoServiceI.getList(pageListRequest);
    }

    /**
     * 商品列表-无分页
     *
     * @param pageListRequest
     * @return
     */
    @ApiOperation("商品列表-无分页")
    @PostMapping(value = "/listNoPage")
    @ApiOperationSupport(ignoreParameters = {"userInfo"})
    public Result<List<GoodsListVO>> listNoPage(@RequestBody GoodsInfoListRequestDTO pageListRequest) {
        return Result.success(goodsInfoServiceI.listNoPage(pageListRequest));
    }
    /**
     * 新增商品
     *
     * @param dto
     * @return
     */
    @ApiOperation("新增商品")
    @PostMapping("/add")
    public ResultTemplate doAdd(@Validated GoodsInfoAddRequestDTO dto) {
        goodsInfoServiceI.doAdd(dto);
        return this.success();
    }

    /**
     * 商品详情
     *
     * @return
     */
    @ApiOperation(value = "商品详情", response = GoodsDetailVO.class)
    @GetMapping(value = "/detail/{id}")
    public GoodsDetailVO getDetail(@PathVariable Long id) {
        return goodsInfoServiceI.getDetail(id);
    }

    /**
     * 商品销量修改
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "商品销量修改", hidden = true)
    @PostMapping("/updateGoodsSalesById")
    public ResultTemplate updateGoodsSalesById(@RequestBody GoodsSalesUpdateDTO dto) {
        goodsInfoServiceI.updateGoodsSales(dto);
        return this.success();
    }
}
