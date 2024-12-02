package com.ohgiraffers.recipeapp.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CookingStepDTO {
    private Long id; // 조리 단계 ID
    private Long recipeId; // 레시피 ID
    private Long stepImageId; // 이미지 ID
    private String description; // 조리 단계 설명
    private int stepNumber; // 조리 단계 순서
}
