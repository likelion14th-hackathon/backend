package com.todayscasting.common.code;

/**
 * 성공 상태 코드가 공통으로 구현해야 하는 규격입니다.
 * 도메인별 성공 코드를 추가할 때 이 인터페이스를 구현합니다.
 */
public interface BaseCode {

    ReasonDTO getReason();
}
