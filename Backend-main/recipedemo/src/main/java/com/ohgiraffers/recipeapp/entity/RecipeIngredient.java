package com.ohgiraffers.recipeapp.entity;

import com.ohgiraffers.recipeapp.keys.RecipeIngredientId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recipe_ingredients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(RecipeIngredientId.class) // 복합 키 클래스 설정
public class RecipeIngredient { // 레시피 포함 재료 테이블

    @Id
    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false) // 재료 ID (외래 키)
    private Ingredient ingredient;

    @Id
    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false) // 레시피 ID (외래 키)
    private Recipe recipe;

    @Column(name = "ingredient_name", nullable = false) // 재료 이름
    private String ingredientName;

    @Column(name = "quantity") // 재료 양 (nullable 아님)
    private String quantity;
}
