package com.todayscasting.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.todayscasting.common.code.status.ErrorStatus;
import com.todayscasting.common.code.status.SuccessStatus;
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
        assertThat(response.getMessage()).isEqualTo("?붿껌???깃났?덉뒿?덈떎.");
        assertThat(response.getResult()).isEqualTo("result");
    }

    @Test
    void createsResponseWithExplicitSuccessStatus() {
        ApiResponse<Long> response = ApiResponse.of(SuccessStatus.CREATED, 1L);

        assertThat(response.getIsSuccess()).isTrue();
        assertThat(response.getCode()).isEqualTo("COMMON_201");
        assertThat(response.getMessage()).isEqualTo("由ъ냼?ㅺ? ?앹꽦?섏뿀?듬땲??");
        assertThat(response.getResult()).isEqualTo(1L);
    }

    @Test
    void createsErrorResponseWithValidationDetails() {
        List<ValidationError> errors = List.of(
                new ValidationError("email", "?대찓???뺤떇???щ컮瑜댁? ?딆뒿?덈떎.")
        );

        ApiResponse<List<ValidationError>> response =
                ApiResponse.onFailure(ErrorStatus.INVALID_INPUT, errors);

        assertThat(response.getIsSuccess()).isFalse();
        assertThat(response.getCode()).isEqualTo("COMMON_400_1");
        assertThat(response.getMessage()).isEqualTo("?낅젰媛믪씠 ?щ컮瑜댁? ?딆뒿?덈떎.");
        assertThat(response.getResult()).containsExactlyElementsOf(errors);
    }
}
