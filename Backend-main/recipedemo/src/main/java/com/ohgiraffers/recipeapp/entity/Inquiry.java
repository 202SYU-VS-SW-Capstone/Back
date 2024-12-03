package com.ohgiraffers.recipeapp.entity;

import com.ohgiraffers.recipeapp.enums.InquiryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "inquiries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id")
    private Long id; // 문의 ID

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 회원 정보

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 문의 내용

    @Column(nullable = false, name = "created_at")
    private LocalDate createdAt; // 문의 생성 날짜

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('Incomplete', 'Complete')")
    private InquiryStatus status = InquiryStatus.Incomplete; // 문의 상태
}
