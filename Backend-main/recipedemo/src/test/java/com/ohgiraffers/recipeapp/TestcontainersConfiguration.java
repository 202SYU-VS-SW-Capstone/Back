package com.ohgiraffers.recipeapp;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    MySQLContainer<?> mysqlContainer() {
        return new MySQLContainer<>(DockerImageName.parse("mysql:8.0")) // MySQL 버전 8.0 사용
                .withDatabaseName("saegil")   // 데이터베이스 이름 설정
                .withUsername("root")        // 사용자 이름 설정
                .withPassword("1868");       // 비밀번호 설정
    }
}
