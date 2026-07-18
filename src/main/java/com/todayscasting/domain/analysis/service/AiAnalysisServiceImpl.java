package com.todayscasting.domain.analysis.service;

import com.todayscasting.common.code.status.ErrorStatus;
import com.todayscasting.common.exception.GeneralException;
import com.todayscasting.domain.analysis.converter.AiAnalysisConverter;
import com.todayscasting.domain.analysis.dto.request.AiAnalysisRequestDTO;
import com.todayscasting.domain.analysis.dto.response.AiAnalysisResponseDTO;
import com.todayscasting.domain.analysis.dto.response.AiAnalysisStatusResponseDTO;
import com.todayscasting.domain.analysis.entity.AiAnalysisLog;
import com.todayscasting.domain.analysis.repository.AiAnalysisLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AiAnalysisServiceImpl implements AiAnalysisService {

    private final AiAnalysisLogRepository aiAnalysisLogRepository;

    @Override
    @Transactional
    public AiAnalysisResponseDTO requestAnalysis(AiAnalysisRequestDTO request) {

        AiAnalysisLog aiAnalysisLog = AiAnalysisLog.builder()
                .dailyRecordId(request.getDailyRecordId())
                .provider("GEMINI")
                .model("gemini-2.0-flash")
                .prompt(buildPrompt(request.getDailyRecordId()))
                .build();

        AiAnalysisLog savedLog = aiAnalysisLogRepository.save(aiAnalysisLog);

        try {
            String rawResponse = callAiServer(savedLog.getPrompt());
            savedLog.markSuccess(rawResponse);
        } catch (Exception e) {
            savedLog.markFailed(e.getMessage());
        }

        return AiAnalysisConverter.toResponseDTO(savedLog);
    }

    @Override
    public AiAnalysisResponseDTO getAnalysisResult(Long dailyRecordId) {
        AiAnalysisLog aiAnalysisLog = findByDailyRecordIdOrThrow(dailyRecordId);
        return AiAnalysisConverter.toResponseDTO(aiAnalysisLog);
    }

    @Override
    public AiAnalysisStatusResponseDTO getAnalysisStatus(Long dailyRecordId) {
        AiAnalysisLog aiAnalysisLog = findByDailyRecordIdOrThrow(dailyRecordId);
        return AiAnalysisConverter.toStatusResponseDTO(aiAnalysisLog);
    }

    private AiAnalysisLog findByDailyRecordIdOrThrow(Long dailyRecordId) {
        return aiAnalysisLogRepository.findByDailyRecordId(dailyRecordId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.RESOURCE_NOT_FOUND));
    }

    private String buildPrompt(Long dailyRecordId) {
        // TODO: daily_records 테이블에서 content 조회해서 실제 프롬프트로 조립
        return "daily_record_id " + dailyRecordId + "에 대한 분석 요청";
    }

    private String callAiServer(String prompt) {
        // TODO: AI 서버(FastAPI) 완성되면 실제 HTTP 호출로 교체
        return "{\"role\": \"임시 배역\", \"genre\": \"임시 장르\", \"score\": 80, \"comment\": \"임시 코멘트입니다\"}";
    }

}