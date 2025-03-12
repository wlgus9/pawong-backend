package com.back.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    private String userName;
    private String nickName;
    private String email;
    private String password;
    private Date birth;
    private Character gender;
    private String phone;
    private int userRole;
    private String comment;

    // 소셜 로그인 관련 필드
    private String socialId;        // 소셜 로그인 ID (ex: Google ID, Kakao ID)
    private String provider;        // 로그인 제공자 (GOOGLE, KAKAO, NAVER)
    private String profileImageUrl; // 프로필 이미지 URL
    private String refreshToken;    // OAuth 리프레시 토큰 (필요할 경우)
}
