package com.ohgiraffers.recipeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDTO {
    private Long id; // 재료 ID
    private String name; // 재료 이름
    private String subCategoryName; // 소분류 이름
    private Long subCategoryId; // 소분류 ID
    private String majorCategoryName; // 대분류 이름
    private String ingredientImageUrl; // 이미지 URL
}

