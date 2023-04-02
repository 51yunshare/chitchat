package com.chitchat.common.enumerate;

import lombok.Getter;

/**
 * 业务类型枚举
 *
 * @author haoxr
 * @date 2021-02-17
 */
@Getter
public enum EnumBusinessType {

    USER(10, "用户", 4),
    MEMBER(20, "会员", 4),
    ORDER(30, "订单", 6),
    ROOM(40, "房间", 4),
    GAME_ROOM(50, "游戏房间号", 6),

    ;

    private int value;
    private String label;
    private int digit;

    EnumBusinessType(Integer value, String label, int digit) {
        this.value = value;
        this.label = label;
        this.digit = digit;
    }

}
