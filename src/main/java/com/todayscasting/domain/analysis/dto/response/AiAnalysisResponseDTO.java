package com.todayscasting.domain.analysis.dto.response;

import com.todayscasting.domain.analysis.entity.AnalysisStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AiAnalysisResponseDTO {

    private Long id;
    private Long dailyRecordId;
    private String provider;
    private String model;
    private AnalysisStatus status;
    private String rawResponse;
    private String errorMessage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}