package com.todayscasting.common.exception;

import com.todayscasting.common.code.status.ErrorStatus;
import com.todayscasting.common.response.ApiResponse;
import com.todayscasting.common.response.ValidationError;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

/**
 * ?좏뵆由ъ??댁뀡?먯꽌 諛쒖깮???덉쇅瑜??쇨???ApiResponse ?뺤떇?쇰줈 蹂?섑빀?덈떎.
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    // ?쒕퉬??濡쒖쭅?먯꽌 諛쒖깮?쒗궓 鍮꾩쫰?덉뒪 ?덉쇅 泥섎━
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(GeneralException exception) {
        var reason = exception.getErrorCode().getReason();

        return ResponseEntity
                .status(reason.status())
                .body(ApiResponse.onFailure(exception.getErrorCode()));
    }

    // @Valid媛 ?곸슜???붿껌 DTO???꾨뱶 寃利??ㅽ뙣 泥섎━
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<List<ValidationError>>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception
    ) {
        List<ValidationError> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> new ValidationError(error.getField(), error.getDefaultMessage()))
                .toList();

        return ResponseEntity
                .status(ErrorStatus.INVALID_INPUT.getStatus())
                .body(ApiResponse.onFailure(ErrorStatus.INVALID_INPUT, errors));
    }

    // PathVariable, RequestParam ?깆뿉 ?곸슜???쒖빟 議곌굔 寃利??ㅽ뙣 泥섎━
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<List<ValidationError>>> handleConstraintViolation(
            ConstraintViolationException exception
    ) {
        List<ValidationError> errors = exception.getConstraintViolations().stream()
                .map(error -> new ValidationError(
                        error.getPropertyPath().toString(),
                        error.getMessage()
                ))
                .toList();

        return ResponseEntity
                .status(ErrorStatus.INVALID_INPUT.getStatus())
                .body(ApiResponse.onFailure(ErrorStatus.INVALID_INPUT, errors));
    }

    // ?꾩닔 ?붿껌 ?뚮씪誘명꽣媛 ?꾨씫??寃쎌슦 泥섎━
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissingRequestParameter(
            MissingServletRequestParameterException exception
    ) {
        return ResponseEntity
                .status(ErrorStatus.MISSING_PARAMETER.getStatus())
                .body(ApiResponse.onFailure(ErrorStatus.MISSING_PARAMETER));
    }

    // JSON 臾몃쾿 ?ㅻ쪟???뚮씪誘명꽣 ???遺덉씪移?泥섎━
    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<ApiResponse<Void>> handleInvalidRequest(Exception exception) {
        return ResponseEntity
                .status(ErrorStatus.INVALID_REQUEST.getStatus())
                .body(ApiResponse.onFailure(ErrorStatus.INVALID_REQUEST));
    }

    // 議댁옱?섏? ?딅뒗 API ?먮뒗 ?뺤쟻 由ъ냼???붿껌 泥섎━
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoResourceFound(NoResourceFoundException exception) {
        return ResponseEntity
                .status(ErrorStatus.RESOURCE_NOT_FOUND.getStatus())
                .body(ApiResponse.onFailure(ErrorStatus.RESOURCE_NOT_FOUND));
    }

    // 吏?먰븯吏 ?딅뒗 HTTP 硫붿꽌???붿껌 泥섎━
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodNotAllowed(
            HttpRequestMethodNotSupportedException exception
    ) {
        return ResponseEntity
                .status(ErrorStatus.METHOD_NOT_ALLOWED.getStatus())
                .body(ApiResponse.onFailure(ErrorStatus.METHOD_NOT_ALLOWED));
    }

    // ?꾩뿉??泥섎━?섏? 紐삵븳 ?덉긽 諛뽰쓽 ?쒕쾭 ?ㅻ쪟瑜?湲곕줉?섍퀬 怨듯넻 硫붿떆吏濡??묐떟
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception exception) {
        log.error("Unhandled exception", exception);

        return ResponseEntity
                .status(ErrorStatus.INTERNAL_SERVER_ERROR.getStatus())
                .body(ApiResponse.onFailure(ErrorStatus.INTERNAL_SERVER_ERROR));
    }
}
