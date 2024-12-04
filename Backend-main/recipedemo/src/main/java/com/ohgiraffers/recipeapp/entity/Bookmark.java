package com.ohgiraffers.recipeapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "bookmarks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bookmark { // 북마크 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id; // 북마크 ID

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 북마크한 사용자

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe; // 북마크된 레시피

    @Column(name = "saved_at", nullable = false)
    private LocalDate savedAt; // 북마크 저장 날짜
}

