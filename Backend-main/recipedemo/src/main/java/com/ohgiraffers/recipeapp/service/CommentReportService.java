package com.ohgiraffers.recipeapp.service;

import com.ohgiraffers.recipeapp.entity.CommentReport;
import com.ohgiraffers.recipeapp.repository.CommentReportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CommentReportService {

    private final CommentReportRepository commentReportRepository;

    public CommentReportService(CommentReportRepository repository) {
        this.commentReportRepository = repository;
    }

    /**
     * 모든 댓글 신고 조회
     *
     * @return List<CommentReport> - 모든 신고 목록
     */
    public List<CommentReport> getAllReports() {
        return commentReportRepository.findAll();
    }

    /**
     * 특정 신고 ID로 신고 조회
     *
     * @param id 신고 ID
     * @return CommentReport - 신고 데이터
     * @throws IllegalArgumentException - 해당 ID의 신고가 없을 경우 예외 발생
     */
    public CommentReport getReportById(Long id) {
        return commentReportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Report not found with id: " + id));
    }

    /**
     * 특정 댓글에 대한 신고 조회
     *
     * @param commentId 댓글 ID
     * @return List<CommentReport> - 해당 댓글의 신고 목록
     */
    public List<CommentReport> getReportsByComment(Long commentId) {
        return commentReportRepository.findByCommentId(commentId);
    }

    /**
     * 특정 회원이 신고한 모든 댓글 신고 조회
     *
     * @param reporterId 회원 ID
     * @return List<CommentReport> - 해당 회원이 신고한 신고 목록
     */
    public List<CommentReport> getReportsByReporter(Long reporterId) {
        return commentReportRepository.findByReporterId(reporterId);
    }

    /**
     * 새로운 신고 저장
     *
     * @param report 저장할 신고 데이터
     * @return CommentReport - 저장된 신고 데이터
     */
    public CommentReport saveReport(CommentReport report) {
        report.setReportedAt(LocalDate.now()); // 신고 날짜 설정
        return commentReportRepository.save(report);
    }

    /**
     * 특정 신고 수정
     *
     * @param id 신고 ID
     * @param updatedReport 수정할 신고 데이터
     * @return CommentReport - 수정된 신고 데이터
     * @throws IllegalArgumentException - 해당 ID의 신고가 없을 경우 예외 발생
     */
    public CommentReport updateReport(Long id, CommentReport updatedReport) {
        CommentReport existingReport = getReportById(id);
        existingReport.setReportTitle(updatedReport.getReportTitle());
        existingReport.setReportContent(updatedReport.getReportContent());
        return commentReportRepository.save(existingReport);
    }

    /**
     * 특정 신고 삭제
     *
     * @param id 삭제할 신고 ID
     */
    public void deleteReport(Long id) {
        commentReportRepository.deleteById(id);
    }
}
