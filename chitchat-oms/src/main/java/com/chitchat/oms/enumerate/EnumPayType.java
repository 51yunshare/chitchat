package com.chitchat.oms.enumerate;



import com.chitchat.common.enumerate.IntegerValuedEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 基本枚举类型，表示支付类型
 *
 * @author Administrator
 */
public enum EnumPayType implements IntegerValuedEnum {
    WX_JSAPI(1, "微信JSAPI支付"),
    ALIPAY(2, "支付宝支付"),
    BALANCE(3, "会员余额支付"),
    WX_APP(4, "微信APP支付"),
    GOOGLE_PAY(5, "谷歌支付")

    ;


    // 成员变量
    private int index;
    private String name;

    //构造方法
    EnumPayType(int index, String name) {
        this.name = name;
        this.index = index;
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

    public static List<Map<String, Object>> getMapInfo() {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (EnumPayType type : EnumPayType.values()) {
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
        for (EnumPayType enumUserSource : values()) {
            if (enumUserSource.getIndex() == index) {
                return enumUserSource.getName();
            }
        }
        return null;
    }

}
