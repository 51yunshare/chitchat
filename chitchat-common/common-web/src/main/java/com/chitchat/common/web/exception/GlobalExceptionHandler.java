package com.chitchat.common.web.exception;

import com.chitchat.common.base.CodeMsg;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.exception.ChitchatException;
import com.chitchat.common.util.ServletUtils;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by Js on 2022/7/25.
 * 全局异常拦截器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = Exception.class)
    public ResultTemplate exception(Exception e) {
        log.error("接口出现异常。url=【" + ServletUtils.getRequest().getRequestURI() + "】,异常消息=" + ExceptionUtils.getStackTrace(e));
        if (e instanceof DuplicateKeyException) {
            return new ResultTemplate(CodeMsg.SERVER_ERROR.getCode(), "数据库已存在，请勿重复操作！", null);
        }
        return new ResultTemplate(CodeMsg.SERVER_ERROR.getCode(), CodeMsg.SERVER_ERROR.getMsg(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultTemplate methodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        Map<String, Object> maps;
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            maps = new HashMap<>(fieldErrors.size());
            fieldErrors.forEach(error -> {
                maps.put(error.getField(), error.getDefaultMessage());
            });
        } else {
            maps = Collections.EMPTY_MAP;
        }
        return new ResultTemplate(CodeMsg.BIND_ERROR.getCode(), CodeMsg.BIND_ERROR.getMsg(), maps);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResultTemplate processException(BindException e) {
        log.error("BindException:{}", e.getMessage());
        String msg = e.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining("；"));
        return new ResultTemplate(CodeMsg.BIND_ERROR.getCode(), CodeMsg.BIND_ERROR.getMsg(), msg);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResultTemplate processException(HttpServletRequest request, NoHandlerFoundException e) {
        log.error("请求rest接口出现异常。url=【" + request.getRequestURI() + "】,异常消息=" + ExceptionUtils.getStackTrace(e));
        return new ResultTemplate(CodeMsg.BIND_ERROR.getCode(), CodeMsg.BIND_ERROR.getMsg(), e.getMessage());
    }

    /**
     * HttpMessageNotReadableException
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultTemplate processException(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        String errorMessage = "http请求参数转换异常";
        Throwable cause = e.getCause();
        if (cause != null) {
            errorMessage = convertMessage(cause);
        }
        return new ResultTemplate(CodeMsg.BIND_ERROR.getCode(), CodeMsg.BIND_ERROR.getMsg(), errorMessage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FeignException.BadRequest.class)
    public ResultTemplate processException(FeignException.BadRequest e) {
        log.info("微服务feign调用异常:{}", e.getMessage());
        return new ResultTemplate(CodeMsg.SERVER_ERROR.getCode(), CodeMsg.SERVER_ERROR.getMsg(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ChitchatException.class)
    public ResultTemplate handleBizException(ChitchatException e) {
        log.error("接口出现异常。url=【" + ServletUtils.getRequest().getRequestURI() + "】,异常消息=" + ExceptionUtils.getStackTrace(e));
        return new ResultTemplate(e.getCode(), e.getMsg(), e.getMessage());
    }
    /**
     * 传参类型错误时，用于消息转换
     *
     * @param throwable 异常
     * @return 错误信息
     */
    private String convertMessage(Throwable throwable) {
        String error = throwable.toString();
        String regulation = "\\[\"(.*?)\"]+";
        Pattern pattern = Pattern.compile(regulation);
        Matcher matcher = pattern.matcher(error);
        String group = "";
        if (matcher.find()) {
            String matchString = matcher.group();
            matchString = matchString.replace("[", "").replace("]", "");
            matchString = matchString.replaceAll("\\\"", "") + "字段类型错误";
            group += matchString;
        }
        return group;
    }
}
