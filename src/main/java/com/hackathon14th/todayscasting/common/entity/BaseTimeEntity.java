package com.hackathon14th.todayscasting.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 엔티티의 생성 시간과 마지막 수정 시간을 자동으로 관리하는 공통 부모 클래스입니다.
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    // 엔티티가 처음 저장된 시간이며 이후에는 변경되지 않습니다.
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 엔티티가 마지막으로 수정된 시간입니다.
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
