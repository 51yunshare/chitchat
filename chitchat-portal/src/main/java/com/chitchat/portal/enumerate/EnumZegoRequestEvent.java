package com.chitchat.portal.enumerate;



import com.chitchat.common.enumerate.IntegerValuedEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 基本枚举类型，表示即构请求事件类型
 *
 * @author Administrator
 */
public enum EnumZegoRequestEvent implements IntegerValuedEnum {

    /*** 房间信令接口 ***/
    推送广播消息("推送广播消息", 1, "SendBroadcastMessage"),
    推送弹幕消息("推送弹幕消息", 2, "SendBarrageMessage"),
    推送自定义消息("推送自定义消息", 3, "SendCustomCommand"),
    获取房间人数("获取房间人数", 4, "DescribeUserNum"),
    获取房间用户列表("获取房间用户列表", 5, "DescribeUserList"),
    踢出房间用户("踢出房间用户", 6, "KickoutUser"),
    增加房间流("增加房间流", 7, "AddStream"),
    删除房间流("删除房间流", 8, "DeleteStream"),
    获取简易流列表("获取简易流列表", 9, "DescribeSimpleStreamList"),
    关闭房间("关闭房间", 10, "CloseRoom"),




    ;


    // 成员变量
    private String name;

    private int index;

    private String uri;

    //构造方法
    EnumZegoRequestEvent(String name, int index, String uri) {
        this.name = name;
        this.index = index;
        this.uri = uri;
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public static List<Map<String, Object>> getMapInfo() {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (EnumZegoRequestEvent type : EnumZegoRequestEvent.values()) {
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
        for (EnumZegoRequestEvent enumUserSource : values()) {
            if (enumUserSource.getIndex() == index) {
                return enumUserSource.getName();
            }
        }
        return null;
    }

}
