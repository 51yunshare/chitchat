package com.chitchat.portal.service.goods.impl;

import com.chitchat.admin.api.dto.GoodsInfoListRequestDTO;
import com.chitchat.admin.api.dto.GoodsInfoPageListRequestDTO;
import com.chitchat.admin.api.enumerate.EnumDictType;
import com.chitchat.admin.api.feign.FeignDictInfoServiceI;
import com.chitchat.admin.api.feign.FeignGoodsInfoServiceI;
import com.chitchat.common.base.Result;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.service.goods.GoodsInfoServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Js on 2022/9/21 .
 **/
@Service
public class GoodsInfoServiceImpl implements GoodsInfoServiceI {

    @Resource
    private FeignGoodsInfoServiceI feignGoodsInfoServiceI;
    @Resource
    private FeignDictInfoServiceI feignDictInfoServiceI;

    /**
     * 商品列表-分页
     *
     * @param pageListRequest
     * @return
     */
    @Override
    public ResultTemplate getList(GoodsInfoPageListRequestDTO pageListRequest) {
        return feignGoodsInfoServiceI.list(pageListRequest);
    }

    /**
     * 商品列表-无分页
     *
     * @param pageListRequest
     * @return
     */
    @Override
    public Result getListNoPage(GoodsInfoListRequestDTO pageListRequest) {
        return feignGoodsInfoServiceI.listNoPage(pageListRequest);
    }

    /**
     * 商品分类
     *
     * @return
     */
    @Override
    public ResultTemplate getGoodsCategory() {
        return feignDictInfoServiceI.getDictByType(EnumDictType.GOODS_CATEGORY.getIndex());
    }
}
