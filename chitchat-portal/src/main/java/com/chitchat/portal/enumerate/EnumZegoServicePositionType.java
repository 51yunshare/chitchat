package com.chitchat.portal.enumerate;



import com.chitchat.common.enumerate.IntegerValuedEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 基本枚举类型，表示即构产品服务地理位置
 *
 * @author Administrator
 */
public enum EnumZegoServicePositionType implements IntegerValuedEnum {
    中国大陆("sha", 1, "上海"),
    港澳台("hkg", 2, "香港"),
    欧洲("fra", 3, "法兰克福"),
    美西("lax", 4, "加州"),
    亚太("bom", 5, "亚太"),

    ;


    // 成员变量
    private String name;

    private int index;

    private String description;

    //构造方法
    EnumZegoServicePositionType(String name, int index, String description) {
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
        for (EnumZegoServicePositionType type : EnumZegoServicePositionType.values()) {
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
        for (EnumZegoServicePositionType enumUserSource : values()) {
            if (enumUserSource.getIndex() == index) {
                return enumUserSource.getName();
            }
        }
        return null;
    }

}
