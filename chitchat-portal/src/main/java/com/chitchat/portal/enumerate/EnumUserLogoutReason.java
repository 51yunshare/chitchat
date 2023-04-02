package com.chitchat.portal.enumerate;



import com.chitchat.common.enumerate.IntegerValuedEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 基本枚举类型，表示退出房间理由
 * 0：正常退出 1：心跳超时退出 2：用户断线退出 3：调用后台接口被踢出 4：token 过期退出
 *
 * @author Administrator
 */
public enum EnumUserLogoutReason implements IntegerValuedEnum {
    正常退出("正常退出", 0, "正常退出"),
    心跳超时退出("心跳超时退出", 1, "心跳超时退出"),
    用户断线退出("用户断线退出", 2, "用户断线退出"),
    调用后台接口被踢出("调用后台接口被踢出", 3, "调用后台接口被踢出"),
    token过期退出("token过期退出", 4, "token 过期退出"),
    房主销毁房间("房主销毁房间", 5, "房主销毁房间退出"),

    ;


    // 成员变量
    private String name;

    private int index;

    private String description;

    //构造方法
    EnumUserLogoutReason(String name, int index, String description) {
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
        for (EnumUserLogoutReason type : EnumUserLogoutReason.values()) {
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
        for (EnumUserLogoutReason enumUserSource : values()) {
            if (enumUserSource.getIndex() == index) {
                return enumUserSource.getName();
            }
        }
        return null;
    }

}
