package com.ohgiraffers.recipeapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "notice_images")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeImage { // 공지사항 이미지 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_image_id") // 테이블의 기본 키 컬럼
    private Long id;

    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "original_name", nullable = false) // 원본 파일 이름
    private String originalName;

    @Column(name = "file_name", nullable = false) // 서버에 저장된 파일 이름
    private String fileName;

    @Column(name = "url", nullable = false) // 파일 접근 URL
    private String url;

    @Column(name = "upload_date", nullable = false) // 파일 업로드 날짜
    private LocalDate uploadDate;
}
