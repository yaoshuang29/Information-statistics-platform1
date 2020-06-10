package com.info.config;

import com.info.service.impl.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserService customUserService;


    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService).passwordEncoder(new MyPasswordEncode());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/static/images/**","/static/css/**","/static/fonts/**","/static/i/**","/static/js/**","/register","/user/**","/student/**","/classes/**")
                    .permitAll()//允许访问静态资源
                    .anyRequest().authenticated() //任何不允许的访问需要验证才能访问
                    .and()
                    .csrf().disable()
                    .formLogin()
                    .loginPage("/login").permitAll()
                    .loginProcessingUrl("/login/form").defaultSuccessUrl("/index",true)
                    .failureForwardUrl("/loginError")
                    .failureUrl("/loginError").permitAll() //失败跳转页面
                    .and()
                    .logout().permitAll();// 退出登录
            http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);

    }
}
