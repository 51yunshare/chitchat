package com.chitchat.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.chitchat.common.base.CodeMsg;
import com.chitchat.common.constant.AuthConstant;
import com.chitchat.gateway.util.ResponseUtils;
import com.nimbusds.jose.JWSObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;

/**
 * 安全拦截全局过滤器
 * JWT校验
 * <p>
 * 将登录用户的JWT转化成用户信息的全局过滤器
 * <p>
 * 在ResourceServerManager#check鉴权善后一些无关紧要的事宜(线上请求拦截、黑名单拦截)
 * <p>
 * Created by Js on 2022/7/31.
 */
@Component
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        log.info(">>>>> 网关->全局过滤器，url={} <<<<<", request.getURI());
        String token = request.getHeaders().getFirst(AuthConstant.JWT_TOKEN_HEADER);
        if (StrUtil.isEmpty(token) || !StrUtil.startWithIgnoreCase(token, AuthConstant.JWT_TOKEN_PREFIX)) {
            return chain.filter(exchange);
        }
        try {
            // 解析JWT获取jti，以jti为key判断redis的黑名单列表是否存在，存在则拦截访问
            //1.将Bearer 移除
            String realToken = token.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
            String userStr = StrUtil.toString(JWSObject.parse(realToken).getPayload());
            JSONObject jsonObject = JSONUtil.parseObj(userStr);
            //判断jwt是否黑名单，用来退出使用
            String jti = jsonObject.getStr(AuthConstant.JWT_JTI);
            Boolean isBlack = redisTemplate.hasKey(AuthConstant.TOKEN_BLACKLIST_PREFIX + jti);
            if (isBlack) {
                return ResponseUtils.writeErrorInfo(response, CodeMsg.TOKEN_ACCESS_FORBIDDEN);
            }

            //从token中解析用户信息并设置到Header中去
            log.info("AuthGlobalFilter.filter() user:{}", userStr);
            request = exchange.getRequest().mutate()
                    .header(AuthConstant.USER_TOKEN_HEADER, URLUtil.encode(userStr))
                    .header(AuthConstant.USER_ID_HEADER, String.valueOf(jsonObject.getLong("id"))).build();
            exchange = exchange.mutate().request(request).build();
        } catch (ParseException e) {
            log.error("过滤器，JWT-token转换失败：{}", ExceptionUtils.getStackTrace(e));
        }
        return chain.filter(exchange);
    }

    /**
     * Order值越小越先执行
     *
     * 两个GlobalFilter类型的过滤器Order值相同时，根据文件名字母排序，文件名靠前的优先更高
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
