package com.hackathon14th.todayscasting.common.code.status;

import com.hackathon14th.todayscasting.common.code.BaseCode;
import com.hackathon14th.todayscasting.common.code.ReasonDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 여러 도메인에서 공통으로 사용하는 성공 상태 코드입니다.
 */
@Getter
@RequiredArgsConstructor
public enum SuccessStatus implements BaseCode {

    OK(HttpStatus.OK, "COMMON_200", "요청에 성공했습니다."),
    CREATED(HttpStatus.CREATED, "COMMON_201", "리소스가 생성되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    // ApiResponse가 사용할 성공 상태 정보를 반환합니다.
    @Override
    public ReasonDTO getReason() {
        return new ReasonDTO(status, code, message);
    }
}
