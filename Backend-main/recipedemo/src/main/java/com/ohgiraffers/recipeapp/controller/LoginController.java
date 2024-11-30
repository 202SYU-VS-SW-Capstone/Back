package com.ohgiraffers.recipeapp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String homePage(Authentication authentication) {
        // 사용자가 인증되어 있다면, home.html로 리다이렉트
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/home"; // 인증된 사용자는 home으로 리다이렉트
        }

        // 인증되지 않은 사용자는 login.html 반환
        return "login";
    }


    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
