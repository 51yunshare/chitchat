package com.chitchat.portal.redis;

import com.alibaba.fastjson.JSONObject;
import com.chitchat.common.base.BaseBean;
import lombok.*;

import java.util.Set;

/**
 * 消息的Bean
 * Created system
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NotifyMessage extends BaseBean {
    // Fields
    private static final long serialVersionUID = 1L;
    /**
     * 主键，自增类型
     */
    private Integer id;

    /*****自定义属性区域begin******/
    /**
     * 目标id房间/用户
     */
    private String targetId;
    /**
     * 发送用户id
     * 发送用户id
     */
    private String fromUserId;
    /**
     * 接收用户id
     * （如果是群组，则此处是群组id）
     */
    private Set<Long> toUserId;
    /**
     * 内容
     * 内容
     */
    private JSONObject content;
}
