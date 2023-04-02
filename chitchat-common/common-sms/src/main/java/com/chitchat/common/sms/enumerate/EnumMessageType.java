package com.chitchat.common.sms.enumerate;

import com.chitchat.common.enumerate.IntegerValuedEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 基本枚举类型，表示短信类型
 *
 * @author Administrator
 */

public enum EnumMessageType implements IntegerValuedEnum {
    用户注册("用户注册", 1, "您正在申请手机注册，验证码为：%s", "register", "code"),

    ;

    // 成员变量
    private String name;

    private int index;

    private String description;

    private String[] paramName;

    private String configName;


    //构造方法
    private EnumMessageType(String name, int index, String description, String configName, String... paramName) {
        this.name = name;
        this.index = index;
        this.description = description;
        this.configName = configName;
        this.paramName = paramName;
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

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String[] getParamName() {
        return paramName;
    }

    public void setParamName(String[] paramName) {
        this.paramName = paramName;
    }

    public static List<Map<String, Object>> getMapInfo() {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (EnumMessageType type : EnumMessageType.values()) {
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
        for (EnumMessageType enumType : values()) {
            if (enumType.getIndex() == index) {
                return enumType.getName();
            }
        }
        return null;
    }
}
