package com.ohgiraffers.recipeapp.repository;

import com.ohgiraffers.recipeapp.entity.BannerImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerImageRepository extends JpaRepository<BannerImage, Long> {
}
