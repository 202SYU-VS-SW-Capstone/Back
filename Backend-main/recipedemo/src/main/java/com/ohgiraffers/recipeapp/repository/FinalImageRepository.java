package com.ohgiraffers.recipeapp.repository;

import com.ohgiraffers.recipeapp.entity.FinalImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinalImageRepository extends JpaRepository<FinalImage, Long> {
}
