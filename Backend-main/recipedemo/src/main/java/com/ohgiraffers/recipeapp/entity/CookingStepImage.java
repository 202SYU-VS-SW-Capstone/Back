package com.ohgiraffers.recipeapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "cooking_step_images")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CookingStepImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cooking_step_image_id", nullable = false, unique = true) // 명시적으로 컬럼 매핑
    private Long id; // Primary Key

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "upload_date")
    private LocalDate uploadDate;

    @Column(name = "url")
    private String url;

    @Column(name = "uuid")
    private String uuid;
}


