package com.back.global.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseDto<T> {
    private final int code;
    private final String message;
    private final T data;

    @Builder
    public ResponseDto(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseDto<T> from(ResponseMessage responseMessage) {
        return ResponseDto.<T>builder()
                .code(responseMessage.getCode())
                .message(responseMessage.getMessage())
                .build();
    }

    public static <T> ResponseDto<T> from(ResponseMessage responseMessage, T data) {
        return ResponseDto.<T>builder()
                .code(responseMessage.getCode())
                .message(responseMessage.getMessage())
                .data(data)
                .build();
    }
}
