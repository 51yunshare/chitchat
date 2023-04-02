package com.chitchat.auth.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.chitchat.auth.bean.Oauth2TokenDto;
import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.constant.AuthConstant;
import com.chitchat.common.util.ServletUtils;
import com.chitchat.common.web.jwt.JwtUtils;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.security.KeyPair;
import java.security.Principal;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 自定义Oauth2获取令牌接口
 * Created by Js on 2022/7/31.
 *
 * OAuth2.0默认认证端口 oauth/token
 *
 */
@Api(tags = "认证中心")
@RestController
@RequestMapping("/oauth")
@AllArgsConstructor
@Slf4j
public class AuthController extends BaseController {

    @Autowired
    private TokenEndpoint tokenEndpoint;
    @Autowired
    private KeyPair keyPair;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取token
     * @RequestParam Map<String, String> parameters
     *
     * @param principal
     * @param grant_type
     * @param client_id
     * @param client_secret
     * @param refresh_token
     * @param username
     * @param password
     * @return
     * @throws HttpRequestMethodNotSupportedException
     */
    @ApiOperation("Oauth2获取token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grant_type", defaultValue = "password", value = "授权模式", required = true),
            @ApiImplicitParam(name = "client_id", defaultValue = "portal-app", value = "Oauth2客户端ID", required = true),
            @ApiImplicitParam(name = "client_secret", defaultValue = "123456", value = "Oauth2客户端秘钥", required = true),
            @ApiImplicitParam(name = "refresh_token", value = "刷新token"),
            @ApiImplicitParam(name = "username", defaultValue = "zhangsan", value = "用户名"),
            @ApiImplicitParam(name = "password", defaultValue = "123456", value = "用户密码")
    })
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public ResultTemplate postAccessToken(Principal principal,@ApiParam @RequestParam Map<String, String> parameters
                                          /*@ApiParam("授权模式") @RequestParam String grant_type,
                                          @ApiParam("Oauth2客户端ID") @RequestParam String client_id,
                                          @ApiParam("Oauth2客户端秘钥") @RequestParam String client_secret,
                                          @ApiParam("刷新token") @RequestParam(required = false) String refresh_token,
                                          @ApiParam("登录用户名") @RequestParam(required = false) String username,
                                          @ApiParam("登录密码") @RequestParam(required = false) String password*/) throws HttpRequestMethodNotSupportedException {
        /*Map<String, String> parameters = new HashMap<>();
        parameters.put("grant_type",grant_type);
        parameters.put("client_id",client_id);
        parameters.put("client_secret",client_secret);
        parameters.putIfAbsent("refresh_token",refresh_token);
        parameters.putIfAbsent("username",username);
        parameters.putIfAbsent("password",password);*/
        /**
         * 获取登录认证的客户端ID
         *
         */
        String clientId = ServletUtils.getOAuth2ClientId();
        log.info("OAuth认证授权 客户端ID:{}，请求参数：{}", clientId, JSONUtil.toJsonStr(parameters));

        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        Oauth2TokenDto oauth2TokenDto = Oauth2TokenDto.builder()
                .token(oAuth2AccessToken.getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .tokenHead(AuthConstant.JWT_TOKEN_PREFIX)
                .jti(String.valueOf(oAuth2AccessToken.getAdditionalInformation().get("jti")))
                .build();

        return this.success(oauth2TokenDto);
    }

    /**
     * JWT 退出登录
     *
     * @return
     */
    @ApiOperation(value = "注销", hidden = true)
    @DeleteMapping("/logout")
    public ResultTemplate logout() {
        JSONObject payload = JwtUtils.getJwtPayload();
        String jti = payload.getStr(AuthConstant.JWT_JTI); // JWT唯一标识
        Long expireTime = payload.getLong(AuthConstant.JWT_EXP); // JWT过期时间戳(单位：秒)
        if (expireTime != null) {
            long currentTime = System.currentTimeMillis() / 1000;// 当前时间（单位：秒）
            if (expireTime > currentTime) { // token未过期，添加至缓存作为黑名单限制访问，缓存时间为token过期剩余时间
                redisTemplate.opsForValue().set(AuthConstant.TOKEN_BLACKLIST_PREFIX + jti, null, (expireTime - currentTime), TimeUnit.SECONDS);
            }
        } else { // token 永不过期则永久加入黑名单
            redisTemplate.opsForValue().set(AuthConstant.TOKEN_BLACKLIST_PREFIX + jti, null);
        }
        return this.success();
    }

    @ApiOperation(value = "获取公钥", notes = "login")
    @GetMapping("/publicKey")
    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

}
