package com.ohgiraffers.recipeapp.repository;

import com.ohgiraffers.recipeapp.entity.CookingStepImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CookingStepImageRepository extends JpaRepository<CookingStepImage, Long> {
}
