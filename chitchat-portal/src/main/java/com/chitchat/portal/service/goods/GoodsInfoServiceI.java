package com.chitchat.portal.service.goods;

import com.chitchat.admin.api.dto.GoodsInfoListRequestDTO;
import com.chitchat.admin.api.dto.GoodsInfoPageListRequestDTO;
import com.chitchat.common.base.Result;
import com.chitchat.common.base.ResultTemplate;

/**
 * Created by Js on 2022/9/21 .
 **/
public interface GoodsInfoServiceI {
    /**
     * 商品列表-分页
     *
     * @param pageListRequest
     * @return
     */
    ResultTemplate getList(GoodsInfoPageListRequestDTO pageListRequest);

    /**
     * 商品列表-无分页
     *
     * @param pageListRequest
     * @return
     */
    Result getListNoPage(GoodsInfoListRequestDTO pageListRequest);

    /**
     * 商品分类
     *
     * @return
     */
    ResultTemplate getGoodsCategory();
}
