package com.chitchat.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chitchat.admin.bean.RoomType;
import com.chitchat.admin.dao.RoomTypeDaoI;
import com.chitchat.admin.dto.RoomTypeAddRequestDTO;
import com.chitchat.admin.dto.RoomTypePageListRequestDTO;
import com.chitchat.admin.service.RoomTypeServiceI;
import com.chitchat.admin.vo.base.RoomTypePageVO;
import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.common.base.CodeMsg;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.exception.ChitchatException;
import com.chitchat.common.util.BeanUtils;
import com.chitchat.common.util.JSONObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 房间类型
 *
 * Created by Js on 2022/10/13 .
 **/
@Service
public class RoomTypeServiceImpl extends BaseServicesImpl<RoomType, RoomTypeDaoI> implements RoomTypeServiceI {

    /**
     * 列表
     *
     * @param pageListRequest
     * @return
     */
    @Override
    public ResultTemplate getList(RoomTypePageListRequestDTO pageListRequest) {
        PageHelper.startPage(pageListRequest.getCp(), pageListRequest.getRows());
        PageInfo<RoomType> data = new PageInfo(baseDaoT.list(pageListRequest));
        return new ResultTemplate(data, JSONObjectUtil.parseArray(BeanUtils.convertList(data.getList(), RoomTypePageVO.class)));
    }

    /**
     * 新增房间类型
     *
     * @param dto
     */
    @Override
    public void doAdd(RoomTypeAddRequestDTO dto) {
        //判重
        RoomType roomType = baseDaoT.getByName(dto.getTypeName());
        if (roomType != null){
            throw new ChitchatException(CodeMsg.BIND_ERROR, "The room type already exists ！");
        }
        //新增
        baseDaoT.insert(BeanUtils.copyProperties(dto, RoomType.class));
    }

    /**
     * 获取房间类型下拉框
     *
     * @return
     */
    @Override
    public JSONArray getRoomTypeComList() {
        List<RoomType> types = baseDaoT.list(null);
        JSONArray result = new JSONArray();
        types.forEach(roomType -> {
            result.add(new JSONObject().fluentPut("id", roomType.getId())
                    .fluentPut("name", roomType.getTypeName()));
        });
        return result;
    }
}
