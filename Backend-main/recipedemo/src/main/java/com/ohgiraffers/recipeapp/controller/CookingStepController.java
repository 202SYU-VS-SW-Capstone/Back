package com.ohgiraffers.recipeapp.controller;

import com.ohgiraffers.recipeapp.dto.CookingStepDTO;
import com.ohgiraffers.recipeapp.service.CookingStepService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cooking-steps")
public class CookingStepController {

    private final CookingStepService service;

    public CookingStepController(CookingStepService service) {
        this.service = service;
    }

    /**
     * 레시피 ID로 조리 단계 목록 조회
     *
     * @param recipeId 레시피 ID
     * @return ResponseEntity<List<CookingStepDTO>> - 조리 단계 목록
     */
    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<List<CookingStepDTO>> getStepsByRecipe(
            @PathVariable(name = "recipeId") Long recipeId
    ) {
        List<CookingStepDTO> steps = service.getCookingStepsByRecipe(recipeId);
        return ResponseEntity.ok(steps);
    }

    /**
     * 새로운 조리 단계 추가
     *
     * @param dto CookingStepDTO
     * @return ResponseEntity<CookingStepDTO>
     */
    @PostMapping
    public ResponseEntity<CookingStepDTO> addCookingStep(@RequestBody CookingStepDTO dto) {
        CookingStepDTO createdStep = service.addCookingStep(dto);
        return ResponseEntity.ok(createdStep);
    }

    /**
     * 특정 조리 단계 수정
     *
     * @param stepId 조리 단계 ID
     * @param dto CookingStepDTO
     * @return ResponseEntity<CookingStepDTO>
     */
    @PutMapping("/{stepId}")
    public ResponseEntity<CookingStepDTO> updateCookingStep(
            @PathVariable(name = "stepId") Long stepId,
            @RequestBody CookingStepDTO dto
    ) {
        CookingStepDTO updatedStep = service.updateCookingStep(stepId, dto);
        return ResponseEntity.ok(updatedStep);
    }

    /**
     * 특정 조리 단계 삭제
     *
     * @param stepId 조리 단계 ID
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("/{stepId}")
    public ResponseEntity<Void> deleteCookingStep(
            @PathVariable(name = "stepId") Long stepId
    ) {
        service.deleteCookingStep(stepId);
        return ResponseEntity.noContent().build();
    }
}
