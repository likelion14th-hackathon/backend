package com.todayscasting.common.code;

import org.springframework.http.HttpStatus;

/**
 * ?깃났 ?묐떟 ?앹꽦???꾩슂??HTTP ?곹깭, 肄붾뱶, 硫붿떆吏瑜?臾띠? 媛?媛앹껜?낅땲??
 */
public record ReasonDTO(
        HttpStatus status,
        String code,
        String message
) {
}
