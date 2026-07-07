package com.todayscasting.global.config;

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
                //REST API ?쒕쾭?먯꽌??蹂댄넻 CSRF瑜??꾧퀬 紐⑤컮???깆뿉??API ?몄텧???덉젙?대씪 ?쇰떒 ??
                .csrf(csrf -> csrf.disable())
                //CORS ?ㅼ젙???ъ슜. ?ㅼ젣 ?몃? ?ㅼ젙? WebConfig?먯꽌 ??
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        //?쇰떒 紐⑤뱺 API ?붿껌 ?덉슜, ?섏쨷??諛붽퓞
                        .anyRequest().permitAll()
                )
                //Spring Security 湲곕낯 濡쒓렇???섏씠吏 ??
                .formLogin(form -> form.disable())
                //釉뚮씪?곗? 湲곕낯 ?몄쬆 ?앹뾽 ??
                .httpBasic(httpBasic -> httpBasic.disable())
                .build();
    }
}