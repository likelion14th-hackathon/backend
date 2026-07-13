package com.todayscasting.domain.record.service;

import com.todayscasting.common.exception.GeneralException;
import com.todayscasting.domain.record.converter.DailyRecordConverter;
import com.todayscasting.domain.record.dto.request.DailyRecordCreateRequest;
import com.todayscasting.domain.record.dto.request.DailyRecordUpdateRequest;
import com.todayscasting.domain.record.dto.response.DailyRecordResponse;
import com.todayscasting.domain.record.entity.DailyRecord;
import com.todayscasting.domain.record.repository.DailyRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DailyRecordServiceImplTest {

    @Mock
    private DailyRecordRepository dailyRecordRepository;

    @InjectMocks
    private DailyRecordServiceImpl dailyRecordService;

    @Test
    void createsDailyRecord() {
        DailyRecordCreateRequest request = new DailyRecordCreateRequest(
                LocalDate.of(2026, 7, 9), "오늘 발표 준비 완료", List.of("GOOD"),
                List.of("뿌듯함"), List.of("개발")
        );
        DailyRecord saved = DailyRecordConverter.toEntity(1L, request);
        when(dailyRecordRepository.save(any(DailyRecord.class))).thenReturn(saved);

        DailyRecordResponse response = dailyRecordService.create(1L, request);

        assertThat(response.content()).isEqualTo("오늘 발표 준비 완료");
        assertThat(response.mood()).isEqualTo(List.of("GOOD"));
    }

    @Test
    void throwsNotFoundWhenUpdatingMissingRecord() {
        when(dailyRecordRepository.findByIdAndUserIdAndDeletedAtIsNull(999L, 1L)).thenReturn(Optional.empty());

        DailyRecordUpdateRequest request = new DailyRecordUpdateRequest("내용", List.of("GOOD"), List.of(), List.of());

        assertThatThrownBy(() -> dailyRecordService.update(1L, 999L, request))
                .isInstanceOf(GeneralException.class);
    }

    @Test
    void throwsNotFoundWhenDateHasNoRecord() {
        when(dailyRecordRepository.findByUserIdAndRecordDateAndDeletedAtIsNull(1L, LocalDate.of(2026, 7, 9)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> dailyRecordService.getByDate(1L, LocalDate.of(2026, 7, 9)))
                .isInstanceOf(GeneralException.class);
    }

    @Test
    void deletesDailyRecordAsSoftDelete() {
        DailyRecord record = DailyRecord.create(1L, LocalDate.of(2026, 7, 9), "내용", List.of("GOOD"), List.of(), List.of());
        when(dailyRecordRepository.findByIdAndUserIdAndDeletedAtIsNull(1L, 1L)).thenReturn(Optional.of(record));

        dailyRecordService.delete(1L, 1L);

        assertThat(record.isDeleted()).isTrue();
    }
}