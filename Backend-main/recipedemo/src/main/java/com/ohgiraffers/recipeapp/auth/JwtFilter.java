package com.ohgiraffers.recipeapp.auth;

import com.ohgiraffers.recipeapp.auth.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 요청 경로 가져오기
        String requestPath = request.getRequestURI();

        // 특정 경로는 필터 제외
        if (requestPath.equals("/auth/login") || requestPath.equals("/auth/token")) {
            filterChain.doFilter(request, response);
            return; // 필터를 중단하고 다음 필터로 넘어감
        }

        // 헤더에서 JWT 추출
        String token = resolveToken(request);

        // 토큰 검증
        if (token != null && jwtProvider.validateToken(token)) {
            // 인증 정보 설정
            SecurityContextHolder.getContext().setAuthentication(jwtProvider.getAuthentication(token));
        }

        // 다음 필터로 이동
        filterChain.doFilter(request, response);
    }

    // 요청 헤더에서 JWT 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 이후의 값 반환
        }
        return null;
    }
}
