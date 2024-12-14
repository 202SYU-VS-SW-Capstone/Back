package com.ohgiraffers.recipeapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "members")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private String role; // 예: "USER", "ADMIN"
}

// 커밋 및 푸쉬 풀리퀘 테스트용 주석입니다.

//package com.ohgiraffers.recipeapp.entity;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Getter
//@Setter
//public class Member {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String username;
//    private String email;
//    private String password;
//    private String role; // 예: ROLE_USER
//}
