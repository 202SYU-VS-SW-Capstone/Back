package com.ohgiraffers.recipeapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "recipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id") // 테이블의 컬럼명에 매핑
    private Long id; // 레시피 ID

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Member author; // 레시피 작성자

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description; // 레시피 설명

    @Column(name = "cooking_time")
    private Integer cookingTime; // 조리 시간 (분 단위)

    private Integer servings; // 인분 수

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt; // 생성 날짜

    @Column(nullable = false)
    private Integer views = 0; // 조회수 (기본값 0)

    private Float rating = 0f; // 평점 (기본값 0)

    @Column(name = "bookmarks_count", nullable = false)
    private Integer bookmarksCount = 0; // 북마크 수 (기본값 0)

    @ManyToOne
    @JoinColumn(name = "final_image_id")
    private FinalImage finalImage; // 최종 레시피 이미지

    @Column(name = "cooking_process_video_link")
    private String cookingProcessVideoLink; // 조리 과정 동영상 링크
}
