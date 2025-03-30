package com.back.domain;

import com.back.dto.member.SignupDto;
import com.back.dto.mypage.UpdateDto;
import com.back.global.common.UserRole;
import com.back.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    private String email;
    private String password;
    private String userName;
    private String nickName;
    private String birth;
    private Character gender;
    private String phone;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private int userType;
    private String comment;

    // 소셜 로그인 관련 필드
    private String socialId;        // 소셜 로그인 ID (ex: Google ID, Kakao ID)
    private String provider;        // 로그인 제공자 (GOOGLE, KAKAO, NAVER)
    private String profileImageUrl; // 프로필 이미지 URL
    private String refreshToken;    // OAuth 리프레시 토큰 (필요할 경우)

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Pet> pets = new ArrayList<>();


    @Builder
    public Member(UUID id, String email, String password, String userName, String nickName, String birth,
                  Character gender, String phone, UserRole userRole, int userType, String comment,
                  String socialId, String provider, String profileImageUrl, String refreshToken) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.nickName = nickName;
        this.birth = birth;
        this.gender = gender;
        this.phone = phone;
        this.userRole = userRole;
        this.userType = userType;
        this.comment = comment;
        this.socialId = socialId;
        this.provider = provider;
        this.profileImageUrl = profileImageUrl;
        this.refreshToken = refreshToken;
    }

    public static Member getUser(SignupDto signupDto) {
        return Member.builder()
                .email(signupDto.getEmail())
                .password(signupDto.getPassword())
                .userName(signupDto.getUserName())
                .nickName(signupDto.getNickName())
                .birth(signupDto.getBirth())
                .gender(signupDto.getGender())
                .phone(signupDto.getPhone())
                .userType(signupDto.getUserType())
                .comment(signupDto.getComment())
                .build();
    }

    public static Member signup(SignupDto signupDto, BCryptPasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(signupDto.getEmail())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .userName(signupDto.getUserName())
                .nickName(signupDto.getNickName())
                .birth(signupDto.getBirth())
                .gender(signupDto.getGender())
                .phone(signupDto.getPhone())
                .userRole(UserRole.ROLE_USER)
                .userType(signupDto.getUserType())
                .comment(signupDto.getComment())
                .build();
    }

    public void update(UpdateDto updateDto) {
        this.nickName = updateDto.getNickName();
        this.comment = updateDto.getComment();
        this.phone = updateDto.getPhone();
    }
}
