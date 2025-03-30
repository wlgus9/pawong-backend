package com.back.global.jwt;

import com.back.global.response.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.back.global.response.ResponseMessage.*;

/**
 * 헤더(Authorization)에 있는 토큰을 꺼내 이상이 없는 경우 SecurityContext에 저장
 * Request 이전에 작동
 */
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private static final List<String> EXCLUDE_URLS = Arrays.asList(
            "/api/users/login",
            "/api/users/signup",
            "/swagger-ui/**",
            "/api-docs/**"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return EXCLUDE_URLS.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        log.info("doFilterInternal Start");

        String token = jwtProvider.resolveToken(request);
        log.info("token : {}", token);

        try {
            if (token != null) {
                // 1. Redis에서 블랙리스트 조회
                String isBlacklisted = redisTemplate.opsForValue().get(token);
                log.info("isBlacklisted : {}", isBlacklisted);

                if (isBlacklisted != null) {
                    SecurityContextHolder.clearContext();
                    sendErrorResponse(response, LOGOUT_TOKEN);
                    return;
                }

                // 2. 토큰 유효성 검증
                if (!jwtProvider.validateToken(token)) {
                    sendErrorResponse(response, INVALID_TOKEN);
                    return;
                }

                // 3. SecurityContext에 저장
                Authentication auth = jwtProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (RedisConnectionFailureException e) {
            SecurityContextHolder.clearContext();
            sendErrorResponse(response, REDIS_CONN_ERROR);
            return;
        } catch (Exception e) {
            sendErrorResponse(response, INVALID_TOKEN);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, ResponseMessage responseMessage) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(responseMessage.getCode());
        response.setContentType("application/json; charset=UTF-8");

        String json = new ObjectMapper().writeValueAsString(responseMessage.getMessage());
        response.getWriter().write(json);
    }
}

