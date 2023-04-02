package com.chitchat.common.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应结构体
 *
 **/
@Data
public class Result<T> implements Serializable {

    private int code;

    private T data;

    private String msg;

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(CodeMsg.SUCCESS.getCode());
        result.setMsg(CodeMsg.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

    public static <T> Result<T> failed() {
        return result(CodeMsg.SERVER_ERROR.getCode(), CodeMsg.SERVER_ERROR.getMsg(), null);
    }

    public static <T> Result<T> failed(String msg) {
        return result(CodeMsg.SERVER_ERROR.getCode(), msg, null);
    }

    public static <T> Result<T> judge(boolean status) {
        if (status) {
            return success();
        } else {
            return failed();
        }
    }

    public static <T> Result<T> failed(CodeMsg resultCode) {
        return result(resultCode.getCode(), resultCode.getMsg(), null);
    }

    public static <T> Result<T> failed(CodeMsg resultCode, String msg) {
        return result(resultCode.getCode(), msg, null);
    }

    private static <T> Result<T> result(CodeMsg resultCode, T data) {
        return result(resultCode.getCode(), resultCode.getMsg(), data);
    }

    private static <T> Result<T> result(int code, String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    public static boolean isSuccess(Result<?> result) {
        return result != null && CodeMsg.SUCCESS.getCode() == result.getCode();
    }
}
