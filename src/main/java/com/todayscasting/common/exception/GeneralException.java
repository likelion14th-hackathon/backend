package com.todayscasting.common.exception;

import com.todayscasting.common.code.BaseErrorCode;
import lombok.Getter;

import java.util.Objects;

/**
 * 서비스 로직에서 의도적으로 발생시키는 공통 비즈니스 예외입니다.
 */
@Getter
public class GeneralException extends RuntimeException {

    private final BaseErrorCode errorCode;

    public GeneralException(BaseErrorCode errorCode) {
        super(Objects.requireNonNull(errorCode, "errorCode must not be null").getReason().message());
        this.errorCode = errorCode;
    }

    public GeneralException(BaseErrorCode errorCode, Throwable cause) {
        super(Objects.requireNonNull(errorCode, "errorCode must not be null").getReason().message(), cause);
        this.errorCode = errorCode;
    }
}
