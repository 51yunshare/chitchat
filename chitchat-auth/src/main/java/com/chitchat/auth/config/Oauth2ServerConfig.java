package com.chitchat.auth.config;

import cn.hutool.crypto.digest.BCrypt;
import com.chitchat.auth.component.JwtTokenEnhancer;
import com.chitchat.auth.extension.mobile.MobilePasswordTokenGranter;
import com.chitchat.auth.extension.third.facebook.FacebookTokenGranter;
import com.chitchat.auth.extension.third.google.GoogleTokenGranter;
import com.chitchat.auth.extension.third.social.SocialTokenGranter;
import com.chitchat.auth.service.impl.ClientDetailsServiceImpl;
import com.chitchat.auth.service.impl.UserDetailsServiceImpl;
import com.chitchat.common.constant.AuthConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 认证服务器配置
 *
 * Created by Js on 2022/7/31.
 */
@RequiredArgsConstructor
@Configuration
@EnableAuthorizationServer
public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 加密器
     */
    private final PasswordEncoder passwordEncoder;
    /**
     * 认证用户实现类
     */
    private final UserDetailsServiceImpl userDetailsService;
    /**
     * 密码模式需要的
     */
    private final AuthenticationManager authenticationManager;
    /**
     * jwtToken增强器(主要是给token添加额外信息)
     */
    private final JwtTokenEnhancer jwtTokenEnhancer;
    /**
     * 客户端实现类
     */
    private final ClientDetailsServiceImpl clientDetailsService;

    /**
     * 配置客户端详情，一个ClientDetails代表一个客户端
     * 这里设置两个客户端(后台、app)，也可以扩展到数据库
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //数据库获取
//        clients.withClientDetails(clientDetailsService);
        //内存中设置客户端信息
        clients.inMemory()
                .withClient(AuthConstant.ADMIN_CLIENT_ID)
                .secret(passwordEncoder.encode(AuthConstant.ADMIN_CLIENT_SECRET))//客户端密钥
                .scopes("all")//权限范围
                .authorizedGrantTypes("password", "refresh_token")//授权类型(密码授权)
                .accessTokenValiditySeconds(3600*24)//过期时间
                .refreshTokenValiditySeconds(3600*24*7)
                .and()
                .withClient(AuthConstant.PORTAL_CLIENT_ID)
                .secret(passwordEncoder.encode(AuthConstant.PORTAL_CLIENT_SECRET))
                .scopes("all")
                .authorizedGrantTypes("password", "refresh_token", "facebook", "mobile_pwd", "google", "social")
                .accessTokenValiditySeconds(3600*24)
                .refreshTokenValiditySeconds(3600*24*7);
    }

    /**
     * 配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //Token增强
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        //Token增强器
        delegates.add(jwtTokenEnhancer);
        //jwt token加密转换
        delegates.add(accessTokenConverter());
        enhancerChain.setTokenEnhancers(delegates); //配置JWT的内容增强器

        //token存储模式设定 默认为InMemoryTokenStore模式存储到内存中
        endpoints.tokenStore(jwtTokenStore());

        //添加自定义授权模式
        // 获取原有默认授权模式(授权码模式、密码模式、客户端模式、简化模式)的授权者
        List<TokenGranter> granterList = new ArrayList<>(Arrays.asList(endpoints.getTokenGranter()));

        // 添加手机号密码授权模式的授权者
        granterList.add(new MobilePasswordTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory(), authenticationManager
        ));
        // 添加Facebook/Google
        granterList.add(new FacebookTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory(), authenticationManager
        ));
        granterList.add(new GoogleTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory(), authenticationManager
        ));
        granterList.add(new SocialTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory(), authenticationManager
        ));


        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService) //配置加载用户信息的服务
                .accessTokenConverter(accessTokenConverter())
                .tokenEnhancer(enhancerChain)

                .tokenGranter(new CompositeTokenGranter(granterList))

                /** refresh_token有两种使用方式：重复使用(true)、非重复使用(false)，默认为true
                 *  1 重复使用：access_token过期刷新时， refresh_token过期时间未改变，仍以初次生成的时间为准
                 *  2 非重复使用：access_token过期刷新时， refresh_token过期时间延续，在refresh_token有效期内刷新便永不失效达到无需再次登录的目的
                 */
                .reuseRefreshTokens(true);
    }

    /**
     * 允许表单提交
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
    }

    /**
     * jwt token存储模式
     */
    @Bean
    public JwtTokenStore jwtTokenStore(){
        return new JwtTokenStore(accessTokenConverter());
    }

    /**
     * 使用非对称加密算法对token签名
     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyPair());
        return jwtAccessTokenConverter;
    }

    /**
     * 密钥库中获取密钥对(公钥+私钥)
     * RSA非对称签名方式
     * (1). 从密钥库获取密钥对(密钥+私钥)
     * (2). 认证服务器私钥对token签名
     * (3). 提供公钥获取接口供资源服务器验签使用
     *
     *
     * 在JDK的bin目录下使用
     * keytool -genkey -alias jwt -keyalg RSA -keypass 123456 -keystore jwt.jks -storepass 123456
     *
     * -alias 别名
     * -keyalg 密钥算法
     * -keypass 密钥口令
     * -keystore 生成密钥库的存储路径和名称
     * -storepass 密钥库口令
     *
     * @return
     */
    @Bean
    public KeyPair keyPair() {
        //从classpath下的证书中获取秘钥对
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "123321".toCharArray());
        return keyStoreKeyFactory.getKeyPair("jwt", "123321".toCharArray());
    }

    public static void main(String[] args) {
        // 用户密码
        String password = "123456";
        // 创建密码加密的对象
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 密码加密
        String newPassword = passwordEncoder.encode(password);
        System.out.println("加密后的密码为：" + newPassword);

        // 校验这两个密码是否是同一个密码
        // matches方法第一个参数是原密码，第二个参数是加密后的密码
        boolean matches = passwordEncoder.matches(password, newPassword);
        System.out.println("两个密码一致:" + matches);

        System.out.println("加密后的密码为：" + BCrypt.hashpw(password));

    }
}
