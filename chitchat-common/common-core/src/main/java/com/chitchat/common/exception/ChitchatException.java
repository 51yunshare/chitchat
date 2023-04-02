package com.chitchat.common.exception;


import com.chitchat.common.base.BaseException;
import com.chitchat.common.base.CodeMsg;

/**
 * Created by Js on 2022/7/23.
 */
public class ChitchatException extends BaseException {

    public ChitchatException(Integer code , String msg , String detailMsg){
        super(code , msg ,detailMsg);
        this.exception = this.getClass().getName();
    }
    public ChitchatException(String msg) {
        super(msg);
        this.exception = this.getClass().getName();
    }

    public ChitchatException(CodeMsg codeMsg) {
        super(codeMsg);
        this.exception = this.getClass().getName();
    }

    public ChitchatException(CodeMsg codeMsg, String msg) {
        super(codeMsg, msg);
        this.exception = this.getClass().getName();
    }
}
