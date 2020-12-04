package com.example.springcloudstarteroauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
//这里的Order一定要大于ResourceServerConfig；
//因为ResourceServerConfig默认先于WebSecurityConfig加载；
@Order(1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        //密码不加密；
        return NoOpPasswordEncoder.getInstance();
//        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //打开表单登录；
                //web应用或第三方授权时，允许使用用户名密码登录
                .formLogin().and()
                //oauth相关接口不做权限校验
                .requestMatchers()
                .antMatchers("/login", "/oauth/authorize", "/oauth/token")
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();
    }

    @Override
    @Bean
    //grant type为password时，需要手动注入；否则会报错：Unsupported grant type: password
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
