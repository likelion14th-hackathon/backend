package com.hackathon14th.todayscasting.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                //REST API 서버에서는 보통 CSRF를 끄고 모바일 앱에서 API 호출할 예정이라 일단 끔
                .csrf(csrf -> csrf.disable())
                //CORS 설정을 사용. 실제 세부 설정은 WebConfig에서 함
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        //일단 모든 API 요청 허용, 나중에 바꿈
                        .anyRequest().permitAll()
                )
                //Spring Security 기본 로그인 페이지 끔
                .formLogin(form -> form.disable())
                //브라우저 기본 인증 팝업 끔
                .httpBasic(httpBasic -> httpBasic.disable())
                .build();
    }
}