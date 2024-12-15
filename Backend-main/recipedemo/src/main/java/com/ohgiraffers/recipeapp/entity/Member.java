package com.ohgiraffers.recipeapp.entity;

import jakarta.persistence.*;
import lombok.*;

//@Entity
//@Table(name = "members")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Getter
//@Setter
//public class Member {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String username;
//    private String email;
//    private String password;
//    private String role; // 예: "USER", "ADMIN"
//
//
//}

@Entity
@Table(name = "members")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id") // member_id 컬럼과 매핑
    private Long id;

    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "ROLE_USER"; // 기본 권한
    // 예: "USER", "ADMIN"
}
