package com.ohgiraffers.recipeapp.controller;

import com.ohgiraffers.recipeapp.entity.CommentReport;
import com.ohgiraffers.recipeapp.service.CommentReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment-reports")
public class CommentReportController {

    private final CommentReportService commentReportService;

    public CommentReportController(CommentReportService commentReportService) {
        this.commentReportService = commentReportService;
    }

    /**
     * 모든 댓글 신고 조회
     *
     * @return ResponseEntity<List<CommentReport>> - 모든 신고 목록
     */
    @GetMapping
    public ResponseEntity<List<CommentReport>> getAllReports() {
        List<CommentReport> reports = commentReportService.getAllReports();
        return ResponseEntity.ok(reports);
    }

    /**
     * 특정 신고 ID로 신고 조회
     *
     * @param id 신고 ID (Path Variable)
     * @return ResponseEntity<CommentReport> - 신고 데이터
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommentReport> getReportById(@PathVariable("id") Long id) {
        CommentReport report = commentReportService.getReportById(id);
        return ResponseEntity.ok(report);
    }

    /**
     * 특정 댓글에 대한 신고 조회
     *
     * @param commentId 댓글 ID (Query Parameter)
     * @return ResponseEntity<List<CommentReport>> - 해당 댓글의 신고 목록
     */
    @GetMapping("/comment")
    public ResponseEntity<List<CommentReport>> getReportsByComment(@RequestParam("commentId") Long commentId) {
        List<CommentReport> reports = commentReportService.getReportsByComment(commentId);
        return ResponseEntity.ok(reports);
    }

    /**
     * 특정 회원이 신고한 모든 댓글 신고 조회
     *
     * @param reporterId 회원 ID (Query Parameter)
     * @return ResponseEntity<List<CommentReport>> - 해당 회원의 신고 목록
     */
    @GetMapping("/reporter")
    public ResponseEntity<List<CommentReport>> getReportsByReporter(@RequestParam("reporterId") Long reporterId) {
        List<CommentReport> reports = commentReportService.getReportsByReporter(reporterId);
        return ResponseEntity.ok(reports);
    }

    /**
     * 새로운 신고 추가
     *
     * @param report 저장할 신고 데이터 (Request Body)
     * @return ResponseEntity<CommentReport> - 저장된 신고 데이터
     */
    @PostMapping
    public ResponseEntity<CommentReport> createReport(@RequestBody CommentReport report) {
        CommentReport savedReport = commentReportService.saveReport(report);
        return ResponseEntity.status(201).body(savedReport); // 201 Created
    }

    /**
     * 특정 신고 수정
     *
     * @param id 신고 ID (Path Variable)
     * @param updatedReport 수정할 신고 데이터 (Request Body)
     * @return ResponseEntity<CommentReport> - 수정된 신고 데이터
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommentReport> updateReport(
            @PathVariable("id") Long id,
            @RequestBody CommentReport updatedReport) {
        CommentReport updated = commentReportService.updateReport(id, updatedReport);
        return ResponseEntity.ok(updated);
    }


    /**
     * 특정 신고 삭제
     *
     * @param id 삭제할 신고 ID (Path Variable)
     * @return ResponseEntity<Void> - 본문 없이 HTTP 상태 코드만 반환
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable("id") Long id) {
        commentReportService.deleteReport(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
