package com.chitchat.portal.service.account.impl;

import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.util.BeanUtils;
import com.chitchat.portal.api.dto.AccountBackpackInfoAddDTO;
import com.chitchat.portal.bean.account.AccountBackpackInfo;
import com.chitchat.portal.dao.account.AccountBackpackDaoI;
import com.chitchat.portal.dto.account.BackpackPageListRequestDTO;
import com.chitchat.portal.service.account.AccountBackpackServiceI;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

/**
 * 用户背包关联service实现类
 */
@Service
public class AccountBackpackServiceImpl extends BaseServicesImpl<AccountBackpackInfo, AccountBackpackDaoI> implements AccountBackpackServiceI {

    /**
     * 背包列表
     *
     * @param dto
     * @return
     */
    @Override
    public ResultTemplate getList(BackpackPageListRequestDTO dto) {
        PageHelper.startPage(dto.getCp(), dto.getRows());
        PageInfo<AccountBackpackInfo> data = new PageInfo(baseDaoT.list(dto));
        return new ResultTemplate(data, data.getList());
    }

    /**
     * 背包新增记录
     *
     * @param dto
     */
    @Override
    public void doAdd(AccountBackpackInfoAddDTO dto) {
        AccountBackpackInfo backpackInfo = BeanUtils.copyProperties(dto, AccountBackpackInfo.class);
        baseDaoT.insert(backpackInfo);
    }
}
