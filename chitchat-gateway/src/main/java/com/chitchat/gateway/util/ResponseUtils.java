package com.chitchat.gateway.util;

import cn.hutool.json.JSONUtil;
import com.chitchat.common.base.CodeMsg;
import com.chitchat.common.base.ResultTemplate;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 异常响应工具类
 *
 * Created by Js on 2022/7/31.
 */
public class ResponseUtils {

    public static Mono<Void> writeErrorInfo(ServerHttpResponse response, CodeMsg resultCode) {
        if (CodeMsg.ACCESS_UNAUTHORIZED.equals(resultCode) || CodeMsg.TOKEN_INVALID_OR_EXPIRED.equals(resultCode)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
        } else if (CodeMsg.TOKEN_ACCESS_FORBIDDEN.equals(resultCode)) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
        } else {
            response.setStatusCode(HttpStatus.BAD_REQUEST);
        }
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.getHeaders().set(HttpHeaders.CACHE_CONTROL, "no-cache");
        String body = JSONUtil.toJsonStr(new ResultTemplate(resultCode));
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer))
                .doOnError(error -> DataBufferUtils.release(buffer));
    }

}
