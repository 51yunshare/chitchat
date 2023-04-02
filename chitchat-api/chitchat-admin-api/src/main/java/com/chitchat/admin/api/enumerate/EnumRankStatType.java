package com.chitchat.admin.api.enumerate;

import com.chitchat.common.enumerate.IntegerValuedEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 基本枚举类型，表示排行榜统计类型
 *
 * @author Administrator
 *
 */
public enum EnumRankStatType implements IntegerValuedEnum {
	ROOM_CONTRIBUTION("房间贡献值", 1, "房间贡献值"),
	SEND_GIFT("送礼者", 2, "送礼者"),
	RECEIVED_GIFTS("收礼者", 3, "收礼者"),

	;


	// 成员变量
	private String name;

	private int index;

	private String description;

	//构造方法
	EnumRankStatType(String name, int index, String description) {
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

	public static List<Map<String, Object>> getMapInfo(){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		for(EnumRankStatType type : EnumRankStatType.values()){
			Map<String, Object> ele = new TreeMap<String, Object>();
			ele.put("id",type.index);

			ele.put("name",type.name);
			result.add(ele);
		}
		return result;

	}

	public static String getNameByIndex(Integer index) {
		if (index == null) {
			return null;
		}
		for (EnumRankStatType enumDictType : values()) {
			if (enumDictType.getIndex() == index) {
				return enumDictType.getName();
			}
		}
		return null;
	}

}
