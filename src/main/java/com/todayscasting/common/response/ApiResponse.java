package com.todayscasting.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.todayscasting.common.code.BaseCode;
import com.todayscasting.common.code.BaseErrorCode;
import com.todayscasting.common.code.status.SuccessStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 모든 API가 공통으로 사용하는 응답 형식입니다.
 *
 * @param <T> 응답 결과 데이터의 타입
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    private final String code;

    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T result;

    public static <T> ApiResponse<T> onSuccess(T result) {
        return of(SuccessStatus.OK, result);
    }

    public static ApiResponse<Void> onSuccess() {
        return of(SuccessStatus.OK, null);
    }

    public static <T> ApiResponse<T> of(BaseCode baseCode, T result) {
        var reason = baseCode.getReason();
        return new ApiResponse<>(true, reason.code(), reason.message(), result);
    }

    public static ApiResponse<Void> onFailure(BaseErrorCode errorCode) {
        return onFailure(errorCode, null);
    }

    public static <T> ApiResponse<T> onFailure(BaseErrorCode errorCode, T result) {
        var reason = errorCode.getReason();
        return new ApiResponse<>(false, reason.code(), reason.message(), result);
    }

    public static <T> ApiResponse<T> onFailure(String code, String message, T result) {
        return new ApiResponse<>(false, code, message, result);
    }
}
