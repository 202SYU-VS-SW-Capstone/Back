package com.ohgiraffers.recipeapp.entity;

import com.ohgiraffers.recipeapp.enums.MemberType;
import com.ohgiraffers.recipeapp.enums.SecurityQuestion;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "members")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id; // 회원 ID

    @Column(nullable = false, unique = true)
    private String email; // 회원 이메일 (고유 값)

    @Column(nullable = false)
    private String password; // 회원 비밀번호

    @Column(nullable = false)
    private String nickname; // 닉네임

    @ManyToOne
    @JoinColumn(name = "profile_picture_id", referencedColumnName = "member_image_id")
    private MemberImage profilePicture; // 프로필 이미지

    @Enumerated(EnumType.STRING)
    @Column(name = "security_question", nullable = false)
    private SecurityQuestion securityQuestion; // 보안 질문

    @Enumerated(EnumType.STRING)
    @Column(name = "member_type", nullable = false)
    private MemberType memberType; // 회원 유형

    @Column(name = "join_date", nullable = false)
    private LocalDate joinDate; // 가입 날짜

    @Column(name = "last_access_date")
    private LocalDate lastAccessDate; // 마지막 접속 날짜
}
