package com.ohgiraffers.recipeapp.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 헤더에서 JWT 추출
        String token = resolveToken(request);

        // 토큰 유효성 검증 및 인증 정보 설정
//        if (token != null && jwtProvider.validateToken(token)) {
//            // 인증 정보를 SecurityContext에 설정
//            SecurityContextHolder.getContext().setAuthentication(jwtProvider.getAuthentication(token));
//        }

        if (token != null && jwtProvider.validateToken(token)) {
            System.out.println("유효한 토큰입니다: " + token);
            Authentication auth = jwtProvider.getAuthentication(token);
            System.out.println("인증 정보: " + auth);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            System.out.println("유효하지 않은 토큰입니다.");
        }


        // 다음 필터로 이동
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
