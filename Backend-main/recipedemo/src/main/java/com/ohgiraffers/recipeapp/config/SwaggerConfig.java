package com.ohgiraffers.recipeapp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Recipe App API") // API 제목
                        .version("1.0.0") // API 버전
                        .description("Recipe Application API documentation") // API 설명
                        .contact(new Contact() // 연락 정보
                                .name("Developer Name")
                                .email("developer@example.com")
                                .url("https://www.example.com")))
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth")) // Security 설정 추가
                .components(new Components()
                        .addSecuritySchemes("BearerAuth", new SecurityScheme()
                                .name("Authorization")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"))); // JWT 인증 방식 정의
    }
}
