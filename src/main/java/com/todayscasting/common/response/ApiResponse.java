package com.todayscasting.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.todayscasting.common.code.BaseCode;
import com.todayscasting.common.code.BaseErrorCode;
import com.todayscasting.common.code.status.SuccessStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 紐⑤뱺 API媛 怨듯넻?쇰줈 ?ъ슜?섎뒗 ?묐떟 ?뺤떇?낅땲??
 *
 * @param <T> ?묐떟 寃곌낵 ?곗씠?곗쓽 ??? */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

    // ?붿껌???깃났 ?щ?
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    // ?깆뿉??遺꾧린 泥섎━?????ъ슜?섎뒗 ?묐떟 肄붾뱶
    private final String code;

    // ?ъ슜???먮뒗 媛쒕컻?먭? ?뺤씤???묐떟 硫붿떆吏
    private final String message;

    // ?ㅼ젣 ?묐떟 ?곗씠?곗씠硫? 媛믪씠 ?놁쑝硫?JSON?먯꽌 ?쒖쇅?⑸땲??
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T result;

    /**
     * 湲곕낯 ?깃났 ?묐떟???앹꽦?⑸땲??
     */
    public static <T> ApiResponse<T> onSuccess(T result) {
        return of(SuccessStatus.OK, result);
    }

    /**
     * 諛섑솚???곗씠?곌? ?녿뒗 湲곕낯 ?깃났 ?묐떟???앹꽦?⑸땲??
     */
    public static ApiResponse<Void> onSuccess() {
        return of(SuccessStatus.OK, null);
    }

    /**
     * CREATED ???먰븯???깃났 ?곹깭瑜?吏?뺥빐 ?묐떟???앹꽦?⑸땲??
     */
    public static <T> ApiResponse<T> of(BaseCode baseCode, T result) {
        var reason = baseCode.getReason();
        return new ApiResponse<>(true, reason.code(), reason.message(), result);
    }

    /**
     * ?뺤쓽???먮윭 肄붾뱶瑜??댁슜???ㅽ뙣 ?묐떟???앹꽦?⑸땲??
     */
    public static ApiResponse<Void> onFailure(BaseErrorCode errorCode) {
        return onFailure(errorCode, null);
    }

    /**
     * Validation ?곸꽭 ?뺣낫泥섎읆 異붽? ?곗씠?곌? ?덈뒗 ?ㅽ뙣 ?묐떟???앹꽦?⑸땲??
     */
    public static <T> ApiResponse<T> onFailure(BaseErrorCode errorCode, T result) {
        var reason = errorCode.getReason();
        return new ApiResponse<>(false, reason.code(), reason.message(), result);
    }

    /**
     * ?몃? API ?ㅻ쪟泥섎읆 誘몃━ ?뺤쓽?섏? ?딆? 肄붾뱶瑜??꾨떖?????ъ슜?⑸땲??
     */
    public static <T> ApiResponse<T> onFailure(String code, String message, T result) {
        return new ApiResponse<>(false, code, message, result);
    }
}
