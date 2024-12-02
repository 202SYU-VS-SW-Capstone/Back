package com.ohgiraffers.recipeapp.controller;

import com.ohgiraffers.recipeapp.dto.RefrigeratorIngredientDTO;
import com.ohgiraffers.recipeapp.service.RefrigeratorIngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/refrigerator-ingredients")
public class RefrigeratorIngredientController {

    private final RefrigeratorIngredientService service;

    public RefrigeratorIngredientController(RefrigeratorIngredientService service) {
        this.service = service;
    }

    /**
     * ID로 냉장고 재료 조회
     *
     * @param ingredientId 재료 ID
     * @param memberId 회원 ID
     * @return RefrigeratorIngredientDTO
     */
    @GetMapping("/{ingredientId}/{memberId}")
    public ResponseEntity<RefrigeratorIngredientDTO> getById(
            @PathVariable("ingredientId") Long ingredientId,
            @PathVariable("memberId") Long memberId
    ) {
        return ResponseEntity.ok(
                RefrigeratorIngredientDTO.fromEntity(service.getById(ingredientId, memberId))
        );
    }

    /**
     * 이름으로 냉장고 재료 조회
     *
     * @param ingredientName 재료 이름
     * @param memberId 회원 ID
     * @return List<RefrigeratorIngredientDTO>
     */
    @GetMapping("/search")
    public ResponseEntity<List<RefrigeratorIngredientDTO>> getByName(
            @RequestParam(name = "ingredientName") String ingredientName,
            @RequestParam("memberId") Long memberId
    ) {
        List<RefrigeratorIngredientDTO> ingredients = service.getByName(ingredientName, memberId).stream()
                .map(RefrigeratorIngredientDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ingredients);
    }

    /**
     * 냉장고 재료 추가
     *
     * @param dto RefrigeratorIngredientDTO
     * @return RefrigeratorIngredientDTO
     */
    @PostMapping
    public ResponseEntity<RefrigeratorIngredientDTO> addIngredient(@RequestBody RefrigeratorIngredientDTO dto) {
        return ResponseEntity.ok(
                RefrigeratorIngredientDTO.fromEntity(service.addIngredient(dto))
        );
    }

    /**
     * 냉장고 재료 수정
     *
     * @param dto RefrigeratorIngredientDTO
     * @return RefrigeratorIngredientDTO
     */
    @PutMapping
    public ResponseEntity<RefrigeratorIngredientDTO> updateIngredient(@RequestBody RefrigeratorIngredientDTO dto) {
        return ResponseEntity.ok(
                RefrigeratorIngredientDTO.fromEntity(service.updateIngredient(dto))
        );
    }

    /**
     * 냉장고 재료 삭제
     *
     * @param ingredientId 재료 ID
     * @param memberId 회원 ID
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("/{ingredientId}/{memberId}")
    public ResponseEntity<Void> deleteIngredient(
            @PathVariable("ingredientId") Long ingredientId,
            @PathVariable("memberId") Long memberId
    ) {
        service.deleteIngredient(ingredientId, memberId);
        return ResponseEntity.noContent().build();
    }
}
