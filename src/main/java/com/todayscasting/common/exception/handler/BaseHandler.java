package com.todayscasting.common.exception.handler;

import com.todayscasting.common.code.BaseErrorCode;
import com.todayscasting.common.exception.GeneralException;

/**
 * UserHandler ???꾨찓?몃퀎 ?덉쇅 ?대옒?ㅺ? 怨듯넻?쇰줈 ?곸냽??湲곕컲 ?대옒?ㅼ엯?덈떎.
 */
public class BaseHandler extends GeneralException {

    public BaseHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }

    public BaseHandler(BaseErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
