package com.ohgiraffers.recipeapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.security.oauth2.client.registration.kakao.client-id=",
        "spring.security.oauth2.client.registration.kakao.client-secret=",
        "spring.security.oauth2.client.registration.kakao.redirect-uri="
})
class RecipedemoApplicationTests {

    @Test
    void contextLoads() {
        // 컨텍스트 로드 테스트
    }
}
