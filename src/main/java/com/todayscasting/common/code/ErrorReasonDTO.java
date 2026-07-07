package com.todayscasting.common.code;

import org.springframework.http.HttpStatus;

/**
 * ?ㅽ뙣 ?묐떟怨?HTTP ?곹깭 寃곗젙???꾩슂???먮윭 ?뺣낫瑜?臾띠? 媛?媛앹껜?낅땲??
 */
public record ErrorReasonDTO(
        HttpStatus status,
        String code,
        String message
) {
}
