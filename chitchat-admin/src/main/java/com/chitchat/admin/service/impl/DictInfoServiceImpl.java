package com.chitchat.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chitchat.admin.bean.DictInfo;
import com.chitchat.admin.dao.DictInfoDaoI;
import com.chitchat.admin.api.enumerate.EnumDictType;
import com.chitchat.admin.service.DictInfoServiceI;
import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.common.base.CodeMsg;
import com.chitchat.common.exception.ChitchatException;
import com.chitchat.common.util.EnumUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * OAuth2客户端业务实现类
 *
 * Created by Js on 2022/8/10.
 */
@Service
public class DictInfoServiceImpl extends BaseServicesImpl<DictInfo, DictInfoDaoI> implements DictInfoServiceI {

    /**
     * 根据类型获取字典信息
     *
     * @param type
     * @return
     */
    @Override
    public JSONArray getDictByType(Integer type) {
        EnumDictType enumDictType = EnumUtil.valueOf(EnumDictType.class, type);
        if (enumDictType == null) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, "type参数值有误！");
        }
        List<DictInfo> dictInfoList = baseDaoT.getDictByTypeOrPid(type, null);
        JSONArray result = new JSONArray();
        for (DictInfo dictInfo : dictInfoList) {
            if (dictInfo.getPid() == null) {
                JSONObject pObject = getJson(dictInfo);
                JSONArray sonObj = new JSONArray();
                for (DictInfo dict : dictInfoList) {
                    if (dict.getPid() != null && dict.getPid().intValue() == dictInfo.getId().intValue()) {
                        sonObj.add(getJson(dict));
                    }
                }
                pObject.put("child", sonObj);
                result.add(pObject);
            }
        }
        return result;
    }

    /**
     * 封装JSON
     *
     * @param dictInfo
     * @return
     */
    private JSONObject getJson(DictInfo dictInfo) {
        JSONObject object = new JSONObject();
        object.put("id", dictInfo.getId());
        object.put("name", dictInfo.getDictValue());
        object.put("type", dictInfo.getDictType());
        object.put("memo", dictInfo.getMemo());
        return object;
    }
}
