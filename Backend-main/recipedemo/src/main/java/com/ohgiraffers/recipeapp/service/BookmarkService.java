package com.ohgiraffers.recipeapp.service;

import com.ohgiraffers.recipeapp.entity.Bookmark;
import com.ohgiraffers.recipeapp.entity.Member;
import com.ohgiraffers.recipeapp.entity.Recipe;
import com.ohgiraffers.recipeapp.repository.BookmarkRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    /**
     * 특정 회원의 북마크 목록 조회
     *
     * @param memberId 회원 ID
     * @return List<Bookmark> - 해당 회원의 북마크 목록
     */
    public List<Bookmark> getBookmarksByMember(Long memberId) {
        return bookmarkRepository.findByMemberId(memberId);
    }

    /**
     * 특정 회원이 특정 레시피를 북마크했는지 확인
     *
     * @param memberId 회원 ID
     * @param recipeId 레시피 ID
     * @return boolean - 북마크 여부
     */
    public boolean isRecipeBookmarked(Long memberId, Long recipeId) {
        return bookmarkRepository.existsByMemberIdAndRecipeId(memberId, recipeId);
    }

    /**
     * 북마크 추가 또는 삭제 (토글 방식)
     *
     * @param memberId 회원 ID
     * @param recipeId 레시피 ID
     * @return boolean - true: 북마크 추가됨, false: 북마크 삭제됨
     */
    public boolean toggleBookmark(Long memberId, Long recipeId) {
        boolean isBookmarked = bookmarkRepository.existsByMemberIdAndRecipeId(memberId, recipeId);

        if (isBookmarked) {
            // 북마크 삭제
            Bookmark bookmark = bookmarkRepository.findByMemberIdAndRecipeId(memberId, recipeId);
            bookmarkRepository.delete(bookmark);
            return false;
        } else {
            // 북마크 추가
            Bookmark bookmark = Bookmark.builder()
                    .member(Member.builder().id(memberId).build())
                    .recipe(Recipe.builder().id(recipeId).build())
                    .savedAt(LocalDate.now())
                    .build();
            bookmarkRepository.save(bookmark);
            return true;
        }
    }
}

