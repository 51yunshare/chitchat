package com.chitchat.auth.exception;

import com.chitchat.common.base.CodeMsg;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.exception.ChitchatException;
import com.chitchat.common.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class AuthExceptionHandler {

    /**
     * 用户不存在
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResultTemplate handleUsernameNotFoundException(UsernameNotFoundException e) {
        log.error(">>>>> 认证中心：接口出现异常(用户不存在)。url=【" + ServletUtils.getRequest().getRequestURI() + "】,异常消息=" + ExceptionUtils.getStackTrace(e));
        return new ResultTemplate(CodeMsg.USER_NOT_EXIST);
    }

    /**
     * 用户名和密码异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(InvalidGrantException.class)
    public ResultTemplate handleInvalidGrantException(InvalidGrantException e) {
        log.error(">>>>> 认证中心：接口出现异常(用户名和密码异常)。url=【" + ServletUtils.getRequest().getRequestURI() + "】,异常消息=" + ExceptionUtils.getStackTrace(e));
        return new ResultTemplate(CodeMsg.USERNAME_PASSWORD_ERROR, e.getMessage());
    }


    /**
     * 账户异常(禁用、锁定、过期)
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({InternalAuthenticationServiceException.class})
    public ResultTemplate handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e) {
        log.error(">>>>> 认证中心：接口出现异常(账户异常)。url=【" + ServletUtils.getRequest().getRequestURI() + "】,异常消息=" + ExceptionUtils.getStackTrace(e));
        return new ResultTemplate(CodeMsg.SERVER_ERROR, e.getMessage());
    }

    /**
     * token 无效或已过期
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({InvalidTokenException.class})
    public ResultTemplate handleInvalidTokenExceptionException(InvalidTokenException e) {
        log.error(">>>>> 认证中心：接口出现异常(token 无效或已过期)。url=【" + ServletUtils.getRequest().getRequestURI() + "】,异常消息=" + ExceptionUtils.getStackTrace(e));
        return ResultTemplate.tokenInvalidOrExpired(e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({ChitchatException.class})
    public ResultTemplate handleException(ChitchatException e) {
        log.error(">>>>> 认证中心：接口出现异常(远程业务服务)。url=【" + ServletUtils.getRequest().getRequestURI() + "】,异常消息=" + ExceptionUtils.getStackTrace(e));
        return new ResultTemplate(e.code, e.msg, e.getMessage());
    }

}
