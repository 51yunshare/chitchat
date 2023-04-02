package com.chitchat.admin.api.feign;

import com.chitchat.admin.api.dto.GoodsInfoListRequestDTO;
import com.chitchat.admin.api.dto.GoodsInfoPageListRequestDTO;
import com.chitchat.admin.api.dto.GoodsSalesUpdateDTO;
import com.chitchat.admin.api.vo.GoodsDetailVO;
import com.chitchat.common.base.Result;
import com.chitchat.common.base.ResultTemplate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 商品
 *
 * Created by Js on 2022/9/21 .
 **/
@FeignClient("chitchat-admin")
public interface FeignGoodsInfoServiceI {

    /**
     * 商品列表-分页
     *
     * @param dto
     * @return
     */
    @PostMapping("/goods/goodsInfo/list")
    ResultTemplate list(@RequestBody GoodsInfoPageListRequestDTO dto);

    /**
     * 商品列表无分页
     *
     * @param dto
     * @return
     */
    @PostMapping("/goods/goodsInfo/listNoPage")
    Result listNoPage(@RequestBody GoodsInfoListRequestDTO dto);

    /**
     * 获取商品详细新
     *
     * @param id
     * @return
     */
    @GetMapping("/goods/goodsInfo/detail/{id}")
    GoodsDetailVO getDetail(@PathVariable("id") Long id);

    /**
     * 修改商品销量
     *
     * @param dto
     */
    @PostMapping("/goods/goodsInfo/updateGoodsSalesById")
    ResultTemplate updateGoodsSalesById(@RequestBody GoodsSalesUpdateDTO dto);
}
