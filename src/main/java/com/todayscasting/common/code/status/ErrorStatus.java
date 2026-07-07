package com.todayscasting.common.code.status;

import com.todayscasting.common.code.BaseErrorCode;
import com.todayscasting.common.code.ErrorReasonDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * ?뱀젙 ?꾨찓?몄뿉 ?랁븯吏 ?딅뒗 怨듯넻 ?먮윭 ?곹깭 肄붾뱶?낅땲??
 */
@Getter
@RequiredArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    INVALID_INPUT(HttpStatus.BAD_REQUEST, "COMMON_400_1", "?낅젰媛믪씠 ?щ컮瑜댁? ?딆뒿?덈떎."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "COMMON_400_2", "?붿껌 ?뺤떇???щ컮瑜댁? ?딆뒿?덈떎."),
    MISSING_PARAMETER(HttpStatus.BAD_REQUEST, "COMMON_400_3", "?꾩닔 ?붿껌媛믪씠 ?꾨씫?섏뿀?듬땲??"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON_404", "?붿껌??由ъ냼?ㅻ? 李얠쓣 ???놁뒿?덈떎."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMMON_405", "吏?먰븯吏 ?딅뒗 HTTP 硫붿꽌?쒖엯?덈떎."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_500", "?쒕쾭 ?대? ?ㅻ쪟媛 諛쒖깮?덉뒿?덈떎.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    // ?덉쇅 泥섎━湲곌? ?ъ슜???먮윭 ?곹깭 ?뺣낫瑜?諛섑솚?⑸땲??
    @Override
    public ErrorReasonDTO getReason() {
        return new ErrorReasonDTO(status, code, message);
    }
}
