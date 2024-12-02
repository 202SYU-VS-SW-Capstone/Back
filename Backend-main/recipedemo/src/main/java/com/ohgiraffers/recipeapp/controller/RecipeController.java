package com.ohgiraffers.recipeapp.controller;

import com.ohgiraffers.recipeapp.entity.Recipe;
import com.ohgiraffers.recipeapp.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * 모든 레시피 조회
     *
     * @return ResponseEntity<List<Recipe>> - 모든 레시피 목록과 HTTP 상태 코드
     */
    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }

    /**
     * ID로 특정 레시피 조회
     *
     * @param id 레시피 ID (Path Variable)
     * @return ResponseEntity<Recipe> - 조회된 레시피 데이터와 HTTP 상태 코드
     */
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable("id") Long id) {
        Recipe recipe = recipeService.getRecipeById(id);
        return ResponseEntity.ok(recipe);
    }

    /**
     * 특정 작성자의 레시피 목록 조회
     *
     * @param authorId 작성자 ID (Query Parameter)
     * @return ResponseEntity<List<Recipe>> - 해당 작성자의 레시피 목록과 HTTP 상태 코드
     */
    @GetMapping("/author")
    public ResponseEntity<List<Recipe>> getRecipesByAuthor(@RequestParam(name = "authorId") Long authorId) {
        List<Recipe> recipes = recipeService.getRecipesByAuthor(authorId);
        return ResponseEntity.ok(recipes);
    }

    /**
     * 새로운 레시피 추가
     *
     * @param recipe 저장할 레시피 데이터 (Request Body)
     * @return ResponseEntity<Recipe> - 저장된 레시피 데이터와 HTTP 상태 코드
     */
    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        Recipe savedRecipe = recipeService.saveRecipe(recipe);
        return ResponseEntity.status(201).body(savedRecipe); // 201 Created
    }

    /**
     * 특정 레시피 수정
     *
     * @param id 수정할 레시피 ID (Path Variable)
     * @param updatedRecipe 수정할 레시피 데이터 (Request Body)
     * @return ResponseEntity<Recipe> - 수정된 레시피 데이터와 HTTP 상태 코드
     */
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(
            @PathVariable("id") Long id,
            @RequestBody Recipe updatedRecipe
    ) {
        Recipe recipe = recipeService.updateRecipe(id, updatedRecipe);
        return ResponseEntity.ok(recipe);
    }

    /**
     * 특정 레시피 삭제
     *
     * @param id 삭제할 레시피 ID (Path Variable)
     * @return ResponseEntity<Void> - 본문 없이 HTTP 상태 코드만 반환
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable("id") Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    /**
     * 제목으로 레시피 검색
     *
     * @param title 레시피 제목 (Query Parameter)
     * @return ResponseEntity<List<Recipe>> - 제목에 해당 문자열이 포함된 레시피 목록과 HTTP 상태 코드
     */
    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> searchRecipesByTitle(@RequestParam(name = "title") String title) {
        List<Recipe> recipes = recipeService.searchRecipesByTitle(title);
        return ResponseEntity.ok(recipes);
    }

    /**
     * 조회수 기준으로 레시피 정렬
     *
     * @return ResponseEntity<List<Recipe>> - 조회수 순으로 정렬된 레시피 목록과 HTTP 상태 코드
     */
    @GetMapping("/popular")
    public ResponseEntity<List<Recipe>> getRecipesByPopularity() {
        List<Recipe> recipes = recipeService.getRecipesByPopularity();
        return ResponseEntity.ok(recipes);
    }
}

