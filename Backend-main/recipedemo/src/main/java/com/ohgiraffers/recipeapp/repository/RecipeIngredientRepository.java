package com.ohgiraffers.recipeapp.repository;

import com.ohgiraffers.recipeapp.entity.RecipeIngredient;
import com.ohgiraffers.recipeapp.keys.RecipeIngredientId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, RecipeIngredientId> {

    /**
     * 특정 레시피의 재료 목록 조회
     * @param recipeId 레시피 ID
     * @return List<RecipeIngredient> - 해당 레시피의 재료 목록
     */
    List<RecipeIngredient> findByRecipeId(Long recipeId);
}

