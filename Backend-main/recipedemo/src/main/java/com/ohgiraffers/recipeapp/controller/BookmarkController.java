package com.ohgiraffers.recipeapp.controller;

import com.ohgiraffers.recipeapp.entity.Bookmark;
import com.ohgiraffers.recipeapp.service.BookmarkService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    /**
     * 특정 회원의 북마크 목록 조회
     *
     * @param memberId 회원 ID (Query Parameter)
     * @return ResponseEntity<List<Bookmark>> - 해당 회원의 북마크 목록과 HTTP 상태 코드
     */
    @GetMapping
    public ResponseEntity<List<Bookmark>> getBookmarksByMember(
            @Parameter(description = "회원 ID") @RequestParam("memberId") Long memberId) {
        List<Bookmark> bookmarks = bookmarkService.getBookmarksByMember(memberId);
        return ResponseEntity.ok(bookmarks);
    }

    /**
     * 북마크 추가 또는 삭제 (토글 방식)
     *
     * @param memberId 회원 ID (Query Parameter)
     * @param recipeId 레시피 ID (Query Parameter)
     * @return ResponseEntity<String> - 추가/삭제 결과 메시지와 HTTP 상태 코드
     */
    @PostMapping("/toggle")
    public ResponseEntity<String> toggleBookmark(
            @RequestParam("memberId") Long memberId,
            @RequestParam("recipeId") Long recipeId
    ) {
        boolean isAdded = bookmarkService.toggleBookmark(memberId, recipeId);
        String message = isAdded ? "Bookmark added successfully." : "Bookmark removed successfully.";
        return ResponseEntity.ok(message);
    }

    /**
     * 특정 회원이 특정 레시피를 북마크했는지 확인
     *
     * @param memberId 회원 ID (Query Parameter)
     * @param recipeId 레시피 ID (Query Parameter)
     * @return ResponseEntity<Boolean> - 북마크 여부와 HTTP 상태 코드
     */
    @GetMapping("/exists")
    public ResponseEntity<Boolean> isRecipeBookmarked(
            @RequestParam("memberId") Long memberId,
            @RequestParam("recipeId") Long recipeId
    ) {
        boolean isBookmarked = bookmarkService.isRecipeBookmarked(memberId, recipeId);
        return ResponseEntity.ok(isBookmarked);
    }
}
