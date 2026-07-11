package com.todayscasting.domain.record.service;

import com.todayscasting.domain.record.dto.request.DailyRecordCreateRequest;
import com.todayscasting.domain.record.dto.request.DailyRecordUpdateRequest;
import com.todayscasting.domain.record.dto.response.DailyRecordResponse;

import java.time.LocalDate;

public interface DailyRecordService {

    DailyRecordResponse create(Long userId, DailyRecordCreateRequest request);

    DailyRecordResponse update(Long userId, Long recordId, DailyRecordUpdateRequest request);

    void delete(Long userId, Long recordId);

    DailyRecordResponse getByDate(Long userId, LocalDate date);
}