package com.chitchat.common.web.jwt;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.chitchat.common.base.UserDto;
import com.chitchat.common.constant.AuthConstant;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

/**
 * JWT工具类
 *
 * Created by Js on 2022/7/31.
 */
@Slf4j
public class UserUtils {

    /**
     * 解析JWT获取用户ID
     *
     * @return
     */
    public static Long getUserId() {
        Long userId = null;
        JSONObject jwtPayload = JwtUtils.getJwtPayload();
        if (jwtPayload != null) {
            userId = jwtPayload.getLong("id");
        }
        return userId;
    }

    @Deprecated
    public static Long getMemberId() {
        Long memberId = null;
        JSONObject jwtPayload =  JwtUtils.getJwtPayload();
        if (jwtPayload != null) {
            memberId = jwtPayload.getLong("id");
        }
        return memberId;
    }

    /**
     * 解析JWT获取获取用户名
     *
     * @return
     */
    public static String getUsername() {
        String username =  JwtUtils.getJwtPayload().getStr(AuthConstant.USER_NAME_KEY);
        return username;
    }

    public static UserDto getUser() {
        JSONObject jwtPayload =  JwtUtils.getJwtPayload();
        if (jwtPayload != null) {
            return JSONUtil.toBean(jwtPayload, UserDto.class);
        }
        return null;
    }


    /**
     * JWT获取用户角色列表
     *
     * @return 角色列表
     */
    public static List<String> getRoles() {
        List<String> roles;
        JSONObject payload =  JwtUtils.getJwtPayload();
        if (payload.containsKey(AuthConstant.AUTHORITY_CLAIM_NAME)) {
            roles = payload.getJSONArray(AuthConstant.AUTHORITY_CLAIM_NAME).toList(String.class);
        } else {
            roles = Collections.emptyList();
        }
        return roles;
    }

    /**
     * 是否「超级管理员」
     *
     * @return
     */
    public static boolean isRoot() {
        List<String> roles = getRoles();
        return CollectionUtil.isNotEmpty(roles) && roles.contains("ROOT");
    }
}
