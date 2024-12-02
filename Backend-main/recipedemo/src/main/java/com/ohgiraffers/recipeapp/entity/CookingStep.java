package com.ohgiraffers.recipeapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cooking_steps")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CookingStep {  // 조리 단계 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "step_id")
    private Long id; // 조리 단계 ID

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe; // 조리 단계가 속한 레시피

    @ManyToOne(fetch = FetchType.LAZY) // Lazy 로딩 적용
    @JoinColumn(name = "step_image_id", nullable = true)
    private CookingStepImage stepImage; // 조리 단계와 연결된 이미지

    @Column(nullable = false)
    private String description; // 조리 단계 설명

    @Column(name = "step_number", nullable = false)
    private int stepNumber; // 조리 단계 순서
}

