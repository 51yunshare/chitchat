package com.chitchat.admin.service;

import com.chitchat.admin.api.dto.GoodsInfoListRequestDTO;
import com.chitchat.admin.api.dto.GoodsInfoPageListRequestDTO;
import com.chitchat.admin.api.dto.GoodsSalesUpdateDTO;
import com.chitchat.admin.api.vo.GoodsDetailVO;
import com.chitchat.admin.api.vo.GoodsListVO;
import com.chitchat.admin.bean.GoodsInfo;
import com.chitchat.admin.dto.GoodsInfoAddRequestDTO;
import com.chitchat.common.base.BaseServicesI;
import com.chitchat.common.base.ResultTemplate;

import java.util.List;

public interface GoodsInfoServiceI extends BaseServicesI<GoodsInfo> {

    /**
     * 商品列表
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
    List<GoodsListVO> listNoPage(GoodsInfoListRequestDTO pageListRequest);

    /**
     * 新增商品
     *
     * @param dto
     */
    void doAdd(GoodsInfoAddRequestDTO dto);

    /**
     * 商品详情
     *
     * @param id
     * @return
     */
    GoodsDetailVO getDetail(Long id);

    /**
     * 商品销量修改
     *
     * @param dto
     */
    void updateGoodsSales(GoodsSalesUpdateDTO dto);
}
