package com.ohgiraffers.recipeapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "notices")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id; // 공지사항 ID

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 작성자 (회원 테이블 참조)

    @Column(nullable = false)
    private String title; // 공지 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 공지 내용

    @ManyToOne
    @JoinColumn(name = "notice_image_id")
    private NoticeImage noticeImage; // 공지 이미지 (이미지 테이블 참조)

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt; // 공지 작성 날짜
}

