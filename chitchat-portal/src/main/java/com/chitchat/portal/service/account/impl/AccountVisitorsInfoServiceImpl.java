package com.chitchat.portal.service.account.impl;

import cn.hutool.core.date.DateUtil;
import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.base.UserDto;
import com.chitchat.common.redis.RedisKey;
import com.chitchat.common.redis.RedisTemplateUtil;
import com.chitchat.common.util.JSONObjectUtil;
import com.chitchat.common.web.jwt.UserUtils;
import com.chitchat.portal.bean.account.AccountVisitorsInfo;
import com.chitchat.portal.dao.account.AccountVisitorsInfoDaoI;
import com.chitchat.portal.dto.account.AccountVisitorsInfoAddDTO;
import com.chitchat.portal.dto.account.VisitorsInfoPageListRequestDTO;
import com.chitchat.portal.service.account.AccountInfoServiceI;
import com.chitchat.portal.service.account.AccountVisitorsInfoServiceI;
import com.chitchat.portal.vo.account.AccountVisitorsInfoVO;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户访客service实现类
 */
@Service
@RequiredArgsConstructor
public class AccountVisitorsInfoServiceImpl extends BaseServicesImpl<AccountVisitorsInfo, AccountVisitorsInfoDaoI> implements AccountVisitorsInfoServiceI {

    @Lazy
    private final AccountInfoServiceI accountInfoServiceI;
    private final RedisTemplateUtil redisTemplateUtil;

    /**
     * 访客信息列表
     *
     * @param pageListRequest
     * @return
     */
    @Override
    public ResultTemplate getList(VisitorsInfoPageListRequestDTO pageListRequest) {
        pageListRequest.setAccountId(UserUtils.getUser().getId());
        PageInfo<AccountVisitorsInfoVO> data = list(pageListRequest);
        return new ResultTemplate(data, JSONObjectUtil.parseArray(data.getList()));
    }

    /**
     * 访客信息新增
     *
     * @param dto
     */
    @Override
    @Transactional
    public void doAdd(AccountVisitorsInfoAddDTO dto) {
        UserDto userDto = UserUtils.getUser();
        accountInfoServiceI.getById(dto.getAccountId(), "用户信息");
        //判断是否已经添加，添加过更新时间
        AccountVisitorsInfo accountVisitorsInfo = baseDaoT.getByAccountIdAndVisitorId(dto.getAccountId(), userDto.getId());
        if (accountVisitorsInfo == null){
            baseDaoT.insert(new AccountVisitorsInfo()
                    .setVisitorId(userDto.getId())
                    .setAccountId(dto.getAccountId()));
            //访客数量+1
            redisTemplateUtil.incr(RedisKey.getVisitorsNum(dto.getAccountId()));
            return;
        }
        //已经访问过，跟新访问时间
        baseDaoT.updateByPrimaryKeySelective(new AccountVisitorsInfo()
                .setId(accountVisitorsInfo.getId())
                .setCreatedTime(DateUtil.date()));
    }
}
