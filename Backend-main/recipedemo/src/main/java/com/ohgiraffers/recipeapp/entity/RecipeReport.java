package com.ohgiraffers.recipeapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "recipe_reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Report_id")
    private Long id; // 신고 ID

    @ManyToOne
    @JoinColumn(name = "reporter_member_id", nullable = false)
    private Member reporter; // 신고한 회원

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe; // 신고된 레시피

    @Column(nullable = false, columnDefinition = "TEXT")
    private String reportTitle; // 신고 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String reportContent; // 신고 내용

    @Column(nullable = false, name = "reported_at")
    private LocalDate reportedAt; // 신고 날짜
}
