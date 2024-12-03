package com.ohgiraffers.recipeapp.controller;

import com.ohgiraffers.recipeapp.entity.Inquiry;
import com.ohgiraffers.recipeapp.enums.InquiryStatus;
import com.ohgiraffers.recipeapp.service.InquiryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inquiries")
public class InquiryController {

    private final InquiryService inquiryService;

    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    /**
     * 모든 문의 조회
     *
     * @return ResponseEntity<List<Inquiry>> - 모든 문의 목록
     */
    @GetMapping
    public ResponseEntity<List<Inquiry>> getAllInquiries() {
        List<Inquiry> inquiries = inquiryService.getAllInquiries();
        return ResponseEntity.ok(inquiries);
    }

    /**
     * 특정 회원의 문의 조회
     *
     * @param memberId 회원 ID (Query Parameter)
     * @return ResponseEntity<List<Inquiry>> - 해당 회원의 문의 목록
     */
    @GetMapping("/member")
    public ResponseEntity<List<Inquiry>> getInquiriesByMember(@RequestParam("memberId") Long memberId) {
        List<Inquiry> inquiries = inquiryService.getInquiriesByMember(memberId);
        return ResponseEntity.ok(inquiries);
    }

    /**
     * 특정 상태의 문의 조회
     *
     * @param status 문의 상태 (Query Parameter)
     * @return ResponseEntity<List<Inquiry>> - 해당 상태의 문의 목록
     */
    @GetMapping("/status")
    public ResponseEntity<List<Inquiry>> getInquiriesByStatus(@RequestParam("status") InquiryStatus status) {
        List<Inquiry> inquiries = inquiryService.getInquiriesByStatus(status);
        return ResponseEntity.ok(inquiries);
    }

    /**
     * ID로 특정 문의 조회
     *
     * @param id 문의 ID (Path Variable)
     * @return ResponseEntity<Inquiry> - 조회된 문의 데이터
     */
    @GetMapping("/{id}")
    public ResponseEntity<Inquiry> getInquiryById(@PathVariable("id") Long id) {
        Inquiry inquiry = inquiryService.getInquiryById(id);
        return ResponseEntity.ok(inquiry);
    }

    /**
     * 새로운 문의 추가
     *
     * @param inquiry 저장할 문의 데이터 (Request Body)
     * @return ResponseEntity<Inquiry> - 저장된 문의 데이터
     */
    @PostMapping
    public ResponseEntity<Inquiry> createInquiry(@RequestBody Inquiry inquiry) {
        Inquiry savedInquiry = inquiryService.saveInquiry(inquiry);
        return ResponseEntity.status(201).body(savedInquiry); // 201 Created
    }

    /**
     * 특정 문의 상태 업데이트
     *
     * @param id 문의 ID (Path Variable)
     * @param status 업데이트할 상태 (Query Parameter)
     * @return ResponseEntity<Inquiry> - 업데이트된 문의 데이터
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Inquiry> updateInquiryStatus(
            @PathVariable("id") Long id,
            @RequestParam("status") InquiryStatus status
    ) {
        Inquiry updatedInquiry = inquiryService.updateInquiryStatus(id, status);
        return ResponseEntity.ok(updatedInquiry);
    }

    /**
     * 특정 문의 삭제
     *
     * @param id 삭제할 문의 ID (Path Variable)
     * @return ResponseEntity<Void> - 본문 없이 HTTP 상태 코드만 반환
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInquiry(@PathVariable("id") Long id) {
        inquiryService.deleteInquiry(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
