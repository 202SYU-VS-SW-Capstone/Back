package com.ohgiraffers.recipeapp.repository;

import com.ohgiraffers.recipeapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 특정 레시피의 댓글 조회
     * @param recipeId 레시피 ID
     * @return List<Comment> - 해당 레시피에 속한 댓글 목록
     */
    List<Comment> findByRecipeId(Long recipeId);

    /**
     * 특정 회원이 작성한 모든 댓글 조회
     *
     * @param memberId 회원 엔티티
     * @return List<Comment> - 해당 회원이 작성한 댓글 목록
     */
    List<Comment> findByAuthorId(Long memberId);
}
