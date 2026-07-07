package com.todayscasting.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //紐⑤뱺 API 寃쎈줈??CORS ?ㅼ젙 ?곸슜
        registry.addMapping("/**")
                //(珥덇린媛쒕컻?? ?대뼡 異쒖쿂???붿껌 ?덉슜
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                //紐⑤뱺 ?붿껌 ?ㅻ뜑 ?덉슜
                .allowedHeaders("*")
                //JWT瑜?Authorization ?ㅻ뜑濡?蹂대궪 ?덉젙 珥덇린 false(荑좏궎 湲곕컲 ?몄쬆 ?덉?)
                .allowCredentials(false);
    }
}