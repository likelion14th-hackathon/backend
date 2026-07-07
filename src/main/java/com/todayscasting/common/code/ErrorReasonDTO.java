package com.todayscasting.common.code;

import org.springframework.http.HttpStatus;

/**
 * 실패 응답과 HTTP 상태 결정에 필요한 에러 정보를 묶은 값 객체입니다.
 */
public record ErrorReasonDTO(
        HttpStatus status,
        String code,
        String message
) {
}
