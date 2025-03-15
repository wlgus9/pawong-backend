package com.back.controller;

import com.back.dto.TokenDto;
import com.back.dto.member.LoginDto;
import com.back.dto.member.SignupDto;
import com.back.global.response.ResponseDto;
import com.back.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "회원", description = "회원 api")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseDto<Void> signup(@RequestBody SignupDto signupDto) {
        return memberService.signup(signupDto);
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseDto<TokenDto> login(@RequestBody LoginDto loginDto) {
        return memberService.login(loginDto);
    }

    @Operation(summary = "토큰 재발급")
    @PostMapping("/reissue")
    public ResponseDto<TokenDto> reissue(@RequestBody TokenDto tokenDto) {
        return memberService.reissue(tokenDto);
    }
}
