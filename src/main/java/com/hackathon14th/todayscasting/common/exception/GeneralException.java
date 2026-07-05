package com.hackathon14th.todayscasting.common.exception;

import com.hackathon14th.todayscasting.common.code.BaseErrorCode;
import lombok.Getter;

import java.util.Objects;

/**
 * 서비스 로직에서 의도적으로 발생시키는 공통 비즈니스 예외입니다.
 */
@Getter
public class GeneralException extends RuntimeException {

    // HTTP 상태와 앱 에러 코드를 함께 가진 에러 정보
    private final BaseErrorCode errorCode;

    /**
     * 정의된 에러 코드로 비즈니스 예외를 생성합니다.
     */
    public GeneralException(BaseErrorCode errorCode) {
        super(Objects.requireNonNull(errorCode, "errorCode must not be null").getReason().message());
        this.errorCode = errorCode;
    }

    /**
     * 원인이 되는 예외를 보존해야 할 때 사용합니다.
     */
    public GeneralException(BaseErrorCode errorCode, Throwable cause) {
        super(Objects.requireNonNull(errorCode, "errorCode must not be null").getReason().message(), cause);
        this.errorCode = errorCode;
    }
}
