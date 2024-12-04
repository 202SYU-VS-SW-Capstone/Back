package com.ohgiraffers.recipeapp.service;

import com.ohgiraffers.recipeapp.dto.RecipeIngredientDTO;
import com.ohgiraffers.recipeapp.entity.Ingredient;
import com.ohgiraffers.recipeapp.entity.Recipe;
import com.ohgiraffers.recipeapp.entity.RecipeIngredient;
import com.ohgiraffers.recipeapp.keys.RecipeIngredientId;
import com.ohgiraffers.recipeapp.repository.IngredientRepository;
import com.ohgiraffers.recipeapp.repository.RecipeIngredientRepository;
import com.ohgiraffers.recipeapp.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;

    public RecipeIngredientService(
            RecipeIngredientRepository recipeIngredientRepository,
            IngredientRepository ingredientRepository,
            RecipeRepository recipeRepository) {
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
    }

    /**
     * 특정 레시피의 재료 목록 조회
     *
     * @param recipeId 레시피 ID
     * @return List<RecipeIngredientDTO> - 해당 레시피의 재료 목록
     */
    public List<RecipeIngredientDTO> getIngredientsByRecipe(Long recipeId) {
        List<RecipeIngredient> ingredients = recipeIngredientRepository.findByRecipeId(recipeId);
        return ingredients.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * 새로운 재료 추가
     *
     * @param dto RecipeIngredientDTO
     * @return RecipeIngredientDTO - 저장된 재료 데이터
     */
    public RecipeIngredientDTO saveRecipeIngredient(RecipeIngredientDTO dto) {
        RecipeIngredient ingredient = mapToEntity(dto);
        RecipeIngredient savedIngredient = recipeIngredientRepository.save(ingredient);
        return mapToDTO(savedIngredient);
    }

    /**
     * 특정 재료 삭제
     *
     * @param ingredientId 재료 ID
     * @param recipeId 레시피 ID
     */
    public void deleteRecipeIngredient(Long ingredientId, Long recipeId) {
        recipeIngredientRepository.deleteById(new RecipeIngredientId(ingredientId, recipeId));
    }

    /**
     * DTO를 Entity로 변환
     *
     * @param dto RecipeIngredientDTO
     * @return RecipeIngredient
     */
    private RecipeIngredient mapToEntity(RecipeIngredientDTO dto) {
        Ingredient ingredient = ingredientRepository.findById(dto.getIngredientId())
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found with ID: " + dto.getIngredientId()));

        Recipe recipe = recipeRepository.findById(dto.getRecipeId())
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found with ID: " + dto.getRecipeId()));

        return RecipeIngredient.builder()
                .ingredient(ingredient)
                .recipe(recipe)
                .ingredientName(dto.getIngredientName())
                .quantity(dto.getQuantity())
                .build();
    }

    /**
     * Entity를 DTO로 변환
     *
     * @param entity RecipeIngredient
     * @return RecipeIngredientDTO
     */
    private RecipeIngredientDTO mapToDTO(RecipeIngredient entity) {
        return RecipeIngredientDTO.builder()
                .ingredientId(entity.getIngredient().getId())
                .recipeId(entity.getRecipe().getId())
                .ingredientName(entity.getIngredientName())
                .quantity(entity.getQuantity())
                .build();
    }
}
