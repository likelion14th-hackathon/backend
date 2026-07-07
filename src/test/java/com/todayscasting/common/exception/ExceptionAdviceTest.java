package com.todayscasting.common.exception;

import com.todayscasting.common.code.status.ErrorStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionAdviceTest {

    private final ExceptionAdvice advice = new ExceptionAdvice();

    @Test
    void convertsGeneralExceptionToApiResponse() {
        GeneralException exception = new GeneralException(ErrorStatus.RESOURCE_NOT_FOUND);

        var response = advice.handleGeneralException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getIsSuccess()).isFalse();
        assertThat(response.getBody().getCode()).isEqualTo("COMMON_404");
    }
}
