package com.ohgiraffers.recipeapp.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Collections;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${spring.jwt.secret}")
    private String secret; // JWT 생성 시 사용할 비밀키

    @Value("${spring.jwt.expiration}")
    private long validityInMilliseconds; // JWT 만료 시간

    private Key secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes()); // 비밀키 초기화
    }

    // JWT 생성 메서드
//    public String createToken(String email, String role) {
//        Claims claims = Jwts.claims().setSubject(email); // 이메일을 클레임의 subject로 설정
//        claims.put("role", role); // 역할(role)을 클레임에 추가
//
//        Date now = new Date();
//        Date validity = new Date(now.getTime() + validityInMilliseconds); // 만료 시간 설정
//
//        return Jwts.builder()
//                .setClaims(claims) // 클레임 추가
//                .setIssuedAt(now) // 발급 시간
//                .setExpiration(validity) // 만료 시간
//                .signWith(secretKey, SignatureAlgorithm.HS256) // 서명 추가
//                .compact();
//    }

    public String createToken(String email, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // JWT 토큰 DTO 생성 메서드
    public TokenDto generateTokenDto(String email, String role) {
        String accessToken = createToken(email, role); // JWT 생성
        Date now = new Date();
        Date accessTokenExpiresIn = new Date(now.getTime() + validityInMilliseconds); // 만료 시간 계산

        // TokenDto 반환
        return TokenDto.builder()
                .grantType("Bearer") // 토큰 타입
                .accessToken(accessToken) // 생성된 JWT 토큰
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime()) // 만료 시간
                .build();
    }

    // JWT 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey) // 비밀키로 서명 검증
                    .build()
                    .parseClaimsJws(token);
            return true; // 토큰이 유효하면 true 반환
        } catch (Exception e) {
            // 유효하지 않은 토큰인 경우 에러 출력
            System.err.println("Invalid JWT token: " + e.getMessage());
            return false; // 토큰이 유효하지 않으면 false 반환
        }
    }

    // JWT 토큰에서 인증 정보 추출
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey) // 비밀키로 서명 검증
                .build()
                .parseClaimsJws(token)
                .getBody(); // 토큰에서 클레임(Claim) 추출

        String email = claims.getSubject(); // 이메일 추출
        String role = claims.get("role", String.class); // 역할(role) 추출

        // 인증 객체 생성 및 반환
        return new UsernamePasswordAuthenticationToken(
                email, // 인증된 사용자 정보
                null, // 인증에 사용된 자격 증명(null로 설정)
                Collections.singletonList(new SimpleGrantedAuthority(role)) // 사용자 권한
        );
    }
}
