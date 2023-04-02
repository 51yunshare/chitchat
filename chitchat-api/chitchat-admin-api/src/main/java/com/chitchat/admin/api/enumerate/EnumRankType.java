package com.chitchat.admin.api.enumerate;

import com.chitchat.common.enumerate.IntegerValuedEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 基本枚举类型，表示排行榜类型
 *
 * @author Administrator
 *
 */
public enum EnumRankType implements IntegerValuedEnum {
	RANK_DAILY("日榜", 1, "日榜"),
	RANK_WEEKLY("周榜", 2, "周榜"),
	RANK_MONTHLY("月榜", 3, "月榜"),

	;


	// 成员变量
	private String name;

	private int index;

	private String description;

	//构造方法
	EnumRankType(String name, int index, String description) {
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

		for(EnumRankType type : EnumRankType.values()){
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
		for (EnumRankType enumDictType : values()) {
			if (enumDictType.getIndex() == index) {
				return enumDictType.getName();
			}
		}
		return null;
	}

}
