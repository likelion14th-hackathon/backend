package com.todayscasting.common.code.status;

import com.todayscasting.common.code.BaseErrorCode;
import com.todayscasting.common.code.ErrorReasonDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 특정 도메인에 속하지 않는 공통 에러 상태 코드입니다.
 */
@Getter
@RequiredArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    INVALID_INPUT(HttpStatus.BAD_REQUEST, "COMMON_400_1", "입력값이 올바르지 않습니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "COMMON_400_2", "요청 형식이 올바르지 않습니다."),
    MISSING_PARAMETER(HttpStatus.BAD_REQUEST, "COMMON_400_3", "필수 요청값이 누락되었습니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON_404", "요청한 리소스를 찾을 수 없습니다."),
    // 이미 존재한다는걸 사용자에게 명확히 알려주는 에러 코드
    // "이미 존재해서 새로 못 만든다"를 알려주는 에러 코드
    // 사용자가 오늘 이미 일기를 작성했는데 또 작성(POST)할려고 할때 필요함
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "COMMON_409", "이미 존재하는 리소스입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMMON_405", "지원하지 않는 HTTP 메서드입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_500", "서버 내부 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return new ErrorReasonDTO(status, code, message);
    }
}
