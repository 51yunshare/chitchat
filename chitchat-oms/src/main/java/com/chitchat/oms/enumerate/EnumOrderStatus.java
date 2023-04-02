package com.chitchat.oms.enumerate;



import com.chitchat.common.enumerate.IntegerValuedEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 基本枚举类型，表示订单状态
 *
 * @author Administrator
 */
public enum EnumOrderStatus implements IntegerValuedEnum {
    PENDING_PAYMENT(101, "待支付"),
    USER_CANCEL(102, "用户取消"),
    AUTO_CANCEL(103, "系统自动取消"),

    PAYED(201, "已支付"),
    APPLY_REFUND(202, "申请退款"),
    REFUNDED(203, "已退款"),

    DELIVERED(301, "已发货"),

    USER_RECEIVE(401, "用户收货"),
    AUTO_RECEIVE(402, "系统自动收货"),

    FINISHED(901, "已完成");

    ;


    // 成员变量
    private int index;
    private String name;

    //构造方法
    EnumOrderStatus(int index, String name) {
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
        for (EnumOrderStatus type : EnumOrderStatus.values()) {
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
        for (EnumOrderStatus enumUserSource : values()) {
            if (enumUserSource.getIndex() == index) {
                return enumUserSource.getName();
            }
        }
        return null;
    }

}
