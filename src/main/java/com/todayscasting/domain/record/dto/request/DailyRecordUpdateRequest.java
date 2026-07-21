package com.todayscasting.domain.record.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record DailyRecordUpdateRequest(
        @NotBlank String content,
        List<String> mood,
        List<String> moodTags,
        List<String> activityTags
) {}