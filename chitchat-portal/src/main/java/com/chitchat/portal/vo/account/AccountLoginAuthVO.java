package com.chitchat.portal.vo.account;

import com.chitchat.common.util.EnumUtil;
import com.chitchat.portal.enumerate.EnumIdentityType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 第三方账号
 *
 * Created by Js on 2022/8/11.
 **/
@Data
public class AccountLoginAuthVO implements Serializable {

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

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;


    /*会员信息*/
    private String username;

    private String nickName;

    private String mobile;

    private String email;

    private String icon;

    private Integer gender;
}
