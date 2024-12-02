package com.ohgiraffers.recipeapp.service;

import com.ohgiraffers.recipeapp.dto.RefrigeratorIngredientDTO;
import com.ohgiraffers.recipeapp.entity.Ingredient;
import com.ohgiraffers.recipeapp.entity.Member;
import com.ohgiraffers.recipeapp.entity.RefrigeratorIngredient;
import com.ohgiraffers.recipeapp.enums.IngredientStatus;
import com.ohgiraffers.recipeapp.keys.RefrigeratorIngredientId;
import com.ohgiraffers.recipeapp.repository.IngredientRepository;
import com.ohgiraffers.recipeapp.repository.MemberRepository;
import com.ohgiraffers.recipeapp.repository.RefrigeratorIngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefrigeratorIngredientService {

    private final RefrigeratorIngredientRepository repository;
    private final IngredientRepository ingredientRepository;
    private final MemberRepository memberRepository;

    public RefrigeratorIngredientService(
            RefrigeratorIngredientRepository repository,
            IngredientRepository ingredientRepository,
            MemberRepository memberRepository
    ) {
        this.repository = repository;
        this.ingredientRepository = ingredientRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * ID로 냉장고 재료 조회
     *
     * @param ingredientId 재료 ID
     * @param memberId 회원 ID
     * @return RefrigeratorIngredient
     */
    public RefrigeratorIngredient getById(Long ingredientId, Long memberId) {
        return repository.findById(new RefrigeratorIngredientId(ingredientId, memberId))
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found for given ID and member"));
    }

    /**
     * 재료 이름으로 냉장고 재료 조회
     *
     * @param ingredientName 재료 이름
     * @param memberId 회원 ID
     * @return RefrigeratorIngredient
     */
    public List<RefrigeratorIngredient> getByName(String ingredientName, Long memberId) {
        return repository.findByIngredientNameAndMemberId(ingredientName, memberId);
    }

    /**
     * 냉장고 재료 추가
     *
     * @param dto RefrigeratorIngredientDTO
     * @return RefrigeratorIngredient
     */
    public RefrigeratorIngredient addIngredient(RefrigeratorIngredientDTO dto) {
        RefrigeratorIngredient ingredient = mapToEntity(dto);
        return repository.save(ingredient);
    }

    /**
     * 냉장고 재료 수정
     *
     * @param dto RefrigeratorIngredientDTO
     * @return RefrigeratorIngredient
     */
    public RefrigeratorIngredient updateIngredient(RefrigeratorIngredientDTO dto) {
        RefrigeratorIngredient existing = repository.findById(new RefrigeratorIngredientId(dto.getIngredientId(), dto.getMemberId()))
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found for update"));

        // 수정 가능한 필드 업데이트
        existing.setQuantity(dto.getQuantity());
        existing.setStatus(IngredientStatus.valueOf(dto.getStatus()));
        existing.setExpirationDate(dto.getExpirationDate());

        return repository.save(existing);
    }

    /**
     * 냉장고 재료 삭제
     *
     * @param ingredientId 재료 ID
     * @param memberId 회원 ID
     */
    public void deleteIngredient(Long ingredientId, Long memberId) {
        repository.deleteById(new RefrigeratorIngredientId(ingredientId, memberId));
    }

    /**
     * DTO를 Entity로 변환
     *
     * 이 메서드는 DTO 객체를 Entity 객체로 변환하는 역할을 합니다.
     * 주어진 DTO에서 재료(Ingredient)와 회원(Member) 정보를 가져오고,
     * RefrigeratorIngredient 엔티티를 생성합니다.
     *
     * @param dto RefrigeratorIngredientDTO - 변환할 DTO 객체
     * @return RefrigeratorIngredient - 변환된 Entity 객체
     */
    private RefrigeratorIngredient mapToEntity(RefrigeratorIngredientDTO dto) {
        Ingredient ingredient;
        if (dto.getIngredientId() != null) {
            ingredient = ingredientRepository.findById(dto.getIngredientId())
                    .orElseThrow(() -> new IllegalArgumentException("Ingredient not found with ID: " + dto.getIngredientId()));
        } else if (dto.getIngredientName() != null) {
            List<Ingredient> ingredients = ingredientRepository.findByNameContainingIgnoreCase(dto.getIngredientName());
            if (ingredients.isEmpty()) {
                throw new IllegalArgumentException("No ingredients found containing name: " + dto.getIngredientName());
            } else if (ingredients.size() > 1) {
                throw new IllegalArgumentException("Multiple ingredients found for name: " + dto.getIngredientName() + ". Be more specific.");
            }
            ingredient = ingredients.get(0); // 정확히 하나일 경우 가져옴
        } else {
            throw new IllegalArgumentException("Ingredient ID or Name must be provided");
        }

        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + dto.getMemberId()));

        return RefrigeratorIngredient.builder()
                .ingredient(ingredient)
                .member(member)
                .quantity(dto.getQuantity())
                .status(IngredientStatus.valueOf(dto.getStatus()))
                .expirationDate(dto.getExpirationDate())
                .build();
    }
}

