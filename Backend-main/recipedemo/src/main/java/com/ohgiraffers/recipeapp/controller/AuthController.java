package com.ohgiraffers.recipeapp.controller;

import com.nimbusds.jwt.JWT;
import com.ohgiraffers.recipeapp.auth.JwtProvider;
import com.ohgiraffers.recipeapp.auth.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtProvider jwtProvider;

//     이메일과 역할로 JWT 발급
    @PostMapping("/auth/token")
    public TokenDto getToken(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String role = request.getOrDefault("role", "ROLE_USER"); // 역할 기본값 설정
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        System.out.println("Received email: " + email + ", Role: " + role);
        return jwtProvider.generateTokenDto(email, role); // JWT 생성
    }


    @PostMapping("/auth/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email is required!"));
        }

        // JWT 토큰 생성
        String token = jwtProvider.createToken(email, "ROLE_USER");

        // 응답에 JWT 포함
        return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "email", email,
                "token", token
        ));
    }



}