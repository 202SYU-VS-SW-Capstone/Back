package com.ohgiraffers.recipeapp.repository;

import com.ohgiraffers.recipeapp.entity.Inquiry;
import com.ohgiraffers.recipeapp.enums.InquiryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    /**
     * 특정 회원의 모든 문의 조회
     * @param memberId 회원 ID
     * @return List<Inquiry> - 해당 회원의 문의 목록
     */
    List<Inquiry> findByMemberId(Long memberId);

    /**
     * 특정 상태의 문의 조회
     * @param status 문의 상태
     * @return List<Inquiry> - 해당 상태의 문의 목록
     */
    List<Inquiry> findByStatus(InquiryStatus status);
}
