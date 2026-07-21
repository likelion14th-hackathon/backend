package com.todayscasting.domain.analysis.dto.response;

import com.todayscasting.domain.analysis.entity.AnalysisStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AiAnalysisStatusResponseDTO {

    private Long id;
    private AnalysisStatus status;

}