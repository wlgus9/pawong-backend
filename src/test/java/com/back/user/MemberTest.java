package com.back.user;

import com.back.domain.Member;
import com.back.dto.member.SignupDto;
import com.back.global.response.ResponseDto;
import com.back.repository.MemberRepository;
import com.back.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MemberTest {
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    private final SignupDto signupDto = SignupDto.builder()
            .email("test@gmail.com")
            .password("1234")
            .build();

    @Test
    @DisplayName("회원가입 성공")
    void signupSuccess() {
        // given
        Member member = Member.getUser(signupDto);
        given(memberRepository.save(any(Member.class))).willReturn(member);

        // when
        ResponseDto<Void> responseDto = memberService.signup(signupDto);

        // then
        assertThat(responseDto.getCode()).isEqualTo(200);
    }
}
