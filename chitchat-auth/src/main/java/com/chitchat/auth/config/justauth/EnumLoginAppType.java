package com.chitchat.auth.config.justauth;

import lombok.Getter;
import me.zhyd.oauth.config.AuthDefaultSource;

import java.util.HashMap;
import java.util.Map;

/**
 * 第三方登录App类型
 */
@Getter
public enum EnumLoginAppType {

    FACEBOOK("facebook", 1, AuthDefaultSource.FACEBOOK),
    GOOGLE("google", 2, AuthDefaultSource.GOOGLE),

    ;

    // 成员变量
    private String name;

    private int index;
    private AuthDefaultSource authSource;

    //构造方法
    private EnumLoginAppType(String name, int index, AuthDefaultSource authSource) {
        this.name = name;
        this.index = index;
        this.authSource = authSource;
    }

    public static EnumLoginAppType getByName(String name) {
        if (name == null) {
            return null;
        }
        for (EnumLoginAppType enumDictType : values()) {
            if (enumDictType.getName().equalsIgnoreCase(name)) {
                return enumDictType;
            }
        }
        return null;
    }

    public static Map<String, String> getMapInfo() {
        Map<String, String> result = new HashMap<>();
        for (EnumLoginAppType type : EnumLoginAppType.values()) {
            result.put(type.name  + "登录", type.name);
        }
        return result;
    }
}
