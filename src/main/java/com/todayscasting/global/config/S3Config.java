package com.todayscasting.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3Config {

    @Value("${aws.s3.region}")
    private String region;

    private DefaultCredentialsProvider defaultCredentialsProvider() {
        return DefaultCredentialsProvider.builder().build();
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                // EC2 배포 환경에서는 인스턴스에 연결된 IAM Role 권한을 사용합니다.
                .credentialsProvider(defaultCredentialsProvider())
                .region(Region.of(region))
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.builder()
                // private 버킷 객체를 임시 URL로 조회할 때 사용합니다.
                .credentialsProvider(defaultCredentialsProvider())
                .region(Region.of(region))
                .build();
    }
}
