package com.chitchat.portal.bean.room;

import com.chitchat.common.base.BaseBean;
import lombok.Data;

import java.io.Serializable;

/**
 * 房间禁止用户表
 *
 * Created by Js on 2022/8/26 .
 */
@Data
public class RoomForbidden extends BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private Long id;

    /**
     * 房间id
     */
    private Long roomId;

    /**
     * 用户id
     */
    private Long accountId;
}
