package com.ohgiraffers.recipeapp.service;

import com.ohgiraffers.recipeapp.entity.Inquiry;
import com.ohgiraffers.recipeapp.enums.InquiryStatus;
import com.ohgiraffers.recipeapp.repository.InquiryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    public InquiryService(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    /**
     * 모든 문의 조회
     *
     * @return List<Inquiry> - 모든 문의 목록
     */
    public List<Inquiry> getAllInquiries() {
        return inquiryRepository.findAll();
    }

    /**
     * 특정 회원의 모든 문의 조회
     *
     * @param memberId 회원 ID
     * @return List<Inquiry> - 해당 회원의 문의 목록
     */
    public List<Inquiry> getInquiriesByMember(Long memberId) {
        return inquiryRepository.findByMemberId(memberId);
    }

    /**
     * 특정 상태의 문의 조회
     *
     * @param status 문의 상태
     * @return List<Inquiry> - 해당 상태의 문의 목록
     */
    public List<Inquiry> getInquiriesByStatus(InquiryStatus status) {
        return inquiryRepository.findByStatus(status);
    }

    /**
     * ID로 특정 문의 조회
     *
     * @param id 문의 ID
     * @return Inquiry - 조회된 문의 데이터
     * @throws IllegalArgumentException - 해당 ID의 문의가 없을 경우 예외 발생
     */
    public Inquiry getInquiryById(Long id) {
        return inquiryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inquiry not found with id: " + id));
    }

    /**
     * 새로운 문의 저장
     *
     * @param inquiry 저장할 문의 데이터
     * @return Inquiry - 저장된 문의 데이터
     */
    public Inquiry saveInquiry(Inquiry inquiry) {
        inquiry.setCreatedAt(LocalDate.now()); // 생성 날짜 설정
        return inquiryRepository.save(inquiry);
    }

    /**
     * 특정 문의 상태 업데이트
     *
     * @param id 문의 ID
     * @param status 업데이트할 상태
     * @return Inquiry - 업데이트된 문의 데이터
     */
    public Inquiry updateInquiryStatus(Long id, InquiryStatus status) {
        Inquiry inquiry = getInquiryById(id);
        inquiry.setStatus(status);
        return inquiryRepository.save(inquiry);
    }

    /**
     * 특정 문의 삭제
     *
     * @param id 삭제할 문의 ID
     */
    public void deleteInquiry(Long id) {
        inquiryRepository.deleteById(id);
    }
}
