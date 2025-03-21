package com.back.global.exception;

import com.back.global.response.ExceptionResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDto> exception(Exception e) {
        log.error("{} : {}", e.getClass().getName(), e.getMessage());

        if(e instanceof CustomException) {
            return ExceptionResponseDto.from(((CustomException) e).getCode(), e.getMessage());
        }

        if(e instanceof IllegalArgumentException) {
            return ExceptionResponseDto.from(HttpStatus.BAD_REQUEST.value(), "잘못된 파라미터");
        }

        return ExceptionResponseDto.from(HttpStatus.INTERNAL_SERVER_ERROR.value(), "알 수 없는 오류");
    }
}
