package com.ohgiraffers.recipeapp.controller;

import com.ohgiraffers.recipeapp.entity.Notice;
import com.ohgiraffers.recipeapp.service.NoticeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    /**
     * 모든 공지사항 조회
     *
     * @return ResponseEntity<List<Notice>> - 모든 공지사항 목록과 HTTP 상태 코드
     */
    @GetMapping
    public ResponseEntity<List<Notice>> getAllNotices() {
        List<Notice> notices = noticeService.getAllNotices();
        return ResponseEntity.ok(notices);
    }

    /**
     * ID로 특정 공지사항 조회
     *
     * @param id 공지사항 ID (Path Variable)
     * @return ResponseEntity<Notice> - 조회된 공지사항 데이터와 HTTP 상태 코드
     */
    @GetMapping("/{id}")
    public ResponseEntity<Notice> getNoticeById(@PathVariable("id") Long id) {
        Notice notice = noticeService.getNoticeById(id);
        return ResponseEntity.ok(notice);
    }

    /**
     * 제목으로 공지사항 검색
     *
     * @param title 공지 제목 (Query Parameter)
     * @return ResponseEntity<List<Notice>> - 검색된 공지사항 목록과 HTTP 상태 코드
     */
    @GetMapping("/search")
    public ResponseEntity<List<Notice>> searchNoticesByTitle(@RequestParam("title") String title) {
        List<Notice> notices = noticeService.searchNoticesByTitle(title);
        return ResponseEntity.ok(notices);
    }

    /**
     * 새로운 공지사항 추가
     *
     * @param notice 저장할 공지사항 데이터 (Request Body)
     * @return ResponseEntity<Notice> - 저장된 공지사항 데이터와 HTTP 상태 코드
     */
    @PostMapping
    public ResponseEntity<Notice> createNotice(@RequestBody Notice notice) {
        Notice savedNotice = noticeService.saveNotice(notice);
        return ResponseEntity.status(201).body(savedNotice); // 201 Created
    }

    /**
     * 특정 공지사항 수정
     *
     * @param id 수정할 공지사항 ID (Path Variable)
     * @param updatedNotice 수정할 공지사항 데이터 (Request Body)
     * @return ResponseEntity<Notice> - 수정된 공지사항 데이터와 HTTP 상태 코드
     */
    @PutMapping("/{id}")
    public ResponseEntity<Notice> updateNotice(
            @PathVariable("id") Long id,
            @RequestBody Notice updatedNotice
    ) {
        Notice notice = noticeService.updateNotice(id, updatedNotice);
        return ResponseEntity.ok(notice);
    }

    /**
     * 특정 공지사항 삭제
     *
     * @param id 삭제할 공지사항 ID (Path Variable)
     * @return ResponseEntity<Void> - 본문 없이 HTTP 상태 코드만 반환
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable("id") Long id) {
        noticeService.deleteNotice(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
