package com.ohgiraffers.recipeapp.controller;

import com.ohgiraffers.recipeapp.entity.Comment;
import com.ohgiraffers.recipeapp.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 모든 댓글 조회
     *
     * @return ResponseEntity<List<Comment>> - 모든 댓글 목록과 HTTP 상태 코드
     */
    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments() {
        return ResponseEntity.ok(commentService.getAllComments());
    }

    /**
     * ID로 특정 댓글 조회
     *
     * @param id 댓글 ID (Path Variable)
     * @return ResponseEntity<Comment> - 조회된 댓글 데이터와 HTTP 상태 코드
     */
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable(name = "id") Long id) {
        Comment comment = commentService.getCommentById(id);
        return ResponseEntity.ok(comment);
    }

    /**
     * 특정 레시피의 댓글 조회
     *
     * @param recipeId 레시피 ID (Query Parameter)
     * @return ResponseEntity<List<Comment>> - 해당 레시피에 속한 댓글 목록과 HTTP 상태 코드
     */
    @GetMapping("/recipe")
    public ResponseEntity<List<Comment>> getCommentsByRecipe(@RequestParam(name = "recipeId") Long recipeId) {
        List<Comment> comments = commentService.getCommentsByRecipe(recipeId);
        return ResponseEntity.ok(comments);
    }

    /**
     * 특정 회원이 작성한 댓글 조회
     *
     * @param memberId 회원 ID (Query Parameter)
     * @return ResponseEntity<List<Comment>> - 해당 회원이 작성한 댓글 목록과 HTTP 상태 코드
     */
    @GetMapping("/member")
    public ResponseEntity<List<Comment>> getCommentsByMember(@RequestParam(name = "memberId") Long memberId) {
        List<Comment> comments = commentService.getCommentsByMember(memberId);
        return ResponseEntity.ok(comments);
    }

    /**
     * 새로운 댓글 추가
     *
     * @param comment 저장할 댓글 데이터 (Request Body)
     * @return ResponseEntity<Comment> - 저장된 댓글 데이터와 HTTP 상태 코드
     */
    @PostMapping
    public ResponseEntity<Comment> createComment(@Valid @RequestBody Comment comment) {
        Comment savedComment = commentService.saveComment(comment);
        return ResponseEntity.status(201).body(savedComment); // 201 Created
    }

    /**
     * 특정 댓글 수정
     *
     * @param id 수정할 댓글 ID (Path Variable)
     * @param updatedComment 수정할 댓글 데이터 (Request Body)
     * @return ResponseEntity<Comment> - 수정된 댓글 데이터와 HTTP 상태 코드
     */
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody Comment updatedComment
    ) {
        Comment comment = commentService.updateComment(id, updatedComment);
        return ResponseEntity.ok(comment);
    }

    /**
     * 특정 댓글 삭제
     *
     * @param id 삭제할 댓글 ID (Path Variable)
     * @return ResponseEntity<Void> - 본문 없이 HTTP 상태 코드만 반환
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable(name = "id") Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
