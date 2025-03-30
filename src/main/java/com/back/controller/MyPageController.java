package com.back.controller;

import com.back.dto.TokenDto;
import com.back.dto.member.LoginDto;
import com.back.dto.member.SignupDto;
import com.back.dto.mypage.MyPageDto;
import com.back.dto.mypage.UpdateDto;
import com.back.global.response.ResponseDto;
import com.back.service.MemberService;
import com.back.service.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/my-page")
@RequiredArgsConstructor
@Tag(name = "마이페이지", description = "마이페이지 api")
public class MyPageController {

    private final MyPageService myPageService;

    @Operation(summary = "마이페이지 조회")
    @GetMapping("/search/user-info")
    public ResponseDto<MyPageDto> searchUserInfo() {
        return myPageService.searchUserInfo();
    }

    @Operation(summary = "회원정보 수정")
    @PatchMapping("/update/user-info")
    public ResponseDto<MyPageDto> updateUserInfo(@RequestBody UpdateDto updateDto) {
        return myPageService.updateUserInfo(updateDto);
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public ResponseDto<Void> logout(HttpServletRequest request) {
        return myPageService.logout(request);
    }
}
