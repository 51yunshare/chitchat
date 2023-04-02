package com.chitchat.admin.api.enumerate;

import com.chitchat.common.enumerate.IntegerValuedEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 基本枚举类型，表示字典类型
 * @author Administrator
 *
 */
public enum EnumDictType implements IntegerValuedEnum {
	PROJECT_TYPE("项目类别", 1, "项目类别"),
	GIFT_TYPE("礼物类别", 2, "礼物类别"),
	GOODS_CATEGORY("商品类别", 3, "商品类别"),

	;


	// 成员变量
	private String name;

	private int index;

	private String description;

	//构造方法
	EnumDictType(String name, int index, String description) {
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

		for(EnumDictType type : EnumDictType.values()){
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
		for (EnumDictType enumDictType : values()) {
			if (enumDictType.getIndex() == index) {
				return enumDictType.getName();
			}
		}
		return null;
	}

}
