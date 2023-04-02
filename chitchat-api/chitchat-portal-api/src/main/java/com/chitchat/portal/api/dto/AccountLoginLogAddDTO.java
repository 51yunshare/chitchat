package com.chitchat.portal.api.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录日志
 *
 * Created by Js on 2022/8/27 .
 */
@Data
@Builder
public class AccountLoginLogAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登录用户id
     */
    private Long accountId;
}
