package com.todayscasting.domain.analysis.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AiAnalysisRequestDTO {

    @NotNull(message = "dailyRecordId는 필수입니다.")
    private Long dailyRecordId;

}