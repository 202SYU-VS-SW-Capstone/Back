package com.ohgiraffers.recipeapp.auth;

import com.ohgiraffers.recipeapp.dto.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Collections;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${spring.jwt.secret}")
    private String secret;

    @Value("${spring.jwt.expiration}")
    private long validityInMilliseconds;

    private Key secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // JWT 생성 메서드
    public String createToken(String email, String role) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey)
                .compact();
    }

    // TokenDto 반환 메서드
    public TokenDto generateTokenDto(String email, String role) {
        String token = createToken(email, role);
        long expirationTime = validityInMilliseconds;

        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(token)
                .accessTokenExpiresIn(expirationTime)
                .build();
    }

    // JWT 유효성 검사 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // JWT에서 인증 정보 추출 메서드
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String role = claims.get("role", String.class);
        return new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }
}
