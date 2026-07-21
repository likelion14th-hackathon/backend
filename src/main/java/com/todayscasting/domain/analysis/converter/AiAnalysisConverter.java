package com.todayscasting.domain.analysis.converter;

import com.todayscasting.domain.analysis.dto.response.AiAnalysisResponseDTO;
import com.todayscasting.domain.analysis.dto.response.AiAnalysisStatusResponseDTO;
import com.todayscasting.domain.analysis.entity.AiAnalysisLog;

public class AiAnalysisConverter {

    private AiAnalysisConverter() {
    }

    public static AiAnalysisResponseDTO toResponseDTO(AiAnalysisLog aiAnalysisLog) {
        return AiAnalysisResponseDTO.builder()
                .id(aiAnalysisLog.getId())
                .dailyRecordId(aiAnalysisLog.getDailyRecordId())
                .provider(aiAnalysisLog.getProvider())
                .model(aiAnalysisLog.getModel())
                .status(aiAnalysisLog.getStatus())
                .rawResponse(aiAnalysisLog.getRawResponse())
                .errorMessage(aiAnalysisLog.getErrorMessage())
                .createdAt(aiAnalysisLog.getCreatedAt())
                .updatedAt(aiAnalysisLog.getUpdatedAt())
                .build();
    }

    public static AiAnalysisStatusResponseDTO toStatusResponseDTO(AiAnalysisLog aiAnalysisLog) {
        return AiAnalysisStatusResponseDTO.builder()
                .id(aiAnalysisLog.getId())
                .status(aiAnalysisLog.getStatus())
                .build();
    }

}