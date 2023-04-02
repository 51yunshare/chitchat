package com.chitchat.common.base;

import lombok.Data;

/**
 * Created by Js on 2022/7/23
 */
@Data
public class BaseException extends RuntimeException{
    public int code;
    public String msg;
    public String detailMsg;
    public String exception;
    public BaseException(){}

    public BaseException(Integer code , String msg , String detailMsg){
        super(msg);
        this.code = code;
        this.msg = msg;
        this.detailMsg = detailMsg;
    }

    public BaseException(String msg){
        super(msg);
        this.msg = msg;
    }
    public BaseException(CodeMsg codeMsg){
        super(codeMsg.getMsg());
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }
    public BaseException(CodeMsg codeMsg , String msg){
        super(codeMsg.getMsg());
        this.code = codeMsg.getCode();
        this.msg = msg;
        this.detailMsg = msg;
    }
}
