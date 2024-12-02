package com.ohgiraffers.recipeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {
    private Long id; // 레시피 ID
    private String description; // 레시피 설명
    private Integer cookingTime; // 조리 시간 (분 단위)
    private Integer servings; // 인분 수
    private String authorName; // 작성자 이름
    private String finalImageUrl; // 최종 이미지 URL
    private String cookingProcessVideoLink; // 조리 과정 동영상 링크
    private Integer views; // 조회수
    private Float rating; // 평점
    private Integer bookmarksCount; // 북마크 수
}


