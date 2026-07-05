package com.hackathon14th.todayscasting.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hackathon14th.todayscasting.common.code.status.ErrorStatus;
import com.hackathon14th.todayscasting.common.code.status.SuccessStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ApiResponseTest {

    @Test
    void keepsMobileApiJsonContract() throws NoSuchFieldException {
        JsonPropertyOrder propertyOrder = ApiResponse.class.getAnnotation(JsonPropertyOrder.class);
        JsonProperty successProperty = ApiResponse.class
                .getDeclaredField("isSuccess")
                .getAnnotation(JsonProperty.class);
        JsonInclude resultInclude = ApiResponse.class
                .getDeclaredField("result")
                .getAnnotation(JsonInclude.class);

        assertThat(propertyOrder.value())
                .containsExactly("isSuccess", "code", "message", "result");
        assertThat(successProperty.value()).isEqualTo("isSuccess");
        assertThat(resultInclude.value()).isEqualTo(JsonInclude.Include.NON_NULL);
    }

    @Test
    void createsSuccessResponse() {
        ApiResponse<String> response = ApiResponse.onSuccess("result");

        assertThat(response.getIsSuccess()).isTrue();
        assertThat(response.getCode()).isEqualTo("COMMON_200");
        assertThat(response.getMessage()).isEqualTo("요청에 성공했습니다.");
        assertThat(response.getResult()).isEqualTo("result");
    }

    @Test
    void createsResponseWithExplicitSuccessStatus() {
        ApiResponse<Long> response = ApiResponse.of(SuccessStatus.CREATED, 1L);

        assertThat(response.getIsSuccess()).isTrue();
        assertThat(response.getCode()).isEqualTo("COMMON_201");
        assertThat(response.getMessage()).isEqualTo("리소스가 생성되었습니다.");
        assertThat(response.getResult()).isEqualTo(1L);
    }

    @Test
    void createsErrorResponseWithValidationDetails() {
        List<ValidationError> errors = List.of(
                new ValidationError("email", "이메일 형식이 올바르지 않습니다.")
        );

        ApiResponse<List<ValidationError>> response =
                ApiResponse.onFailure(ErrorStatus.INVALID_INPUT, errors);

        assertThat(response.getIsSuccess()).isFalse();
        assertThat(response.getCode()).isEqualTo("COMMON_400_1");
        assertThat(response.getMessage()).isEqualTo("입력값이 올바르지 않습니다.");
        assertThat(response.getResult()).containsExactlyElementsOf(errors);
    }
}
