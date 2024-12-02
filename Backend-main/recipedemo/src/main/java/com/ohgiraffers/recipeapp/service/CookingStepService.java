package com.ohgiraffers.recipeapp.service;

import com.ohgiraffers.recipeapp.dto.CookingStepDTO;
import com.ohgiraffers.recipeapp.entity.CookingStep;
import com.ohgiraffers.recipeapp.entity.CookingStepImage;
import com.ohgiraffers.recipeapp.entity.Recipe;
import com.ohgiraffers.recipeapp.repository.CookingStepRepository;
import com.ohgiraffers.recipeapp.repository.CookingStepImageRepository;
import com.ohgiraffers.recipeapp.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CookingStepService {

    private final CookingStepRepository cookingStepRepository;
    private final RecipeRepository recipeRepository;
    private final CookingStepImageRepository cookingStepImageRepository;

    public CookingStepService(
            CookingStepRepository cookingStepRepository,
            RecipeRepository recipeRepository,
            CookingStepImageRepository cookingStepImageRepository) {
        this.cookingStepRepository = cookingStepRepository;
        this.recipeRepository = recipeRepository;
        this.cookingStepImageRepository = cookingStepImageRepository;
    }

    /**
     * 특정 레시피의 모든 조리 단계 조회
     *
     * @param recipeId 레시피 ID
     * @return List<CookingStepDTO> - 해당 레시피의 조리 단계 목록
     */
    public List<CookingStepDTO> getCookingStepsByRecipe(Long recipeId) {
        return cookingStepRepository.findByRecipeIdOrderByStepNumberAsc(recipeId)
                .stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 새로운 조리 단계 추가
     *
     * @param dto CookingStepDTO
     * @return CookingStepDTO - 저장된 조리 단계
     */
    public CookingStepDTO addCookingStep(CookingStepDTO dto) {
        CookingStep cookingStep = toEntity(dto);
        return fromEntity(cookingStepRepository.save(cookingStep));
    }

    /**
     * 특정 조리 단계 수정
     *
     * @param id 수정할 조리 단계 ID
     * @param dto CookingStepDTO
     * @return CookingStepDTO - 수정된 조리 단계
     */
    public CookingStepDTO updateCookingStep(Long id, CookingStepDTO dto) {
        CookingStep existingStep = cookingStepRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("CookingStep not found with id: " + id));
        existingStep.setDescription(dto.getDescription());
        existingStep.setStepNumber(dto.getStepNumber());
        existingStep.setStepImage(dto.getStepImageId() != null
                ? cookingStepImageRepository.findById(dto.getStepImageId())
                .orElse(null) : null);
        return fromEntity(cookingStepRepository.save(existingStep));
    }

    /**
     * 특정 조리 단계 삭제
     *
     * @param id 삭제할 조리 단계 ID
     */
    public void deleteCookingStep(Long id) {
        cookingStepRepository.deleteById(id);
    }

    /**
     * Entity -> DTO 변환
     *
     * @param entity CookingStep
     * @return CookingStepDTO
     */
    private CookingStepDTO fromEntity(CookingStep entity) {
        return CookingStepDTO.builder()
                .id(entity.getId())
                .recipeId(entity.getRecipe().getId())
                .stepImageId(entity.getStepImage() != null ? entity.getStepImage().getId() : null)
                .description(entity.getDescription())
                .stepNumber(entity.getStepNumber())
                .build();
    }

    /**
     * DTO -> Entity 변환
     *
     * @param dto CookingStepDTO
     * @return CookingStep
     */
    private CookingStep toEntity(CookingStepDTO dto) {
        Recipe recipe = recipeRepository.findById(dto.getRecipeId())
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found with id: " + dto.getRecipeId()));
        CookingStepImage stepImage = dto.getStepImageId() != null
                ? cookingStepImageRepository.findById(dto.getStepImageId()).orElse(null)
                : null;

        return CookingStep.builder()
                .recipe(recipe)
                .stepImage(stepImage)
                .description(dto.getDescription())
                .stepNumber(dto.getStepNumber())
                .build();
    }
}
