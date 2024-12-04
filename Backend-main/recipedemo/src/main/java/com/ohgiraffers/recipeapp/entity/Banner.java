package com.ohgiraffers.recipeapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "banners")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banner_id")
    private Long id; // 배너 ID

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 배너 생성자 (회원)

    @ManyToOne
    @JoinColumn(name = "notice_id", nullable = false)
    private Notice notice; // 연결된 공지사항

    @ManyToOne
    @JoinColumn(name = "banner_image_id", nullable = false)
    private BannerImage bannerImage; // 배너 이미지 (이미지 테이블의 외래키)

    @Column(nullable = false, name = "created_at")
    private LocalDate createdAt; // 배너 생성 날짜
}

