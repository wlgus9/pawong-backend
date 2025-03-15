package com.back.service;

import com.back.domain.Member;
import com.back.dto.member.LoginDto;
import com.back.dto.member.SignupDto;
import com.back.dto.TokenDto;
import com.back.global.exception.CustomException;
import com.back.global.jwt.JwtProvider;
import com.back.global.response.ResponseDto;
import com.back.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.back.global.response.ResponseMessage.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public ResponseDto<Void> signup(SignupDto signupDto) {
        Member member = Member.signup(signupDto, passwordEncoder);

        if(memberRepository.existsByEmail(member.getEmail())) {
            throw new CustomException(SIGNUP_DUPLICATE);
        }

        memberRepository.save(member);

        return ResponseDto.from(SIGNUP_SUCCESS);
    }

    @Transactional
    public ResponseDto<TokenDto> login(LoginDto loginDto) {

        // 1. Login Email/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()
        );

        // 2. 실제 검증이 이루어지는 부분 (= 사용자 비밀번호 체크)
        //    authenticate 메서드 실행 시 CustomUserDetailsService에서 만들었던 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder
                .getObject()
                .authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 토큰 생성
        TokenDto tokenDto = TokenDto.from(
                jwtProvider.createAccessToken(authentication),
                jwtProvider.createRefreshToken(authentication)
        );

        return ResponseDto.from(LOGIN_SUCCESS, tokenDto);
    }

    @Transactional
    public ResponseDto<TokenDto> reissue(TokenDto tokenDto) {

        String accessToken = tokenDto.getAccessToken();
        String refreshToken = tokenDto.getRefreshToken();

        // Refresh Token 검증
        jwtProvider.validateToken(refreshToken);

        // Access Token 에서 Member ID 가져오기
        Authentication authentication = jwtProvider.getAuthentication(accessToken);

        // Redis에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        String redisRefreshToken = redisTemplate.opsForValue().get(authentication.getName());

        // Refresh Token 일치하는지 검사
        if (!redisRefreshToken.equals(refreshToken)) {
            throw new CustomException(INVALID_TOKEN);
        }

        // 새로운 토큰 생성
        TokenDto newTokenDto = TokenDto.from(
                jwtProvider.createAccessToken(authentication),
                jwtProvider.createRefreshToken(authentication)
        );

        return ResponseDto.from(NEW_TOKEN, newTokenDto);
    }
}
