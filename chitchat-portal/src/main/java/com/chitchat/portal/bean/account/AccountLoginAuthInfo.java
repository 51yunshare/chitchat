package com.chitchat.portal.bean.account;

import com.chitchat.common.base.BaseBean;
import com.chitchat.common.util.EnumUtil;
import com.chitchat.portal.enumerate.EnumIdentityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 第三方账号
 *
 * Created by Js on 2022/8/11.
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountLoginAuthInfo extends BaseBean {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 会员id
     */
    private Long accountId;
    /**
     * 平台唯一标识
     */
    private String identifier;
    /**
     * 应用类型(facebook,qq,微信)
     */
    private Integer identityType;
    private EnumIdentityType enumIdentityType;
    /**
     * 密码凭证
     */
    private String credential;
    /**
     * 绑定标识
     */
    private Integer bindFlag;

    public void setIdentityType(Integer identityType) {
        this.identityType = identityType;
        this.enumIdentityType = identityType == null ? null : EnumUtil.valueOf(EnumIdentityType.class, identityType);
    }
}
