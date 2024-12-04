package com.ohgiraffers.recipeapp.repository;

import com.ohgiraffers.recipeapp.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    /**
     * 특정 회원의 북마크 목록 조회
     * @param memberId 회원 ID
     * @return List<Bookmark> - 해당 회원의 북마크 목록
     */
    List<Bookmark> findByMemberId(Long memberId);

    /**
     * 특정 회원이 특정 레시피를 북마크했는지 확인
     * @param memberId 회원 ID
     * @param recipeId 레시피 ID
     * @return boolean - 북마크 여부
     */
    boolean existsByMemberIdAndRecipeId(Long memberId, Long recipeId);

    /**
     * 특정 회원과 레시피로 북마크 조회
     * @param memberId 회원 ID
     * @param recipeId 레시피 ID
     * @return Bookmark - 북마크 데이터
     */
    Bookmark findByMemberIdAndRecipeId(Long memberId, Long recipeId);
}



