package com.back.service;

import com.back.domain.Member;
import com.back.global.exception.CustomException;
import com.back.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static com.back.global.response.ResponseMessage.LOGIN_FAIL;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("loadUserByUsername param = {}", email);

        return memberRepository.findByEmail(email)
                .map(this::createUserDetails)
                .orElseThrow(() -> new CustomException(LOGIN_FAIL));
    }

    // DB 에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(Member member) {
        log.info("createUserDetails userRole = {}", member.getUserRole().toString());
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getUserRole().toString());

        return new User(
                member.getEmail(),
                member.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }
}