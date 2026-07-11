package com.todayscasting.domain.record.repository;

import com.todayscasting.domain.record.entity.DailyRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyRecordRepository extends JpaRepository<DailyRecord, Long> {

    Optional<DailyRecord> findByUserIdAndRecordDateAndDeletedAtIsNull(Long userId, LocalDate recordDate);

    Optional<DailyRecord> findByIdAndDeletedAtIsNull(Long id);
}