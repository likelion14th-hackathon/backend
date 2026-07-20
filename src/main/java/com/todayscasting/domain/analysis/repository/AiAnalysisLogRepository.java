package com.todayscasting.domain.analysis.repository;

import com.todayscasting.domain.analysis.entity.AiAnalysisLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AiAnalysisLogRepository extends JpaRepository<AiAnalysisLog, Long> {

    Optional<AiAnalysisLog> findByDailyRecordId(Long dailyRecordId);

}