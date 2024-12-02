package com.ohgiraffers.recipeapp.repository;

import com.ohgiraffers.recipeapp.entity.RefrigeratorIngredient;
import com.ohgiraffers.recipeapp.keys.RefrigeratorIngredientId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefrigeratorIngredientRepository extends JpaRepository<RefrigeratorIngredient, RefrigeratorIngredientId> {

    /**
     * 특정 회원의 냉장고 재료 목록 조회
     *
     * @param memberId 회원 ID
     * @return List<RefrigeratorIngredient> - 해당 회원의 냉장고 재료 목록
     */
    List<RefrigeratorIngredient> findByMemberId(Long memberId);

    /**
     * 특정 회원의 재료 이름으로 냉장고 재료 조회
     *
     * @param ingredientName 재료 이름
     * @param memberId 회원 ID
     * @return List<RefrigeratorIngredient> - 특정 이름과 회원 ID에 해당하는 재료 목록
     */
    List<RefrigeratorIngredient> findByIngredientNameAndMemberId(String ingredientName, Long memberId);
}
