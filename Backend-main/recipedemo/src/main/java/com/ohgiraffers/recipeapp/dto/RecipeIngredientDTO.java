package com.ohgiraffers.recipeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RecipeIngredientDTO - 클라이언트와 데이터 교환을 위한 DTO 클래스
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeIngredientDTO {
    private Long ingredientId; // 재료 ID
    private Long recipeId;     // 레시피 ID
    private String ingredientName; // 재료 이름
    private String quantity;       // 재료 양
}
