package com.todayscasting.common.response;

/**
 * 요청값 검증에 실패한 필드명과 원인을 응답에 전달하는 값 객체입니다.
 */
public record ValidationError(
        String field,
        String message
) {
}
