package com.chitchat.admin.enumerate;

import com.chitchat.common.enumerate.IntegerValuedEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 基本枚举类型，表示活动类型
 * @author Administrator
 *
 */
public enum EnumActivityType implements IntegerValuedEnum {
	首页Banner("首页Banner", 1, "首页轮播"),
	其他("其他", 2, "其他"),

	;


	// 成员变量
	private String name;

	private int index;

	private String description;

	//构造方法
	EnumActivityType(String name, int index, String description) {
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

		for(EnumActivityType type : EnumActivityType.values()){
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
		for (EnumActivityType enumDictType : values()) {
			if (enumDictType.getIndex() == index) {
				return enumDictType.getName();
			}
		}
		return null;
	}

}
