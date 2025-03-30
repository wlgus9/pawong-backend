package com.back.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {
    SIGNUP_SUCCESS(HttpStatus.OK.value(), "회원가입이 완료되었습니다.")
    , SIGNUP_EMAIL_OK(HttpStatus.OK.value(), "사용 가능한 이메일입니다.")
    , LOGIN_SUCCESS(HttpStatus.OK.value(), "로그인에 성공했습니다.")
    , LOGOUT_SUCCESS(HttpStatus.OK.value(), "로그아웃에 성공했습니다.")
    , MYPAGE_SEARCH_SUCCESS(HttpStatus.OK.value(), "마이페이지 조회가 완료되었습니다.")
    , MYPAGE_USER_INFO_UPDATE_SUCCESS(HttpStatus.OK.value(), "회원 정보 수정이 완료되었습니다.")
    , PET_CREATE_SUCCESS(HttpStatus.OK.value(), "반려동물 정보 등록이 완료되었습니다.")
    , PET_SEARCH_SUCCESS(HttpStatus.OK.value(), "반려동물 정보 조회가 완료되었습니다.")
    , NEW_TOKEN(HttpStatus.OK.value(), "토큰 재발급이 완료되었습니다.")

    , SIGNUP_DUPLICATE(HttpStatus.BAD_REQUEST.value(), "이메일이 중복되었습니다.")
    , SIGNUP_FAIL(HttpStatus.INTERNAL_SERVER_ERROR.value(), "회원가입에 실패했습니다.")
    , LOGIN_FAIL(HttpStatus.BAD_REQUEST.value(), "아이디 또는 비밀번호가 일치하지 않습니다.")
    , LOGOUT_FAIL(HttpStatus.INTERNAL_SERVER_ERROR.value(), "로그아웃에 실패했습니다.")
    , MYPAGE_SEARCH_FAIL(HttpStatus.BAD_REQUEST.value(), "마이페이지 조회에 실패했습니다.")
    , MYPAGE_USER_INFO_UPDATE_FAIL(HttpStatus.OK.value(), "회원 정보 수정에 실패했습니다.")
    , PET_CREATE_FAIL(HttpStatus.BAD_REQUEST.value(), "반려동물 정보 등록에 실패했습니다.")
    , PET_SEARCH_FAIL(HttpStatus.BAD_REQUEST.value(), "반려동물 정보 조회에 실패했습니다.")
    , MISSING_TOKEN(HttpStatus.UNAUTHORIZED.value(), "토큰이 존재하지 않습니다.")
    , LOGOUT_TOKEN(HttpStatus.UNAUTHORIZED.value(), "로그아웃된 토큰입니다.")
    , INVALID_TOKEN(HttpStatus.FORBIDDEN.value(), "토큰이 유효하지 않습니다.")
    , REDIS_CONN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Redis 연결에 오류가 발생했습니다.")
    ;

    private final int code;
    private final String message;
}
