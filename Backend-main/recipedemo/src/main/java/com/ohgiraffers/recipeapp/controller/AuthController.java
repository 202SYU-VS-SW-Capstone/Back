package com.ohgiraffers.recipeapp.controller;

import com.ohgiraffers.recipeapp.auth.JwtProvider;
import com.ohgiraffers.recipeapp.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final JwtProvider jwtProvider;

    /**
     * JWT 발급: 이메일과 역할을 받아 토큰 생성
     */
    @PostMapping("/token")
    public ResponseEntity<TokenDto> getToken(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String role = request.getOrDefault("role", "ROLE_USER"); // 기본 역할 설정
        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body(null);
        }

        System.out.println("Received email: " + email + ", Role: " + role);

        // JWT 생성 및 DTO 반환
        TokenDto tokenDto = jwtProvider.generateTokenDto(email, role);
        return ResponseEntity.ok(tokenDto);
    }

    /**
     * 로그인: 이메일로만 JWT 발급
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email is required!"));
        }

        // 기본 역할로 JWT 생성
        String token = jwtProvider.createToken(email, "ROLE_USER");

        return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "email", email,
                "token", token
        ));
    }
}
