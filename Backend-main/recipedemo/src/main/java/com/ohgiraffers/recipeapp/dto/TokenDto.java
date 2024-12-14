package com.ohgiraffers.recipeapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "JWT 토큰 응답 DTO")
public class TokenDto {

    @Schema(description = "토큰 유형 (Bearer)", example = "Bearer")
    private String grantType;

    @Schema(description = "Access Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;

    @Schema(description = "Access Token 만료 시간 (밀리초 단위)", example = "3600000")
    private long accessTokenExpiresIn;
}
