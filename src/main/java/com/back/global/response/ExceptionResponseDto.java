package com.back.global.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class ExceptionResponseDto {
    private final int code;
    private final String message;

    @Builder
    public ExceptionResponseDto(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseEntity<ExceptionResponseDto> from(int code, String message) {
        ExceptionResponseDto response = ExceptionResponseDto.builder()
                .code(code)
                .message(message)
                .build();

        return ResponseEntity.status(code).body(response);
    }
}
