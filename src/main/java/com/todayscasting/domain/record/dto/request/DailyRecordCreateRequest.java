package com.todayscasting.domain.record.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record DailyRecordCreateRequest(
        @NotNull LocalDate recordDate,
        @NotBlank String content,
        List<String> mood,
        List<String> moodTags,
        List<String> activityTags
) {}