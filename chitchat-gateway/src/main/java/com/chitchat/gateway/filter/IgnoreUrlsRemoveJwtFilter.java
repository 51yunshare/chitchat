package com.chitchat.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.chitchat.common.constant.AuthConstant;
import com.chitchat.gateway.config.WhiteUrlsConfig;
import com.nimbusds.jose.JWSObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.text.ParseException;
import java.util.List;

/**
 * 白名单路径访问时需要移除JWT请求头
 * 这块会影响Postman的Basic Auth认证
 *
 * Created by Js on 2022/7/30.
 */
@Component
@Slf4j
public class IgnoreUrlsRemoveJwtFilter implements WebFilter {
    @Autowired
    private WhiteUrlsConfig whiteUrlsConfig;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.info(">>>>> 网关->白名单过滤器，url={} <<<<<", exchange.getRequest().getURI());
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        PathMatcher pathMatcher = new AntPathMatcher();
        //白名单路径移除JWT请求头
        List<String> ignoreUrls = whiteUrlsConfig.getUrls();
        for (String ignoreUrl : ignoreUrls) {
            if (pathMatcher.match(ignoreUrl, uri.getPath())) {
                //退出登录判断(需要记录已经退出登录的token，并禁用)
                logoutFilter(exchange, ignoreUrl);
                request = exchange.getRequest().mutate().header(AuthConstant.JWT_TOKEN_HEADER, "").build();
                exchange = exchange.mutate().request(request).build();
                return chain.filter(exchange);
            }
        }
        return chain.filter(exchange);
    }

    /**
     * 退出登录判断(需要记录已经退出登录的token，并禁用)
     *
     * @param exchange
     * @param ignoreUrl
     */
    private void logoutFilter(ServerWebExchange exchange, String ignoreUrl) {
        if (ignoreUrl.contains("logout")) {
            try {
                String token = exchange.getRequest().getHeaders().getFirst(AuthConstant.JWT_TOKEN_HEADER);
                String realToken = token.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
                String userStr = StrUtil.toString(JWSObject.parse(realToken).getPayload());
                exchange.getRequest().mutate().header(AuthConstant.USER_TOKEN_HEADER, URLUtil.encode(userStr));
            } catch (ParseException e) {
                log.error("过滤器，JWT-token转换失败：{}", ExceptionUtils.getStackTrace(e));
            }
        }
    }
}
