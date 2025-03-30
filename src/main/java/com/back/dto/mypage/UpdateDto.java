package com.back.dto.mypage;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateDto {
    private final String nickName;
    private final String comment;
    private final String phone;

    @Builder
    public UpdateDto(String nickName, String comment, String phone) {
        this.nickName = nickName;
        this.comment = comment;
        this.phone = phone;
    }

    public static UpdateDto nickNameUpdateDto(String nickName) {
        return UpdateDto.builder()
                .nickName(nickName)
                .build();
    }

    public static UpdateDto commentUpdateDto(String comment) {
        return UpdateDto.builder()
                .comment(comment)
                .build();
    }

    public static UpdateDto phoneUpdateDto(String phone) {
        return UpdateDto.builder()
                .phone(phone)
                .build();
    }

    public static UpdateDto updateDto(String nickName, String comment, String phone) {
        return UpdateDto.builder()
                .nickName(nickName)
                .comment(comment)
                .phone(phone)
                .build();
    }
}
