package com.example.springcore.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
            // 이미지 폴더, CSS 폴더 관련된 리소스는 login 없이 허용합니다.
                .antMatchers("/images/**").permitAll()
                .antMatchers("/css/**").permitAll()
            // 어떤 요청이든 '인증'
                .anyRequest().authenticated()
            .and()
            // 로그인 기능 허용
                .formLogin()
                .loginPage("/user/login")
                .defaultSuccessUrl("/")
                .failureUrl("/user/login?error")
                .permitAll()
            .and()
            // 로그아웃 기능 허용
                .logout()
                .permitAll();
    }
}
