package com.hackathon14th.todayscasting.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //모든 API 경로에 CORS 설정 적용
        registry.addMapping("/**")
                //(초기개발용) 어떤 출처든 요청 허용
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                //모든 요청 헤더 허용
                .allowedHeaders("*")
                //JWT를 Authorization 헤더로 보낼 예정 초기 false(쿠키 기반 인증 안씀)
                .allowCredentials(false);
    }
}