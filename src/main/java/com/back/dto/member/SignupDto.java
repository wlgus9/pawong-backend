package com.back.dto.member;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class SignupDto {
    private String email;
    private String password;
    private String userName;
    private String nickName;
    private String birth;
    private Character gender;
    private String phone;
    private int userType;
    private String comment;

    @Builder
    public SignupDto(String email, String password, String userName, String nickName, String birth,
                     Character gender, String phone, int userType, String comment) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.nickName = nickName;
        this.birth = birth;
        this.gender = gender;
        this.phone = phone;
        this.userType = userType;
        this.comment = comment;
    }
}
