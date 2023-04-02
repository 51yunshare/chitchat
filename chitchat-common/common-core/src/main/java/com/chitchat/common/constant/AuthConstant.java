package com.chitchat.common.constant;

/**
 * 权限相关常量定义
 *
 * Created by Js on 2022/7/30.
 **/
public interface AuthConstant {

    /**
     * JWT存储权限前缀
     */
    String AUTHORITY_PREFIX = "ROLE_";

    /**
     * JWT存储权限属性
     */
    String AUTHORITY_CLAIM_NAME = "authorities";

    /**
     * 后台管理client_id
     */
    String ADMIN_CLIENT_ID = "admin-app";
    String ADMIN_CLIENT_SECRET = "123456";

    /**
     * 前台商城client_id
     */
    String PORTAL_CLIENT_ID = "portal-app";
    String PORTAL_CLIENT_SECRET = "123456";

    /**
     * 后台管理接口路径匹配
     */
    String ADMIN_URL_PATTERN = "/chitchat-admin/**";

    /**
     * Redis缓存权限规则key
     */
    String RESOURCE_ROLES_MAP_KEY = "auth:resourceRolesMap";

    /**
     * 认证信息Http请求头
     */
    String JWT_TOKEN_HEADER = "Authorization";

    /**
     * JWT令牌前缀
     */
    String JWT_TOKEN_PREFIX = "Bearer ";

    /**
     * 用户信息Http请求头
     */
    String USER_TOKEN_HEADER = "user";
    String USER_ID_HEADER = "user_id";

    /**
     * JWT载体key
     */
    String JWT_PAYLOAD_KEY = "payload";
    /**
     * username
     */
    String USERNAME_KEY = "username";
    String USER_NAME_KEY = "user_name";

    String CLIENT_ID_KEY = "client_id";
    /**
     * 黑名单token前缀
     */
    String TOKEN_BLACKLIST_PREFIX = "auth:token:blacklist:";

    /**
     * JWT ID 唯一标识
     */
    String JWT_JTI = "jti";

    /**
     * JWT ID 唯一标识
     */
    String JWT_EXP = "exp";
}
