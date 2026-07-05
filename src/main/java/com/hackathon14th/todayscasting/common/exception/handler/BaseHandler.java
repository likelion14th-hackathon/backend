package com.hackathon14th.todayscasting.common.exception.handler;

import com.hackathon14th.todayscasting.common.code.BaseErrorCode;
import com.hackathon14th.todayscasting.common.exception.GeneralException;

/**
 * UserHandler 등 도메인별 예외 클래스가 공통으로 상속할 기반 클래스입니다.
 */
public class BaseHandler extends GeneralException {

    public BaseHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }

    public BaseHandler(BaseErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
