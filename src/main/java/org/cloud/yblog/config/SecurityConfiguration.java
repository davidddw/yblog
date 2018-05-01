/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 d05660@163.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.cloud.yblog.config;

import org.cloud.yblog.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.regex.Pattern;

import static org.cloud.yblog.constant.UrlConstants.*;

/**
 * Created by d05660ddw on 2017/7/14.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String RESOURCE_ID = "restservice";
    private static final String CLIENTID = "clientapp";
    private static final String SECRET = "123456";
    private static final String REMEMBERME = "rememberme";

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AccountAuthenticationProvider accountAuthenticationProvider;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(accountAuthenticationProvider);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 采用 spring oauth2.0认证模式
     */
    @Configuration
    @Order(4)
    public static class ApiWebSecurityConfig extends WebSecurityConfigurerAdapter {


        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
            jwtAccessTokenConverter.setSigningKey("ASDFASFsdfsdfsdfsfadsf234asdfasfdas");
            return jwtAccessTokenConverter;
        }

        @Bean
        public TokenStore tokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        @Autowired
        private AuthenticationManager authenticationManager;

        /**
         * AuthorizationEndpoint：用来作为请求者获得授权的服务，默认的URL是/oauth/authorize.
         */
        @Configuration
        @EnableAuthorizationServer
        protected static class OAuth2Configuration extends AuthorizationServerConfigurerAdapter {

            @Autowired
            private JwtAccessTokenConverter jwtAccessTokenConverter;

            @Autowired
            private TokenStore tokenStore;

            @Autowired
            @Qualifier("authenticationManagerBean")
            private AuthenticationManager authenticationManager;

            @Autowired
            UserDetailsService userDetailsService;

            @Override
            public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
                // @formatter:off
                endpoints
                        .tokenStore(tokenStore)
                        .authenticationManager(authenticationManager)
                        .accessTokenConverter(jwtAccessTokenConverter)
                        .userDetailsService(userDetailsService);
                // @formatter:on
            }

            @Override
            public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
                // @formatter:off
                clients
                        .inMemory()
                        .withClient(CLIENTID)
                        .authorizedGrantTypes("password", "refresh_token")
                        .authorities("ADMIN", "USER")
                        .scopes("read", "write")
                        .resourceIds(RESOURCE_ID)
                        .secret(SECRET)
                        .accessTokenValiditySeconds(1200)
                        .refreshTokenValiditySeconds(3600);
                // @formatter:on
            }
        }

        /**
         * 资源服务 Resource Service.
         */
        @Configuration
        @Order(3)
        //注解自动增加了一个类型为 OAuth2AuthenticationProcessingFilter 的过滤器链，
        @EnableResourceServer
        protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

            @Inject
            private Http401UnauthorizedEntryPoint authenticationEntryPoint;

            @Autowired
            private TokenStore tokenStore;

            @Override
            public void configure(ResourceServerSecurityConfigurer resources) {
                // @formatter:off
                resources.resourceId(RESOURCE_ID).tokenStore(tokenStore);
                // @formatter:on
            }

            @Override
            public void configure(HttpSecurity http) throws Exception {
                // @formatter:off
                ContentNegotiationStrategy contentNegotiationStrategy = http.getSharedObject(ContentNegotiationStrategy.class);
                if (contentNegotiationStrategy == null) {
                    contentNegotiationStrategy = new HeaderContentNegotiationStrategy();
                }
                MediaTypeRequestMatcher preferredMatcher = new MediaTypeRequestMatcher(contentNegotiationStrategy,
                        MediaType.APPLICATION_FORM_URLENCODED,
                        MediaType.APPLICATION_JSON,
                        MediaType.MULTIPART_FORM_DATA);


                http
                        .csrf().disable()
                        .antMatcher(OAUTHAPI + "/**")
                        .authorizeRequests().anyRequest().hasRole("ADMIN")
                        .and()
                        .anonymous().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and()
                        .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                        .defaultAuthenticationEntryPointFor(authenticationEntryPoint, preferredMatcher);
                // @formatter:on
            }
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http.csrf().disable().antMatcher(WEB + "/login").authorizeRequests().anyRequest().permitAll();
            // @formatter:on
        }
    }

    @Configuration
    @Order(5)
    public static class FormWebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Autowired
        DataSource dataSource;

        @Bean
        public PersistentTokenRepository persistentTokenRepository() {
            JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
            db.setDataSource(dataSource);
            return db;
        }

        @Bean
        public AuthenticationSuccessHandler authenticationSuccessHandler() {
            SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler = new SimpleUrlAuthenticationSuccessHandler();
            authenticationSuccessHandler.setDefaultTargetUrl(ADMIN);
            authenticationSuccessHandler.setTargetUrlParameter("redirect");
            return authenticationSuccessHandler;
        }

        @Bean
        public AuthenticationFailureHandler authenticationFailureHandler() {
            SimpleUrlAuthenticationFailureHandler authenticationFailureHandler = new SimpleUrlAuthenticationFailureHandler();
            authenticationFailureHandler.setDefaultFailureUrl(WEB + "/login?error=1");
            return authenticationFailureHandler;
        }

        @Bean
        public UsernamePasswordAuthenticationFilter customAuthenticationFilter() {
            /* All the configuration you do in http.formLogin().x().y().z() is applied to the standard
             * UsernamePasswordAuthenticationFilter not the custom filter you build. You will need to configure it
             * manually yourself. My auth filter initialization looks like this:
             */
            CustomAuthenticationFilter authFilter = new CustomAuthenticationFilter();
            authFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(WEB + "/login","POST"));
            authFilter.setAuthenticationManager(authenticationManager);
            authFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
            authFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
            authFilter.setUsernameParameter("username");
            authFilter.setPasswordParameter("password");
            return authFilter;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            //默认没有UsernamePasswordAuthenticationFilter，需要自定义一个filter，并将之放置在LogoutFilter之后。
            http.addFilterAfter(customAuthenticationFilter(), LogoutFilter.class);
            http
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/", WEB + "/**").permitAll()
                    .antMatchers(ADMIN + "/**").access("hasAnyRole('ADMIN','USER')")
                    .and()
                    .formLogin().loginPage(WEB + "/login").permitAll().usernameParameter("username").passwordParameter("password")
                    .and()
                    .rememberMe()
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(1209600)
                    .and()
                    .csrf().requireCsrfProtectionMatcher(new RestRequestMatcher())
                    .and()
                    .logout().logoutSuccessUrl(WEB + "/login?logout").permitAll()
                    .and()
                    .exceptionHandling().accessDeniedPage(WEB + "/403");
        }

        private class RestRequestMatcher implements RequestMatcher {
            private Pattern allowedMethods = Pattern.compile("^(GET|POST|PUT|PATCH|DELETE)$");
            private RegexRequestMatcher apiMatcher = new RegexRequestMatcher("/v[0-9]*/.*", null);

            @Override
            public boolean matches(HttpServletRequest request) {
                // No CSRF due to allowedMethod
                if (allowedMethods.matcher(request.getMethod()).matches())
                    return false;
                // No CSRF due to api call
                if (apiMatcher.matches(request))
                    return false;
                // CSRF for everything else that is not an API call or an allowedMethod
                return true;
            }
        }
    }
}