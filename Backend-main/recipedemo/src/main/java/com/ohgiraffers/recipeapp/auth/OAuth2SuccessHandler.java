package com.ohgiraffers.recipeapp.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    public OAuth2SuccessHandler(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email"); // 카카오에서 제공하는 이메일 정보

        if (email == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email not found in OAuth2 response");
            return;
        }

        // JWT 생성
        String token = jwtProvider.createToken(email, "ROLE_USER");

        // 응답 헤더에 JWT 추가
        response.setHeader("Authorization", "Bearer " + token);

        // JSON 응답 생성
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Login successful");
        responseBody.put("email", email);
        responseBody.put("token", token);

        // 응답 반환
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(responseBody));
    }
}
