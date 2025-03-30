package com.back.global.jwt;

import com.back.global.exception.CustomException;
import com.back.global.response.ResponseMessage;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * [JWT 관련 메서드를 제공하는 클래스]
 */
@Slf4j
@Component
public class JwtProvider {
    private static final String AUTH_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";

    private final RedisTemplate<String, String> redisTemplate;
    private final Key secretKey; // JWT secret key

    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${jwt.expiration_time.access_token}")
    private long accessTokenExpTime; // access 토큰 유효기간

    @Value("${jwt.expiration_time.refresh_token}")
    private long refreshTokenExpTime; // refresh 토큰 유효기간

    public JwtProvider(@Value("${jwt.secret_key}") String secretKey, RedisTemplate<String, String> redisTemplate) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.redisTemplate = redisTemplate;
    }

    /**
     * Token 생성
     */
    public String createToken(Authentication authentication) {
        Claims claims = Jwts.claims().setSubject(authentication.getName());
        Date now = new Date();
        Date accessExpireDate = new Date(now.getTime() + accessTokenExpTime);
        Date refreshExpireDate = new Date(now.getTime() + refreshTokenExpTime);

        String accessToken =  Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(accessExpireDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(refreshExpireDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        // refresh token redis에 저장
        redisTemplate.opsForValue().set(
                authentication.getName(),
                refreshToken,
                refreshTokenExpTime,
                TimeUnit.MILLISECONDS
        );

        return accessToken;
    }

    /**
     * 토큰으로부터 클레임을 만들고, 이를 통해 User 객체 생성해 Authentication 객체 반환
     */
    public Authentication getAuthentication(String token) {
        String userPrincipal = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        log.info("getAuthentication userPrincipal = {}", userPrincipal);

        UserDetails userDetails = userDetailsService.loadUserByUsername(userPrincipal);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * http 헤더에서 bearer 토큰 추출
     */
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * Access 토큰을 검증
     */
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
            return true;
        } catch(JwtException e) {
            log.error("validateToken() >> Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 로그아웃 -> 토큰 블랙리스트 처리
     */
    public void logout(HttpServletRequest request) {
        String accessToken = resolveToken(request);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // 1. 리프레시 토큰 삭제
        redisTemplate.delete(email);

        // 2. 액세스 토큰 블랙리스트 처리 (예: 남은 유효시간 동안만 저장)
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();

        // 3. JWT의 남은 만료 시간 계산
        long expiration = claims.getExpiration().getTime() - System.currentTimeMillis();

        // 4. 레디스 저장
        redisTemplate.opsForValue().set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);
    }

}
