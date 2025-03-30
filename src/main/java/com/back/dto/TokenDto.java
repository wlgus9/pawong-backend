package com.back.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenDto {
    private final String accessToken;

    @Builder
    public TokenDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public static TokenDto from(String accessToken) {
        return TokenDto.builder()
                .accessToken(accessToken)
                .build();
    }
}
