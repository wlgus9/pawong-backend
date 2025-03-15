package com.back.dto.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginDto {
    private final String email;
    private final String password;

    @Builder
    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static LoginDto from(String email, String password) {
        return LoginDto.builder()
                .email(email)
                .password(password)
                .build();
    }
}
