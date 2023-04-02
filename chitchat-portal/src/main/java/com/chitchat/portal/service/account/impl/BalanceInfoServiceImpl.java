package com.chitchat.portal.service.account.impl;

import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.common.base.CodeMsg;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.exception.ChitchatException;
import com.chitchat.common.util.BeanUtils;
import com.chitchat.common.util.BigDecimalUtil;
import com.chitchat.common.web.jwt.UserUtils;
import com.chitchat.portal.api.enumerate.EnumBalanceFlowType;
import com.chitchat.portal.api.enumerate.EnumBalanceType;
import com.chitchat.portal.bean.account.BalanceFlowInfo;
import com.chitchat.portal.bean.account.BalanceInfo;
import com.chitchat.portal.dao.account.BalanceInfoDaoI;
import com.chitchat.portal.dto.account.BalancePageListRequestDTO;
import com.chitchat.portal.service.account.BalanceFlowInfoServiceI;
import com.chitchat.portal.service.account.BalanceInfoServiceI;
import com.chitchat.portal.vo.account.BalanceInfoListVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * Created by Js on 2022/9/16 .
 **/
@Service
public class BalanceInfoServiceImpl extends BaseServicesImpl<BalanceInfo, BalanceInfoDaoI> implements BalanceInfoServiceI {

    @Resource
    private BalanceFlowInfoServiceI balanceFlowInfoServiceI;

    /**
     * 余额查询
     *
     * @param dto
     * @return
     */
    @Override
    public ResultTemplate getList(BalancePageListRequestDTO dto) {
        dto.setUserInfo(UserUtils.getUser());
        List<BalanceInfo> dataList = baseDaoT.list(dto);
        return new ResultTemplate(BeanUtils.convertList(dataList, BalanceInfoListVO.class));
    }

    /**
     * 查询用户余额
     *
     * @param accountId
     * @return
     */
    @Override
    public BalanceInfo getByAccountId(Long accountId) {
        return baseDaoT.getByAccountId(accountId);
    }

    /**
     * 用户余额判断和扣减
     *
     * @param accountId
     * @param giftAmount
     * @return
     */
    @Override
    public boolean accountBalanceAndSub(Long accountId, BigDecimal giftAmount) {
        final BalanceInfo prdBalanceInfo = baseDaoT.getByAccountId(accountId);
        if (prdBalanceInfo == null || prdBalanceInfo.getCoinsBalance().compareTo(giftAmount) < 0) {
            throw new ChitchatException(CodeMsg.OPERATE_ERROR, "当前金币余额不足，请进行充值！");
        }
        int num = baseDaoT.accountBalanceSubCoins(accountId, giftAmount);
        if (num > 0){
            //todo 流水记录
            final BalanceInfo postBalanceInfo = baseDaoT.getByAccountId(accountId);
            balanceFlowInfoServiceI.insert(BalanceFlowInfo.builder()
                    .type(EnumBalanceType.金币.getIndex())
                    .accountId(accountId)
                    .balanceId(prdBalanceInfo.getId())
                    .flowType(EnumBalanceFlowType.送礼.getIndex())
                    .preBalanceNum(prdBalanceInfo.getCoinsBalance() == null ? BigDecimal.ZERO : prdBalanceInfo.getCoinsBalance())
                    .curOrderNum(BigDecimalUtil.getMinus(giftAmount))
                    .postBalanceNum(postBalanceInfo.getCoinsBalance() == null ? BigDecimal.ZERO : postBalanceInfo.getCoinsBalance())
                    .build());
            return true;
        }
        return false;
    }

    /**
     * 用户余额判断
     *
     * @param accountId
     * @param goodsPrice
     * @return
     */
    @Override
    public boolean checkBalance(Long accountId, BigDecimal goodsPrice) {
        final BalanceInfo prdBalanceInfo = baseDaoT.getByAccountId(accountId);
        if (prdBalanceInfo == null || prdBalanceInfo.getCoinsBalance().compareTo(goodsPrice) < 0) {
            throw new ChitchatException(CodeMsg.SERVER_ERROR, "当前金币余额不足，请进行充值！");
        }
        return true;
    }
}
