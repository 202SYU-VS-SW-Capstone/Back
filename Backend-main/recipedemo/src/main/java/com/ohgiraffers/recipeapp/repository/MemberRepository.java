package com.ohgiraffers.recipeapp.repository;

import com.ohgiraffers.recipeapp.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email); // 이메일로 회원 조회
}
