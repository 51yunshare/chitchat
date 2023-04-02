package com.chitchat.common.base;

import com.alibaba.fastjson.JSONObject;
import com.chitchat.common.util.JSONObjectUtil;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Js on 2022/7/25.
 * 返回结果信息
 */
@Data
@Builder
@AllArgsConstructor
public class ResultTemplate implements Serializable {
    private int code;
    private String msg = "";
    private Object data = new JSONObject();


    public ResultTemplate() {
        this.code = CodeMsg.SUCCESS.getCode();
    }

    public ResultTemplate(String msg) {
        this.code = CodeMsg.PUBLIC_ERROR.getCode();
        this.msg = msg;
    }

    public ResultTemplate(CodeMsg codeMsg) {
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
        this.data = codeMsg.getMsg();
    }

    public ResultTemplate(CodeMsg codeMsg, Object data) {
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
        this.data = data;
    }

    public ResultTemplate(Object data) {
        this.code = CodeMsg.SUCCESS.getCode();
        this.data = data;
    }

    public ResultTemplate(int status, String msg) {
        this.code = status;
        this.msg = msg;
    }

    public ResultTemplate(PageInfo pageInfo, Object data) {
        this.code = CodeMsg.SUCCESS.getCode();
        JSONObject result = getPageInfoJSON(pageInfo);
        result.put("data", data);
        this.data = result;
    }

    private JSONObject getPageInfoJSON(PageInfo pageInfo) {
        JSONObject result = new JSONObject();
        //总页数
        result.put("pages", pageInfo.getPages());
        //每页的数量
        result.put("pageSize", pageInfo.getPageSize());
        //总记录数
        result.put("total", pageInfo.getTotal());
        //当前页
        result.put("cp", pageInfo.getPageNum());
        //当前页的数量
        result.put("size", pageInfo.getSize());
        return JSONObjectUtil.parseObject(result);
    }

    /**
     * 未登录返回结果
     */
    public static ResultTemplate tokenInvalidOrExpired(Object data) {
        return new ResultTemplate(CodeMsg.TOKEN_INVALID_OR_EXPIRED.getCode(), CodeMsg.TOKEN_INVALID_OR_EXPIRED.getMsg(), data);
    }
}
