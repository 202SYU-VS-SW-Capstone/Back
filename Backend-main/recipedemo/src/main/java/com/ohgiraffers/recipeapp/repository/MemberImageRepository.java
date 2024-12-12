package com.ohgiraffers.recipeapp.repository;

import com.ohgiraffers.recipeapp.entity.MemberImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberImageRepository extends JpaRepository<MemberImage, Long> {
}
