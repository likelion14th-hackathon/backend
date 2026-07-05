package com.hackathon14th.todayscasting.common.code;

import org.springframework.http.HttpStatus;

/**
 * 성공 응답 생성에 필요한 HTTP 상태, 코드, 메시지를 묶은 값 객체입니다.
 */
public record ReasonDTO(
        HttpStatus status,
        String code,
        String message
) {
}
