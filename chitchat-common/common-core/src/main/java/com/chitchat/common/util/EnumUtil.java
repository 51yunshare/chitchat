package com.chitchat.common.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chitchat.common.enumerate.IntegerValuedEnum;
import com.chitchat.common.enumerate.StringValuedEnum;

import java.lang.reflect.Field;
import java.util.Vector;

public class EnumUtil {


	public static <T extends Enum & IntegerValuedEnum> T valueOf(Class<T> enumClass, int index) {
		if (enumClass == null) {
			return null;
		}
		for (IntegerValuedEnum theEnum : enumClass.getEnumConstants()) {
			if (theEnum.getIndex() == index) {
				return (T)theEnum;
			}
		}
		return null;
	}

	public static <T extends Enum & IntegerValuedEnum> T valueOf(Class<T> enumClass, String index) {
		if (enumClass == null) {
			return null;
		}
		if (StringUtil.format(index).equals("") || !StringUtil.isInt(index)) {
			return null;
		} else {
			int indexInt = StringUtil.parseInt(index);
			return valueOf(enumClass, indexInt);
		}
	}

	public static <T extends Enum & IntegerValuedEnum> JSONArray toMap(Class<T> enumClass , String... fields){
		JSONArray result = new JSONArray();

		for (IntegerValuedEnum theEnum : enumClass.getEnumConstants()) {
			JSONObject ele = new JSONObject();
			ele.put("index" , theEnum.getIndex());
			ele.put("name" , theEnum.getName());
			if(fields != null){
				for (String f : fields) {
					try {
						Field field = enumClass.getDeclaredField(f);
						field.setAccessible(true);
						ele.put(f , field.get(theEnum));
					} catch (NoSuchFieldException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
			result.add(ele);
		}
		return result;
	}

	public static <T extends Enum & StringValuedEnum> T stringValueOf(Class<T> enumClass, String index) {
		if (enumClass == null || index == null || index.equals("")) {
			return null;
		}

		for (StringValuedEnum theEnum : enumClass.getEnumConstants()) {
			if (theEnum.getIndex().equals(index)) {
				return (T) theEnum;
			}
		}
		return null;

	}

	public static <T extends Enum & IntegerValuedEnum> Vector getVector(Class<T> enumClass) {
		if (enumClass == null) {
			return null;
		}
		Vector vector = new Vector();
		for (IntegerValuedEnum theEnum : enumClass.getEnumConstants()) {
			Object[] values = new Object[2];
			values[0] = theEnum.getIndex();
			values[1] = theEnum.getName();
			vector.add(values);
		}
		return vector;
	}
}
