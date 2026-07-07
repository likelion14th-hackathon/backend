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
 * 애플리케이션에서 발생한 예외를 공통 API 응답 형식으로 변환합니다.
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(GeneralException exception) {
        var reason = exception.getErrorCode().getReason();

        return ResponseEntity
                .status(reason.status())
                .body(ApiResponse.onFailure(exception.getErrorCode()));
    }

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

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissingRequestParameter(
            MissingServletRequestParameterException exception
    ) {
        return ResponseEntity
                .status(ErrorStatus.MISSING_PARAMETER.getStatus())
                .body(ApiResponse.onFailure(ErrorStatus.MISSING_PARAMETER));
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<ApiResponse<Void>> handleInvalidRequest(Exception exception) {
        return ResponseEntity
                .status(ErrorStatus.INVALID_REQUEST.getStatus())
                .body(ApiResponse.onFailure(ErrorStatus.INVALID_REQUEST));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoResourceFound(NoResourceFoundException exception) {
        return ResponseEntity
                .status(ErrorStatus.RESOURCE_NOT_FOUND.getStatus())
                .body(ApiResponse.onFailure(ErrorStatus.RESOURCE_NOT_FOUND));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodNotAllowed(
            HttpRequestMethodNotSupportedException exception
    ) {
        return ResponseEntity
                .status(ErrorStatus.METHOD_NOT_ALLOWED.getStatus())
                .body(ApiResponse.onFailure(ErrorStatus.METHOD_NOT_ALLOWED));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception exception) {
        log.error("Unhandled exception", exception);

        return ResponseEntity
                .status(ErrorStatus.INTERNAL_SERVER_ERROR.getStatus())
                .body(ApiResponse.onFailure(ErrorStatus.INTERNAL_SERVER_ERROR));
    }
}
