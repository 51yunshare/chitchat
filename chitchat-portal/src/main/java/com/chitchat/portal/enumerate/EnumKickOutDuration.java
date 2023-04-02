package com.chitchat.portal.enumerate;



import cn.hutool.core.date.DateUtil;
import com.chitchat.common.enumerate.IntegerValuedEnum;

import java.util.*;

/**
 * 基本枚举类型，表示用户f封禁时长
 *
 * @author Administrator
 */
public enum EnumKickOutDuration implements IntegerValuedEnum {
    HOUR("1 hour", 1, "1 小时"),
    DAY("1 day", 2, "1 天"),
    FOREVER("Forever", 3, "永远"),

    ;


    // 成员变量
    private String name;

    private int index;

    private String description;

    //构造方法
    EnumKickOutDuration(String name, int index, String description) {
        this.name = name;
        this.index = index;
        this.description = description;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static List<Map<String, Object>> getMapInfo() {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (EnumKickOutDuration type : EnumKickOutDuration.values()) {
            Map<String, Object> ele = new TreeMap<String, Object>();
            ele.put("id", type.index);
            ele.put("name", type.name);
            result.add(ele);
        }
        return result;

    }

    public static String getNameByIndex(Integer index) {
        if (index == null) {
            return null;
        }
        for (EnumKickOutDuration enumUserSource : values()) {
            if (enumUserSource.getIndex() == index) {
                return enumUserSource.getName();
            }
        }
        return null;
    }

    /**
     * 计算最终日期
     *
     * @param enumKickOutDuration
     * @return
     */
    public static Date getKickOutEndTime(EnumKickOutDuration enumKickOutDuration){
        switch (enumKickOutDuration){
            case DAY:
                return DateUtil.offsetDay(DateUtil.date(), 1);
            case HOUR:
                return DateUtil.offsetHour(DateUtil.date(), 1);
            default:
                return null;
        }
    }
}
