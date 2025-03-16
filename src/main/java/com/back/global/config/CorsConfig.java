package com.back.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);       // 내 서버가 응답할 때 json 자바 스크립트 허용
        config.addAllowedOriginPattern("*");    // 포트번호 응답 다름 허용
        config.addAllowedHeader("*");           // 헤더 값 응답 허용
        config.addAllowedMethod("*");           // 메서드 응답 허용

        source.registerCorsConfiguration("/**", config); // 모든 url에 대해 위 사항 적용
        return new CorsFilter(source);
    }
}