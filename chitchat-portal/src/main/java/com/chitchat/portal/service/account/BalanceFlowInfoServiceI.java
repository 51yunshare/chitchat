package com.chitchat.portal.service.account;

import com.chitchat.common.base.BaseServicesI;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.bean.account.BalanceFlowInfo;
import com.chitchat.portal.dto.account.BalanceFlowPageListRequestDTO;

/**
 * 余额流水
 *
 * Created by Js on 2022/9/16 .
 **/
public interface BalanceFlowInfoServiceI extends BaseServicesI<BalanceFlowInfo> {

    /**
     * 余额流水列表
     *
     * @param dto
     * @return
     */
    ResultTemplate getList(BalanceFlowPageListRequestDTO dto);
}
