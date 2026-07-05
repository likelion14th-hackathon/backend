package com.hackathon14th.todayscasting.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hackathon14th.todayscasting.common.code.BaseCode;
import com.hackathon14th.todayscasting.common.code.BaseErrorCode;
import com.hackathon14th.todayscasting.common.code.status.SuccessStatus;
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

    // 요청의 성공 여부
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    // 앱에서 분기 처리할 때 사용하는 응답 코드
    private final String code;

    // 사용자 또는 개발자가 확인할 응답 메시지
    private final String message;

    // 실제 응답 데이터이며, 값이 없으면 JSON에서 제외됩니다.
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T result;

    /**
     * 기본 성공 응답을 생성합니다.
     */
    public static <T> ApiResponse<T> onSuccess(T result) {
        return of(SuccessStatus.OK, result);
    }

    /**
     * 반환할 데이터가 없는 기본 성공 응답을 생성합니다.
     */
    public static ApiResponse<Void> onSuccess() {
        return of(SuccessStatus.OK, null);
    }

    /**
     * CREATED 등 원하는 성공 상태를 지정해 응답을 생성합니다.
     */
    public static <T> ApiResponse<T> of(BaseCode baseCode, T result) {
        var reason = baseCode.getReason();
        return new ApiResponse<>(true, reason.code(), reason.message(), result);
    }

    /**
     * 정의된 에러 코드를 이용해 실패 응답을 생성합니다.
     */
    public static ApiResponse<Void> onFailure(BaseErrorCode errorCode) {
        return onFailure(errorCode, null);
    }

    /**
     * Validation 상세 정보처럼 추가 데이터가 있는 실패 응답을 생성합니다.
     */
    public static <T> ApiResponse<T> onFailure(BaseErrorCode errorCode, T result) {
        var reason = errorCode.getReason();
        return new ApiResponse<>(false, reason.code(), reason.message(), result);
    }

    /**
     * 외부 API 오류처럼 미리 정의되지 않은 코드를 전달할 때 사용합니다.
     */
    public static <T> ApiResponse<T> onFailure(String code, String message, T result) {
        return new ApiResponse<>(false, code, message, result);
    }
}
