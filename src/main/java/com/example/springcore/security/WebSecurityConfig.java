package com.example.springcore.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 회원 관리 처리 API (POST /user/**) 에 대해 CSRF 무시
        http.csrf().disable();
        // .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()); // 'XSRF-TOKEN' 쿠키에 CSRF 토큰 유지
        // .disable(); // 전체 csrf 끄기
        // .ignoringAntMatchers("/user/**") // 해당 요청을 무시합니다.
        // .ignoringAntMatchers("/api/products/**");


        http.authorizeRequests() // 권한검증 수행
                // endpoint의 권한 분기 : image 폴더, css 폴더, 회원 관리 처리 API 전부를 로그인 없이도도 허용
                // permitAll() - 인증 없이 접근가능, authentication() - 인증된 사용자는 권한없이 접근가능, hasAnyRole("ADMIN", "MANAGER") -명시된 권한을 가진 사용자만 접근 가능, hasRole() - 하나의 권한과 매핑
                .antMatchers("/images/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/css2/**").permitAll()
                .antMatchers("/user/**").permitAll()
                // 그 외 어떤 요청이든 '인증'을 요구합니다.
                .anyRequest().authenticated()
                .and()
                // 로그인 관련 기능을 정의 - formLogin 형태의 경우로 국한
                .formLogin()
                .loginPage("/user/login") // 인증되지 않은 사용자의 로그인 View 제공 (GET /user/login)
                .loginProcessingUrl("/user/login") // 지정된 로그인 처리 endpoint로 (POST /user/login) 요청이 왔을 때 요청된 정보 기반 로그인 처리 수행 : 프론트에서 반드시 username, password로 전달 받아야 합니다.
                .defaultSuccessUrl("/") // 로그인 처리 후 성공 시 URL
                .failureUrl("/user/login?error") // 로그인 처리 후 실패 시 URL
                .permitAll()
                .and()
                // [로그아웃 기능]
                .logout()
                .logoutUrl("/user/logout") // 로그아웃 처리 URL : 기본적으로 GET으로 처리됩니다.
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/forbidden.html");// 접근 불가 페이지 설정

        http.userDetailsService(userDetailsService);
    }
}
