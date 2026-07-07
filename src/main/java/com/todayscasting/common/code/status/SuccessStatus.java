package com.todayscasting.common.code.status;

import com.todayscasting.common.code.BaseCode;
import com.todayscasting.common.code.ReasonDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * ?щ윭 ?꾨찓?몄뿉??怨듯넻?쇰줈 ?ъ슜?섎뒗 ?깃났 ?곹깭 肄붾뱶?낅땲??
 */
@Getter
@RequiredArgsConstructor
public enum SuccessStatus implements BaseCode {

    OK(HttpStatus.OK, "COMMON_200", "?붿껌???깃났?덉뒿?덈떎."),
    CREATED(HttpStatus.CREATED, "COMMON_201", "由ъ냼?ㅺ? ?앹꽦?섏뿀?듬땲??");

    private final HttpStatus status;
    private final String code;
    private final String message;

    // ApiResponse媛 ?ъ슜???깃났 ?곹깭 ?뺣낫瑜?諛섑솚?⑸땲??
    @Override
    public ReasonDTO getReason() {
        return new ReasonDTO(status, code, message);
    }
}
