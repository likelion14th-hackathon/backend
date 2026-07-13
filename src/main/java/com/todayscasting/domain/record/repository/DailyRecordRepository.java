package com.todayscasting.domain.record.repository;

import com.todayscasting.domain.record.entity.DailyRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyRecordRepository extends JpaRepository<DailyRecord, Long> {

    // 삭제가 안된것들에서만 찾는 메서드
    Optional<DailyRecord> findByUserIdAndRecordDateAndDeletedAtIsNull(Long userId, LocalDate recordDate);

    Optional<DailyRecord> findByIdAndDeletedAtIsNull(Long id);

    Optional<DailyRecord> findByIdAndUserIdAndDeletedAtIsNull(Long id, Long userId);

    // 삭제된거까지 포함해서 찾는 조회
    Optional<DailyRecord> findByUserIdAndRecordDate(Long userId, LocalDate recordDate);

}