package com.back.dto;

import com.back.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@Builder
public class UserDto {
    private String userName;
    private String nickName;
    private String email;
    private String password;
    private Date birth;
    private Character gender;
    private String phone;
    private int userRole;
    private String comment;

    public static UserDto getUserDto(String userName) {
        return UserDto.builder()
                .userName(userName)
                .build();
    }
}
