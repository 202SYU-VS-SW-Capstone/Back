package com.ohgiraffers.recipeapp.service;

import com.ohgiraffers.recipeapp.entity.Comment;
import com.ohgiraffers.recipeapp.repository.CommentRepository;
import com.ohgiraffers.recipeapp.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    public CommentService(CommentRepository commentRepository, MemberRepository memberRepository) {
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * 모든 댓글 조회
     *
     * @return List<Comment> - 모든 댓글 목록
     */
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    /**
     * ID로 특정 댓글 조회
     *
     * @param id 댓글 ID
     * @return Comment - 조회된 댓글 데이터
     * @throws IllegalArgumentException - 해당 ID의 댓글이 없을 경우 예외 발생
     */
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + id));
    }

    /**
     * 특정 레시피의 댓글 조회
     *
     * @param recipeId 레시피 ID
     * @return List<Comment> - 해당 레시피에 속한 댓글 목록
     */
    public List<Comment> getCommentsByRecipe(Long recipeId) {
        return commentRepository.findByRecipeId(recipeId);
    }

    /**
     * 특정 회원이 작성한 모든 댓글 조회
     *
     * @param memberId 회원 ID
     * @return List<Comment> - 해당 회원이 작성한 댓글 목록
     */
    public List<Comment> getCommentsByMember(Long memberId) {
        // 회원 ID를 기반으로 댓글 조회
        return commentRepository.findByAuthorId(memberId);
    }

    /**
     * 새로운 댓글 저장
     *
     * @param comment 저장할 댓글 데이터
     * @return Comment - 저장된 댓글 데이터
     */
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    /**
     * 특정 댓글 수정
     *
     * @param id 수정할 댓글 ID
     * @param updatedComment 수정할 댓글 데이터
     * @return Comment - 수정된 댓글 데이터
     */
    public Comment updateComment(Long id, Comment updatedComment) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + id));
        existingComment.setContent(updatedComment.getContent());
        return commentRepository.save(existingComment);
    }

    /**
     * 특정 댓글 삭제
     *
     * @param id 삭제할 댓글 ID
     */
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
