package com.hackathon14th.todayscasting.common.exception;

import com.hackathon14th.todayscasting.common.code.status.ErrorStatus;
import com.hackathon14th.todayscasting.common.response.ApiResponse;
import com.hackathon14th.todayscasting.common.response.ValidationError;
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
 * 애플리케이션에서 발생한 예외를 일관된 ApiResponse 형식으로 변환합니다.
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    // 서비스 로직에서 발생시킨 비즈니스 예외 처리
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(GeneralException exception) {
        var reason = exception.getErrorCode().getReason();

        return ResponseEntity
                .status(reason.status())
                .body(ApiResponse.onFailure(exception.getErrorCode()));
    }

    // @Valid가 적용된 요청 DTO의 필드 검증 실패 처리
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

    // PathVariable, RequestParam 등에 적용된 제약 조건 검증 실패 처리
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

    // 필수 요청 파라미터가 누락된 경우 처리
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissingRequestParameter(
            MissingServletRequestParameterException exception
    ) {
        return ResponseEntity
                .status(ErrorStatus.MISSING_PARAMETER.getStatus())
                .body(ApiResponse.onFailure(ErrorStatus.MISSING_PARAMETER));
    }

    // JSON 문법 오류나 파라미터 타입 불일치 처리
    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<ApiResponse<Void>> handleInvalidRequest(Exception exception) {
        return ResponseEntity
                .status(ErrorStatus.INVALID_REQUEST.getStatus())
                .body(ApiResponse.onFailure(ErrorStatus.INVALID_REQUEST));
    }

    // 존재하지 않는 API 또는 정적 리소스 요청 처리
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoResourceFound(NoResourceFoundException exception) {
        return ResponseEntity
                .status(ErrorStatus.RESOURCE_NOT_FOUND.getStatus())
                .body(ApiResponse.onFailure(ErrorStatus.RESOURCE_NOT_FOUND));
    }

    // 지원하지 않는 HTTP 메서드 요청 처리
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodNotAllowed(
            HttpRequestMethodNotSupportedException exception
    ) {
        return ResponseEntity
                .status(ErrorStatus.METHOD_NOT_ALLOWED.getStatus())
                .body(ApiResponse.onFailure(ErrorStatus.METHOD_NOT_ALLOWED));
    }

    // 위에서 처리하지 못한 예상 밖의 서버 오류를 기록하고 공통 메시지로 응답
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception exception) {
        log.error("Unhandled exception", exception);

        return ResponseEntity
                .status(ErrorStatus.INTERNAL_SERVER_ERROR.getStatus())
                .body(ApiResponse.onFailure(ErrorStatus.INTERNAL_SERVER_ERROR));
    }
}
