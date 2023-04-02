package com.chitchat.common.base;

import com.github.pagehelper.PageInfo;

/**
 * Created by Js on 2022/7/25.
 */
public class BaseController {

    public ResultTemplate success(String msg) {
        return new ResultTemplate(100, msg);
    }

    public ResultTemplate fail(String msg) {
        return new ResultTemplate(msg);
    }

    public ResultTemplate success() {
        return new ResultTemplate();
    }

    public ResultTemplate success(Object data) {
        return new ResultTemplate(data);
    }

    public ResultTemplate success(CodeMsg codeMsg) {
        return new ResultTemplate(codeMsg.getCode(), codeMsg.getMsg());
    }

    /**
     * 分页返回
     *
     * @param pageInfo
     * @param data
     * @return
     */
    public ResultTemplate success(PageInfo pageInfo, Object data) {
        return new ResultTemplate(pageInfo, data);
    }

}
