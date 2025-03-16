package com.back.global.exception;

import com.back.global.response.ResponseMessage;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private int code;
    private final String message;
    private Throwable throwable;

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    public CustomException(ResponseMessage responseMessage) {
        super(responseMessage.getMessage());
        this.code = responseMessage.getCode();
        this.message = responseMessage.getMessage();
    }

    public CustomException(ResponseMessage responseMessage, Throwable throwable) {
        super(responseMessage.getMessage());
        this.code = responseMessage.getCode();
        this.message = responseMessage.getMessage();
        this.throwable = throwable;
    }
}
