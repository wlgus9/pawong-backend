package com.back.service;

import com.back.domain.Member;
import com.back.dto.mypage.MyPageDto;
import com.back.dto.mypage.UpdateDto;
import com.back.global.exception.CustomException;
import com.back.global.jwt.JwtProvider;
import com.back.global.response.ResponseDto;
import com.back.repository.querydsl.MyPageRepositoryImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.back.global.response.ResponseMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MyPageRepositoryImpl myPageRepository;
    private final JwtProvider jwtProvider;

    public ResponseDto<MyPageDto> searchUserInfo() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Member userInfo = myPageRepository.findByEmailWithPets(email)
                .orElseThrow(() -> new CustomException(MYPAGE_SEARCH_FAIL));

        return ResponseDto.from(MYPAGE_SEARCH_SUCCESS, MyPageDto.getUserInfo(userInfo));
    }

    @Transactional
    public ResponseDto<MyPageDto> updateUserInfo(UpdateDto updateDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Member updateUserInfo = myPageRepository.findByEmailWithPets(email)
                .orElseThrow(() -> new CustomException(MYPAGE_SEARCH_FAIL));

        updateUserInfo.update(updateDto);

        return ResponseDto.from(MYPAGE_USER_INFO_UPDATE_SUCCESS, MyPageDto.getUserInfo(updateUserInfo));
    }

    public ResponseDto<Void> logout(HttpServletRequest request) {
        jwtProvider.logout(request);
        return ResponseDto.from(LOGOUT_SUCCESS);
    }
}
