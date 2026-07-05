package com.hackathon14th.todayscasting.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * BaseTimeEntity의 생성·수정 시간이 자동 기록되도록 JPA Auditing을 활성화합니다.
 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {
}
