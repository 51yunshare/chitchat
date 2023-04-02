package com.chitchat.admin.service.impl;

import com.chitchat.admin.api.vo.ActivityInfoFeignVO;
import com.chitchat.admin.bean.ActivityInfo;
import com.chitchat.admin.dao.ActivityInfoDaoI;
import com.chitchat.admin.dto.ActivityInfoAddRequestDTO;
import com.chitchat.admin.dto.ActivityInfoPageListRequestDTO;
import com.chitchat.admin.service.ActivityInfoServiceI;
import com.chitchat.admin.vo.base.ActivityInfoPageVO;
import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.oss.OssKey;
import com.chitchat.common.oss.service.OssServiceI;
import com.chitchat.common.util.BeanUtils;
import com.chitchat.common.util.JSONObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 活动service实现类
 */
@Service
public class ActivityInfoServiceImpl extends BaseServicesImpl<ActivityInfo, ActivityInfoDaoI> implements ActivityInfoServiceI {

    @Autowired
    private OssServiceI ossServiceI;

    /**
     * 活动列表
     *
     * @param pageListRequest
     * @return
     */
    @Override
    public ResultTemplate getList(ActivityInfoPageListRequestDTO pageListRequest) {

        PageHelper.startPage(pageListRequest.getCp(), pageListRequest.getRows());
        PageInfo<ActivityInfo> data = new PageInfo(baseDaoT.list(pageListRequest));
        return new ResultTemplate(data, JSONObjectUtil.parseArray(BeanUtils.convertList(data.getList(), ActivityInfoPageVO.class)));
    }

    /**
     * 活动新增
     *
     * @param dto
     */
    @Override
    @Transactional
    public void doAdd(ActivityInfoAddRequestDTO dto) {
        ActivityInfo activityInfo = BeanUtils.copyProperties(dto, ActivityInfo.class);
        //活动图
        if (dto.getPic() != null){
            String uploadIcon = ossServiceI.upload(dto.getPic(), OssKey.getImgPath(dto.getPic().getOriginalFilename()));
            activityInfo.setPicUrl(uploadIcon);
        }
        activityInfo.setSort(activityInfo.getSort() == null ? 1 : activityInfo.getSort());
        baseDaoT.insert(activityInfo);
    }

    /**
     * 获取活动列表通过类型
     *
     * @param type
     * @return
     */
    @Override
    public List<ActivityInfoFeignVO> getListByType(Integer type) {
        List<ActivityInfo> list = baseDaoT.list(new ActivityInfoPageListRequestDTO().setType(type));
        return BeanUtils.convertList(list, ActivityInfoFeignVO.class);
    }

}
