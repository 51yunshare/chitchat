package com.chitchat.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.chitchat.admin.bean.DictInfo;
import com.chitchat.admin.bean.GiftInfo;
import com.chitchat.admin.dao.GiftInfoDaoI;
import com.chitchat.admin.dto.GiftAddRequestDTO;
import com.chitchat.admin.dto.GiftPageListRequestDTO;
import com.chitchat.admin.service.DictInfoServiceI;
import com.chitchat.admin.service.GiftInfoServiceI;
import com.chitchat.admin.vo.base.GiftInfoVo;
import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.oss.OssKey;
import com.chitchat.common.oss.service.OssServiceI;
import com.chitchat.common.redis.RedisKey;
import com.chitchat.common.redis.RedisTemplateUtil;
import com.chitchat.common.util.BeanUtils;
import com.chitchat.common.util.JSONObjectUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

/**
 * 礼物service实现类
 */
@Service
public class GiftInfoServiceImpl extends BaseServicesImpl<GiftInfo, GiftInfoDaoI> implements GiftInfoServiceI {

    @Autowired
    private DictInfoServiceI dictInfoServiceI;
    @Autowired
    private OssServiceI ossServiceI;
    @Autowired
    private RedisTemplateUtil redisTemplateUtil;
//    @Autowired
//    private ThreadPoolExecutor threadPoolExecutor;


    /**
     * 礼物列表
     *
     * @param pageListRequest
     * @return
     */
    @Override
    public ResultTemplate getList(GiftPageListRequestDTO pageListRequest) {
        PageInfo<GiftInfo> data = list(pageListRequest);
        return new ResultTemplate(data, JSONObjectUtil.parseArray(BeanUtils.convertList(data.getList(), GiftInfoVo.class)));
    }

    /**
     * 礼物新增
     *
     * @param dto
     */
    @Override
    @Transactional
    public void doAdd(GiftAddRequestDTO dto) {
        GiftInfo giftInfo = BeanUtils.copyProperties(dto, GiftInfo.class);
        //参数判断
        DictInfo dictInfo = dictInfoServiceI.getById(dto.getGiftType());
        giftInfo.setGiftTypeName(dictInfo.getDictValue());
        //礼物新增
        baseDaoT.insert(giftInfo);
        //上传icon、效果图
        if (dto.getIcon() != null && StrUtil.isNotBlank(dto.getIcon().getOriginalFilename())){
            String uploadIcon = ossServiceI.upload(dto.getIcon(), OssKey.getGiftPath(dto.getIcon().getOriginalFilename(), OssKey.OssPath.GIFT, giftInfo.getId()));
            giftInfo.setGiftIcon(uploadIcon);
        }
        //效果图
        if (dto.getGiftEffect() != null && StrUtil.isNotBlank(dto.getGiftEffect().getOriginalFilename())){
            String uploadEffect = ossServiceI.upload(dto.getGiftEffect(), OssKey.getGiftPath(dto.getGiftEffect().getOriginalFilename(), OssKey.OssPath.GIFT_EFFECT, giftInfo.getId()));
            giftInfo.setGiftEffectUrl(uploadEffect);
            //更新版本号
            redisTemplateUtil.incr(RedisKey.getGiftVersion());
            CompletableFuture.runAsync(() -> {
                //打包所有的效果图（1.获取所有的效果图 2.打包zip上传）
                ossServiceI.packZipFile(OssKey.OssPath.GIFT_EFFECT.getPath(), OssKey.OssPath.GIFT_EFFECT_ZIP.getPath());
            });
        }
        baseDaoT.updateByPrimaryKeySelective(giftInfo);
    }

}
