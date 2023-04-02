package com.chitchat.portal.service.base.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.chitchat.admin.api.enumerate.EnumDictType;
import com.chitchat.admin.api.feign.FeignDictInfoServiceI;
import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.oss.OssKey;
import com.chitchat.common.oss.service.OssServiceI;
import com.chitchat.common.redis.RedisKey;
import com.chitchat.common.redis.RedisTemplateUtil;
import com.chitchat.common.util.BeanUtils;
import com.chitchat.common.util.JSONObjectUtil;
import com.chitchat.common.util.ServletUtils;
import com.chitchat.portal.bean.base.GiftInfo;
import com.chitchat.portal.dao.base.GiftInfoDaoI;
import com.chitchat.portal.dto.base.GiftPageListRequestDTO;
import com.chitchat.portal.service.base.GiftInfoServiceI;
import com.chitchat.portal.vo.base.GiftInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 礼物service实现类
 */
@Service
@Slf4j
public class GiftInfoServiceImpl extends BaseServicesImpl<GiftInfo, GiftInfoDaoI> implements GiftInfoServiceI {

    @Autowired
    private FeignDictInfoServiceI feignDictInfoServiceI;
    @Autowired
    private RedisTemplateUtil redisTemplateUtil;
    @Autowired
    private OssServiceI ossServiceI;
    private static final String GIFT_DOWN = "/chitchat-portal/base/gift/giftDown";

    /**
     * 礼物列表
     *
     * @param pageListRequest
     * @return
     */
    @Override
    public ResultTemplate getList(GiftPageListRequestDTO pageListRequest) {
        List<GiftInfo> data = baseDaoT.list(pageListRequest);
        List<GiftInfoVO> voList = BeanUtils.convertList(data, GiftInfoVO.class);
        if (ObjectUtil.length(voList) >0){
            Map<String, List<GiftInfoVO>> map = voList.stream().collect(Collectors.groupingBy(GiftInfoVO::getGiftTypeName));
            return new ResultTemplate(JSONObjectUtil.parseObject(map));
        }
        return new ResultTemplate();
    }

    /**
     * 礼物类型
     *
     * @return
     */
    @Override
    public ResultTemplate getGiftType() {
        return feignDictInfoServiceI.getDictByType(EnumDictType.GIFT_TYPE.getIndex());
    }

    /**
     * 获取礼物版本
     *
     * @return
     */
    @Override
    public JSONObject getGiftVersion() {
        return new JSONObject()
                .fluentPut("giftVersion", redisTemplateUtil.get(RedisKey.getGiftVersion()) == null ? 0 : redisTemplateUtil.get(RedisKey.getGiftVersion()))
                .fluentPut("giftUrl", GIFT_DOWN);
    }

    /**
     * 礼物下载
     *
     */
    @Override
    public void downGift() {
        HttpServletResponse response = ServletUtils.getResponse();
        final String giftPath = OssKey.OssPath.GIFT_EFFECT_ZIP.getPath() + "gift.zip";
        log.debug("礼物文件下载，IP：{}；access file：{}", ServletUtils.getRealIp(ServletUtils.getRequest()), giftPath);
        ossServiceI.downloadResponse(response, giftPath, null, null);
    }
}
