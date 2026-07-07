package com.todayscasting.common.exception.handler;

import com.todayscasting.common.code.BaseErrorCode;
import com.todayscasting.common.exception.GeneralException;

/**
 * 도메인별 예외 핸들러가 공통으로 상속할 기반 클래스입니다.
 */
public class BaseHandler extends GeneralException {

    public BaseHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }

    public BaseHandler(BaseErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
