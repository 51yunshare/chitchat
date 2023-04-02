package com.chitchat.admin.service.impl;

import com.chitchat.admin.api.dto.GoodsInfoListRequestDTO;
import com.chitchat.admin.api.dto.GoodsInfoPageListRequestDTO;
import com.chitchat.admin.api.dto.GoodsSalesUpdateDTO;
import com.chitchat.admin.api.enumerate.EnumDictType;
import com.chitchat.admin.api.vo.GoodsDetailVO;
import com.chitchat.admin.api.vo.GoodsListVO;
import com.chitchat.admin.bean.DictInfo;
import com.chitchat.admin.bean.GoodsInfo;
import com.chitchat.admin.dao.GoodsInfoDaoI;
import com.chitchat.admin.dto.GoodsInfoAddRequestDTO;
import com.chitchat.admin.service.DictInfoServiceI;
import com.chitchat.admin.service.GoodsInfoServiceI;
import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.common.base.CodeMsg;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.exception.ChitchatException;
import com.chitchat.common.util.BeanUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Js on 2022/9/20.
 **/
@Service
public class GoodsInfoServiceImpl extends BaseServicesImpl<GoodsInfo, GoodsInfoDaoI> implements GoodsInfoServiceI {

    @Resource
    private DictInfoServiceI dictInfoServiceI;

    /**
     * 商品列表
     *
     * @param pageListRequest
     * @return
     */
    @Override
    public ResultTemplate getList(GoodsInfoPageListRequestDTO pageListRequest) {
        PageHelper.startPage(pageListRequest.getCp(), pageListRequest.getRows());
        PageInfo<GoodsInfo> data = new PageInfo(baseDaoT.list(pageListRequest));
        return new ResultTemplate(data, BeanUtils.convertList(data.getList(), GoodsListVO.class));
    }

    /**
     * 商品列表-无分页
     *
     * @param dto
     * @return
     */
    @Override
    public List<GoodsListVO> listNoPage(GoodsInfoListRequestDTO dto) {
        return BeanUtils.convertList(baseDaoT.list(BeanUtils.copyProperties(dto, GoodsInfoPageListRequestDTO.class)), GoodsListVO.class);
    }

    /**
     * 新增商品
     *
     * @param dto
     */
    @Override
    @Transactional
    public void doAdd(GoodsInfoAddRequestDTO dto) {
        //商品分类
        DictInfo categoryDict = dictInfoServiceI.getById(dto.getGoodsCategoryId(), "商品分类信息");
        if (categoryDict.getDictType().intValue() != EnumDictType.GOODS_CATEGORY.getIndex()){
            throw new ChitchatException(CodeMsg.BIND_ERROR, "商品分类信息有误！");
        }
        //上传
        GoodsInfo goodsInfo = BeanUtils.copyProperties(dto, GoodsInfo.class);
        goodsInfo.setGoodsCategoryName(categoryDict.getDictValue());
        goodsInfo.setSales(0);
        baseDaoT.insert(goodsInfo);
    }

    /**
     * 商品详情
     *
     * @param id
     * @return
     */
    @Override
    public GoodsDetailVO getDetail(Long id) {
        GoodsInfo goodsInfo = baseDaoT.getById(id);
        if (goodsInfo == null){
            throw new ChitchatException(CodeMsg.NULL_ERROR, "商品信息不存在");
        }
        return BeanUtils.copyProperties(goodsInfo, GoodsDetailVO.class);
    }

    /**
     * 商品销量修改
     *
     * @param dto
     */
    @Override
    public void updateGoodsSales(GoodsSalesUpdateDTO dto) {
        GoodsInfo goods = this.getById(dto.getGoodsId(), "商品信息");
        baseDaoT.updateByPrimaryKeySelective(GoodsInfo.builder()
                .id(dto.getGoodsId())
                .sales(goods.getSales() == null ? dto.getNum() : goods.getSales() + dto.getNum())
                .build());
    }
}
