package com.back.global.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.back.global.response.ResponseMessage.INVALID_TOKEN;

/**
 * 유저 정보는 있으나 자원에 접근할 수 있는 권한이 없는 경우 : SC_FORBIDDEN (403) 응답
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws ServletException, IOException {
        // response.sendError(HttpServletResponse.SC_FORBIDDEN);
        response.setStatus(INVALID_TOKEN.getCode());
        response.setContentType("application/json");
        response.getWriter().write("{\"message\":\"" + INVALID_TOKEN.getMessage() + "\"}");
    }
}