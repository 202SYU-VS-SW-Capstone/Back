package com.ohgiraffers.recipeapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "final_images")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinalImage {   // 요리 완성 이미지 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "final_image_id")
    private Long id; // 기본 키

    @Column(nullable = false, unique = true)
    private String uuid; // UUID

    @Column(name = "original_name", nullable = false)
    private String originalName; // 원본 파일 이름

    @Column(name = "file_name", nullable = false)
    private String fileName; // 저장된 파일 이름

    @Column(nullable = false)
    private String url; // 파일 URL

    @Column(name = "upload_date", nullable = false)
    private LocalDate uploadDate; // 업로드 날짜
}


