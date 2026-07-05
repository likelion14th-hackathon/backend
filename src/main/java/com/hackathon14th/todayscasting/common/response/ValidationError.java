package com.hackathon14th.todayscasting.common.response;

/**
 * 요청값 검증에 실패한 필드명과 원인을 앱에 전달하는 응답입니다.
 */
public record ValidationError(
        String field,
        String message
) {
}
