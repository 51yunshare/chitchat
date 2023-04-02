package com.chitchat.portal.bean.account;

import com.chitchat.common.base.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户关注
 *
 * Created by Js on 2022/7/31.
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountFollowLink extends BaseBean {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 关注类型1-用户2-房间
     */
    private Integer followType;

    /**
     * 关注目标id
     */
    private Long targetId;

    /**
     * 用户id
     */
    private Long accountId;

    /**
     * 关注状态
     */
    private Integer followStatus;

    /**
     * 是否互关
     */
    private Integer friend;

}
