package com.ohgiraffers.recipeapp.dto;

import com.ohgiraffers.recipeapp.entity.Ingredient;
import com.ohgiraffers.recipeapp.entity.RefrigeratorIngredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefrigeratorIngredientDTO {
    private Long ingredientId;      // 재료 ID (옵션)
    private String ingredientName;  // 재료 이름
    private Long memberId;          // 회원 ID
    private String quantity;        // 재료 수량
    private String status;          // 재료 상태 (ENUM: Unused, Used 등)
    private LocalDate expirationDate; // 유통기한

    /**
     * RefrigeratorIngredient 엔티티를 DTO로 변환하는 메서드
     *
     * @param entity RefrigeratorIngredient 엔티티
     * @return RefrigeratorIngredientDTO
     */
    public static RefrigeratorIngredientDTO fromEntity(RefrigeratorIngredient entity) {
        Ingredient ingredient = entity.getIngredient();

        return RefrigeratorIngredientDTO.builder()
                .ingredientId(ingredient.getId())
                .ingredientName(ingredient.getName())
                .memberId(entity.getMember().getId())
                .quantity(entity.getQuantity())
                .status(entity.getStatus().name())
                .expirationDate(entity.getExpirationDate())
                .build();
    }
}

