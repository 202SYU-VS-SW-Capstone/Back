package com.ohgiraffers.recipeapp.entity;

import com.ohgiraffers.recipeapp.enums.IngredientStatus;
import com.ohgiraffers.recipeapp.keys.RefrigeratorIngredientId;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "refrigerator_ingredients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(RefrigeratorIngredientId.class) // 복합 키를 사용하기 위한 설정
public class RefrigeratorIngredient {

    @Id
    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient; // 재료 ID와 연결

    @Id
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 회원 ID와 연결

    @Column(nullable = false)
    private String quantity; // 재료 수량

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IngredientStatus status = IngredientStatus.Unused; // 재료 상태

    @Column(name = "expiration_date")
    private LocalDate expirationDate; // 재료의 유통기한
}
