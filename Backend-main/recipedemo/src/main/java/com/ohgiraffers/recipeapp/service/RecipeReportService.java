package com.ohgiraffers.recipeapp.service;

import com.ohgiraffers.recipeapp.entity.RecipeReport;
import com.ohgiraffers.recipeapp.repository.RecipeReportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecipeReportService {

    private final RecipeReportRepository recipeReportRepository;

    public RecipeReportService(RecipeReportRepository recipeReportRepository) {
        this.recipeReportRepository = recipeReportRepository;
    }

    /**
     * 모든 레시피 신고 조회
     *
     * @return List<RecipeReport> - 모든 신고 목록
     */
    public List<RecipeReport> getAllReports() {
        return recipeReportRepository.findAll();
    }

    /**
     * 특정 신고 ID로 신고 조회
     *
     * @param id 신고 ID
     * @return RecipeReport - 신고 데이터
     * @throws IllegalArgumentException - 해당 ID의 신고가 없을 경우 예외 발생
     */
    public RecipeReport getReportById(Long id) {
        return recipeReportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Report not found with id: " + id));
    }

    /**
     * 특정 레시피에 대한 신고 조회
     *
     * @param recipeId 레시피 ID
     * @return List<RecipeReport> - 해당 레시피의 신고 목록
     */
    public List<RecipeReport> getReportsByRecipe(Long recipeId) {
        return recipeReportRepository.findByRecipeId(recipeId);
    }

    /**
     * 특정 회원이 신고한 모든 레시피 신고 조회
     *
     * @param reporterId 회원 ID
     * @return List<RecipeReport> - 해당 회원이 신고한 신고 목록
     */
    public List<RecipeReport> getReportsByReporter(Long reporterId) {
        return recipeReportRepository.findByReporterId(reporterId);
    }

    /**
     * 새로운 신고 저장
     *
     * @param report 저장할 신고 데이터
     * @return RecipeReport - 저장된 신고 데이터
     */
    public RecipeReport saveReport(RecipeReport report) {
        report.setReportedAt(LocalDate.now()); // 신고 날짜 설정
        return recipeReportRepository.save(report);
    }

    /**
     * 특정 신고 수정
     *
     * @param id 수정할 신고 ID
     * @param updatedReport 수정할 신고 데이터
     * @return RecipeReport - 수정된 신고 데이터
     */
    public RecipeReport updateReport(Long id, RecipeReport updatedReport) {
        RecipeReport existingReport = recipeReportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("신고를 찾을 수 없습니다. ID: " + id));

        // 필드 업데이트 (수정 가능한 필드만 갱신)
        existingReport.setReportTitle(updatedReport.getReportTitle());
        existingReport.setReportContent(updatedReport.getReportContent());
        existingReport.setReportedAt(updatedReport.getReportedAt());

        return recipeReportRepository.save(existingReport);
    }

    /**
     * 특정 신고 삭제
     *
     * @param id 삭제할 신고 ID
     */
    public void deleteReport(Long id) {
        recipeReportRepository.deleteById(id);
    }
}
