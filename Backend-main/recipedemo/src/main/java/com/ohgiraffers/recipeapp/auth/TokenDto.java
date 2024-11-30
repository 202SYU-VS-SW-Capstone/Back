package com.ohgiraffers.recipeapp.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenDto {
    private String grantType; // Bearer
    private String accessToken; // JWT 토큰
    private long accessTokenExpiresIn; // 만료 시간
}
