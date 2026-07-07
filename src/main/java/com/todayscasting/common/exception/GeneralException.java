package com.todayscasting.common.exception;

import com.todayscasting.common.code.BaseErrorCode;
import lombok.Getter;

import java.util.Objects;

/**
 * ?쒕퉬??濡쒖쭅?먯꽌 ?섎룄?곸쑝濡?諛쒖깮?쒗궎??怨듯넻 鍮꾩쫰?덉뒪 ?덉쇅?낅땲??
 */
@Getter
public class GeneralException extends RuntimeException {

    // HTTP ?곹깭? ???먮윭 肄붾뱶瑜??④퍡 媛吏??먮윭 ?뺣낫
    private final BaseErrorCode errorCode;

    /**
     * ?뺤쓽???먮윭 肄붾뱶濡?鍮꾩쫰?덉뒪 ?덉쇅瑜??앹꽦?⑸땲??
     */
    public GeneralException(BaseErrorCode errorCode) {
        super(Objects.requireNonNull(errorCode, "errorCode must not be null").getReason().message());
        this.errorCode = errorCode;
    }

    /**
     * ?먯씤???섎뒗 ?덉쇅瑜?蹂댁〈?댁빞 ?????ъ슜?⑸땲??
     */
    public GeneralException(BaseErrorCode errorCode, Throwable cause) {
        super(Objects.requireNonNull(errorCode, "errorCode must not be null").getReason().message(), cause);
        this.errorCode = errorCode;
    }
}
