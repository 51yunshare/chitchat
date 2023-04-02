package com.chitchat.gateway.security;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import com.chitchat.common.base.CodeMsg;
import com.chitchat.common.constant.AuthConstant;
import com.chitchat.gateway.config.WhiteUrlsConfig;
import com.chitchat.gateway.filter.IgnoreUrlsRemoveJwtFilter;
import com.chitchat.gateway.util.ResponseUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * 资源服务器配置
 *
 * 1.配置访问白名单列表 ignoreUrls，白名单请求无需认证和鉴权；resourceServerManager
 *
 * 2.配置本地方式获取公钥或者远程获取公钥，公钥验证JWT的签名；
 *
 * 3.配置未授权、token无效或者已过期的自定义异常。
 *
 * 关于token校验说明：是oath2自己去完成的
 *
 * 路径==>>>
 * 1. org.springframework.security.web.server.authentication.AuthenticationWebFilter#filter
 * 2. org.springframework.security.oauth2.server.resource.authentication.JwtReactiveAuthenticationManager#authenticate
 * 3. org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder#decode(java.lang.String)【NimbusJwtDecoder#parse 解密 ==> NimbusJwtDecoder#decode 解析】
 * 4. org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder#validateJwt
 * 5. org.springframework.security.oauth2.jwt.JwtTimestampValidator#validate 【用于校验Authorization中的token是否过期】
 *
 */
@AllArgsConstructor
@Configuration
// 注解需要使用@EnableWebFluxSecurity而非@EnableWebSecurity,因为SpringCloud Gateway基于WebFlux
@EnableWebFluxSecurity
@Slf4j
public class ResourceServerConfig {
    /**
     * ignoreUrls白名单、鉴权中心
     */
    private final ResourceServerManager resourceServerManager;
    private final WhiteUrlsConfig whiteListConfig;
    private final IgnoreUrlsRemoveJwtFilter ignoreUrlsRemoveJwtFilter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        log.info(">>>>> 网关->资源服务器配置中心 <<<<<");

        if (whiteListConfig.getUrls() == null) {
            log.error("网关白名单路径读取失败：Nacos配置读取失败，请检查配置中心连接是否正确！");
        }
        http.oauth2ResourceServer().jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                .publicKey(rsaPublicKey()); // 本地获取公钥
                //.jwkSetUri() // 远程获取公钥

        // 自定义处理JWT请求头过期或签名错误的结果
        http.oauth2ResourceServer().authenticationEntryPoint(authenticationEntryPoint());
        //对白名单路径，直接移除JWT请求头
//        http.addFilterBefore(ignoreUrlsRemoveJwtFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        http.authorizeExchange()
                .pathMatchers(Convert.toStrArray(whiteListConfig.getUrls())).permitAll()
                .anyExchange().access(resourceServerManager)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler()) // 处理未授权
                .authenticationEntryPoint(authenticationEntryPoint()) //处理未认证
                .and().csrf().disable();

        return http.build();
    }

    /**
     * @linkhttps://blog.csdn.net/qq_24230139/article/details/105091273
     * ServerHttpSecurity没有将jwt中authorities的负载部分当做Authentication
     * 需要把jwt的Claim中的authorities加入
     * 方案：重新定义ReactiveAuthenticationManager权限管理器，默认转换器JwtGrantedAuthoritiesConverter
     */
    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AuthConstant.AUTHORITY_PREFIX);
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AuthConstant.AUTHORITY_CLAIM_NAME);

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

    /**
     * 自定义未授权响应
     */
    @Bean
    ServerAccessDeniedHandler accessDeniedHandler() {
        log.info(">>>>> 网关->资源服务器配置中心->自定义未授权响应 <<<<<");
        return (exchange, denied) -> {
            Mono<Void> mono = Mono.defer(() -> Mono.just(exchange.getResponse()))
                    .flatMap(response -> ResponseUtils.writeErrorInfo(response, CodeMsg.ACCESS_UNAUTHORIZED));
            return mono;
        };
    }

    /**
     * token无效或者已过期自定义响应
     */
    @Bean
    ServerAuthenticationEntryPoint authenticationEntryPoint() {
        log.info(">>>>> 网关->资源服务器配置中心->自定义token无效响应 <<<<<");
        return (exchange, e) -> {
            Mono<Void> mono = Mono.defer(() -> Mono.just(exchange.getResponse()))
                    .flatMap(response -> ResponseUtils.writeErrorInfo(response, CodeMsg.TOKEN_INVALID_OR_EXPIRED));
            return mono;
        };
    }

    /**
     * 本地获取JWT验签公钥
     *
     * 可以使用jwkSetUri远程获取，为了减少一次网络开销，直接使用本地获取
     *
     */
    @SneakyThrows
    @Bean
    public RSAPublicKey rsaPublicKey() {
        log.info(">>>>> 网关->资源服务器配置中心->验签公钥 <<<<<");
        Resource resource = new ClassPathResource("public.key");
        InputStream is = resource.getInputStream();
        String publicKeyData = IoUtil.read(is).toString();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec((Base64.decode(publicKeyData)));

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        return rsaPublicKey;
    }
}
