package com.ohgiraffers.recipeapp.controller;

import com.ohgiraffers.recipeapp.dto.RecipeIngredientDTO;
import com.ohgiraffers.recipeapp.service.RecipeIngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe-ingredients")
public class RecipeIngredientController {

    private final RecipeIngredientService recipeIngredientService;

    // RecipeIngredientService 를 생성자로 주입
    public RecipeIngredientController(RecipeIngredientService recipeIngredientService) {
        this.recipeIngredientService = recipeIngredientService;
    }

    /**
     * 특정 레시피의 재료 목록 조회
     *
     * @param recipeId 레시피 ID (Query Parameter)
     * @return ResponseEntity<List<RecipeIngredientDTO>> - 해당 레시피의 재료 목록과 HTTP 상태 코드
     */
    @GetMapping
    public ResponseEntity<List<RecipeIngredientDTO>> getIngredientsByRecipe(
            @RequestParam("recipeId") Long recipeId) {
        List<RecipeIngredientDTO> ingredients = recipeIngredientService.getIngredientsByRecipe(recipeId);
        return ResponseEntity.ok(ingredients);
    }

    /**
     * 새로운 재료 추가
     *
     * @param recipeIngredientDTO 저장할 재료 데이터 (Request Body)
     * @return ResponseEntity<RecipeIngredientDTO> - 저장된 재료 데이터와 HTTP 상태 코드
     */
    @PostMapping
    public ResponseEntity<RecipeIngredientDTO> createRecipeIngredient(
            @RequestBody RecipeIngredientDTO recipeIngredientDTO) {
        RecipeIngredientDTO savedIngredient = recipeIngredientService.saveRecipeIngredient(recipeIngredientDTO);
        return ResponseEntity.status(201).body(savedIngredient); // 201 Created
    }

    /**
     * 특정 재료 삭제
     *
     * @param ingredientId 재료 ID (Path Variable)
     * @param recipeId 레시피 ID (Path Variable)
     * @return ResponseEntity<Void> - 본문 없이 HTTP 상태 코드만 반환
     */
    @DeleteMapping("/{ingredientId}/{recipeId}")
    public ResponseEntity<Void> deleteRecipeIngredient(
            @PathVariable("ingredientId") Long ingredientId,
            @PathVariable("recipeId") Long recipeId) {
        recipeIngredientService.deleteRecipeIngredient(ingredientId, recipeId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
