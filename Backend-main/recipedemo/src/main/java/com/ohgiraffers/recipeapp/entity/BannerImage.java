package com.ohgiraffers.recipeapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "banner_images")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BannerImage {  // 배너 이미지 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banner_image_id")
    private Long id; // 배너 이미지 ID

    @Column(nullable = false)
    private String uuid; // UUID

    @Column(nullable = false)
    private String originalName; // 원본 파일명

    @Column(nullable = false)
    private String fileName; // 저장된 파일명

    @Column(nullable = false)
    private String url; // 이미지 URL

    @Column(nullable = false, name = "upload_date")
    private LocalDate uploadDate; // 업로드 날짜
}


