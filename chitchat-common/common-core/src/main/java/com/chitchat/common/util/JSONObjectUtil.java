package com.chitchat.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created by Js on 2022/8/12.
 */
public final class JSONObjectUtil {
    public static JSONObject parseObject(Object o){
        return JSONObject.parseObject(JSONObject.toJSONString(o, SerializerFeature.WriteDateUseDateFormat));
    }

    public static JSONArray parseArray(Object o){
        return JSONArray.parseArray(JSONObject.toJSONString(o, SerializerFeature.WriteDateUseDateFormat));
    }

    public static JSONObject parseObjectNotNull(Object o){
        if(o == null){
            return null;
        }
        return JSONObject.parseObject(o.toString());
    }

    public static JSONArray parseArrayNotNull(Object o){
        if(o == null){
            return null;
        }
        return JSONArray.parseArray(o.toString());
    }

}
