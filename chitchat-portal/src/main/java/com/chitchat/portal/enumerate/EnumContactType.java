package com.chitchat.portal.enumerate;



import com.chitchat.common.enumerate.IntegerValuedEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 基本枚举类型，表示用户反馈联系方式
 *
 * @author Administrator
 */
public enum EnumContactType implements IntegerValuedEnum {
    WhatsApp("WhatsApp", 1, "WhatsApp"),
    Email("Email", 2, "Email"),
    Phone("Phone", 3, "Phone"),

    ;


    // 成员变量
    private String name;

    private int index;

    private String description;

    //构造方法
    EnumContactType(String name, int index, String description) {
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
        for (EnumContactType type : EnumContactType.values()) {
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
        for (EnumContactType enumUserSource : values()) {
            if (enumUserSource.getIndex() == index) {
                return enumUserSource.getName();
            }
        }
        return null;
    }

}
