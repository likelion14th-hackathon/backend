package com.todayscasting.domain.record.service;

import com.todayscasting.common.code.status.ErrorStatus;
import com.todayscasting.common.exception.GeneralException;
import com.todayscasting.domain.record.converter.DailyRecordConverter;
import com.todayscasting.domain.record.dto.request.DailyRecordCreateRequest;
import com.todayscasting.domain.record.dto.request.DailyRecordUpdateRequest;
import com.todayscasting.domain.record.dto.response.DailyRecordResponse;
import com.todayscasting.domain.record.entity.DailyRecord;
import com.todayscasting.domain.record.repository.DailyRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DailyRecordServiceImpl implements DailyRecordService {

    private final DailyRecordRepository dailyRecordRepository;

    @Override
    @Transactional
    public DailyRecordResponse create(Long userId, DailyRecordCreateRequest request) {
        DailyRecord dailyRecord = DailyRecordConverter.toEntity(userId, request);
        DailyRecord saved = dailyRecordRepository.save(dailyRecord);
        return DailyRecordConverter.toResponse(saved);
    }

    @Override
    @Transactional
    public DailyRecordResponse update(Long userId, Long recordId, DailyRecordUpdateRequest request) {
        DailyRecord dailyRecord = dailyRecordRepository.findByIdAndDeletedAtIsNull(recordId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.RESOURCE_NOT_FOUND));

        dailyRecord.update(request.content(), request.mood(), request.moodTags(), request.activityTags());
        return DailyRecordConverter.toResponse(dailyRecord);
    }

    @Override
    @Transactional
    public void delete(Long userId, Long recordId) {
        DailyRecord dailyRecord = dailyRecordRepository.findByIdAndDeletedAtIsNull(recordId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.RESOURCE_NOT_FOUND));

        dailyRecord.delete();
    }

    @Override
    public DailyRecordResponse getByDate(Long userId, LocalDate date) {
        DailyRecord dailyRecord = dailyRecordRepository.findByUserIdAndRecordDateAndDeletedAtIsNull(userId, date)
                .orElseThrow(() -> new GeneralException(ErrorStatus.RESOURCE_NOT_FOUND));

        return DailyRecordConverter.toResponse(dailyRecord);
    }
}