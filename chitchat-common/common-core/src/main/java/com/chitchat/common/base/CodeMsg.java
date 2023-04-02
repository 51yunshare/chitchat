package com.chitchat.common.base;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Js on 2022/7/23
 * 状态码
 */
@Data
@AllArgsConstructor
public class CodeMsg implements Serializable {
    private int code;
    private String msg;

    //通用的错误码    5001XX
    public static CodeMsg SUCCESS = new CodeMsg(200, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "系统执行出错");
    public static CodeMsg PUBLIC_ERROR = new CodeMsg(500106, "通用异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常");
    public static CodeMsg NULL_ERROR = new CodeMsg(500102, "数据不存在");
    public static CodeMsg OPERATE_ERROR = new CodeMsg(500103, "操作异常");
    public static CodeMsg CAN_NOT_DELETE = new CodeMsg(500104, "不可删除");
    public static CodeMsg RESOURCE_NOT_FOUND = new CodeMsg(500105, "请求资源不存在");
    //登录-用户不存在 300
    public static CodeMsg USER_NOT_EXISTS = new CodeMsg(300, "The user does not exist！");

    //用户模块  5002XX
    public static CodeMsg LOGIN_INVALID= new CodeMsg(500201, "登录失效，请重新登录");
    public static CodeMsg AUTH_LIMIT= new CodeMsg(500202, "用户的授权权限不足");
    public static CodeMsg LOGIN_LIMIT= new CodeMsg(500203, "您的账号已被限制外网登录！");
    public static CodeMsg LOGIN_FAIL_NEED_VER_CODE= new CodeMsg(500204, "登录失败，请填写图形验证码");

    //oss 700xxxx
    public static CodeMsg OSS_ERROR = new CodeMsg(700100, "oss处理失败");
    public static CodeMsg OSS_FILE_NOT_EXIST = new CodeMsg(700100, "file not exist");

    //短信通道 800xxxx
    public static CodeMsg SMS_FAIL = new CodeMsg(800100, "短信发送失败");

    //Auth 5003xxx
    public static CodeMsg USERNAME_PASSWORD_ERROR = new CodeMsg(500300, "用户名或密码错误!");
    public static CodeMsg CREDENTIALS_EXPIRED = new CodeMsg(500301, "该账户的登录凭证已过期，请重新登录!");
    public static CodeMsg ACCOUNT_DISABLED = new CodeMsg(500302, "该账户已被禁用，请联系管理员!");
    public static CodeMsg ACCOUNT_LOCKED = new CodeMsg(500303, "该账号已被锁定，请联系管理员!");
    public static CodeMsg ACCOUNT_EXPIRED = new CodeMsg(500304, "该账号已过期，请联系管理员!");
    public static CodeMsg PERMISSION_DENIED = new CodeMsg(500305, "没有访问权限，请联系管理员!");

    //token 400xxxx
    public static CodeMsg TOKEN_INVALID_OR_EXPIRED = new CodeMsg(400100, "暂未登录或token已经过期");
    public static CodeMsg TOKEN_ACCESS_FORBIDDEN = new CodeMsg(400200, "token已被禁止访问");
    public static CodeMsg AUTHORIZED_ERROR = new CodeMsg(400300, "访问权限异常");
    public static CodeMsg ACCESS_UNAUTHORIZED = new CodeMsg(400400, "访问未授权");
    public static CodeMsg USER_NOT_EXIST = new CodeMsg(400500, "用户不存在");
    public static CodeMsg CLIENT_AUTHENTICATION_FAILED = new CodeMsg(400600, "客户端认证失败");

    // ========== 定时任务 600xxxx ==========
    public static CodeMsg JOB_NOT_EXISTS = new CodeMsg(600100, "定时任务不存在");
    public static CodeMsg JOB_HANDLER_EXISTS = new CodeMsg(600101, "定时任务的处理器已经存在");
    public static CodeMsg JOB_CHANGE_STATUS_INVALID = new CodeMsg(600102, "只允许修改为开启或者关闭状态");
    public static CodeMsg JOB_CHANGE_STATUS_EQUALS = new CodeMsg(600103, "定时任务已经处于该状态，无需修改");
    public static CodeMsg JOB_UPDATE_ONLY_NORMAL_STATUS = new CodeMsg(600104, "只有开启状态的任务，才可以修改");
    public static CodeMsg JOB_CRON_EXPRESSION_VALID = new CodeMsg(600105, "CRON 表达式不正确");

    // Room 900xxx
    public static CodeMsg GAME_ROOM_NOT_EXISTS = new CodeMsg(900100, "The game room does not exist");
}
