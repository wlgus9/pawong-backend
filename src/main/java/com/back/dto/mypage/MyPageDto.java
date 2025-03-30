package com.back.dto.mypage;

import com.back.domain.Member;
import com.back.domain.Pet;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MyPageDto {
    private final String email;
    private final String password;
    private final String userName;
    private final String nickName;
    private final String birth;
    private final Character gender;
    private final String phone;
    private final int userType;
    private final String comment;
    private final List<Pet> pets;

    @Builder
    public MyPageDto(String email, String password, String userName, String nickName, String birth,
                     Character gender, String phone, int userType, String comment, List<Pet> pets) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.nickName = nickName;
        this.birth = birth;
        this.gender = gender;
        this.phone = phone;
        this.userType = userType;
        this.comment = comment;
        this.pets = pets;
    }

    public static MyPageDto getUserInfo(Member member) {
        return MyPageDto.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .userName(member.getUserName())
                .nickName(member.getNickName())
                .birth(member.getBirth())
                .gender(member.getGender())
                .phone(member.getPhone())
                .userType(member.getUserType())
                .comment(member.getComment())
                .pets(member.getPets())
                .build();
    }
}
