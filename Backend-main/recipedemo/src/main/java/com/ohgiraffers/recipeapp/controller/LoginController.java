package com.ohgiraffers.recipeapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/")
    @Operation(summary = "홈 페이지", description = "사용자가 인증되었는지 확인 후, 홈 또는 로그인 페이지로 이동합니다.")
    @ApiResponse(responseCode = "302", description = "리다이렉션 성공")
    public String homePage(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/home";
        }
        return "login";
    }

    @GetMapping("/login")
    @Operation(summary = "로그인 페이지", description = "로그인 페이지를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "로그인 페이지 반환 성공")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/home")
    @Operation(summary = "홈 페이지", description = "홈 페이지를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "홈 페이지 반환 성공")
    public String home() {
        return "home";
    }
}
