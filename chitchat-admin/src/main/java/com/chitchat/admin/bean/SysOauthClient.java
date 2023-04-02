package com.chitchat.admin.bean;

import com.chitchat.common.base.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客户端实体
 *
 * Created by Js on 2022/8/10.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysOauthClient extends BaseBean {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * 资源id集合
     */
    private String resourceIds;

    /**
     * 作用域
     */
    private String scope;

    /**
     * 授权方式
     */
    private String authorizedGrantTypes;

    /**
     * 回调地址
     */
    private String webServerRedirectUri;

    /**
     * 权限集合
     */
    private String authorities;

    /**
     * 认证令牌时效()
     */
    private Integer accessTokenValidity;

    /**
     * 刷新令牌时效(单位:秒)
     */
    private Integer refreshTokenValidity;

    /**
     * 扩展信息
     */
    private String additionalInformation;

    /**
     * 是否自动放行
     */
    private String autoApprove;

}
