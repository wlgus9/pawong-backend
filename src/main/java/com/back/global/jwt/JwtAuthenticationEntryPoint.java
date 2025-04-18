package com.back.global.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.back.global.response.ResponseMessage.MISSING_TOKEN;

/**
 * 유저 정보 없이 접근한 경우 : SC_UNAUTHORIZED (401) 응답
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        // response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        response.setStatus(MISSING_TOKEN.getCode());
        response.setContentType("application/json");
        response.getWriter().write("{\"message\":\"" + MISSING_TOKEN.getMessage() + "\"}");
    }
}