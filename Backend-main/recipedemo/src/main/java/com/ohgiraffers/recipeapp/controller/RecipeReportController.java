package com.ohgiraffers.recipeapp.controller;

import com.ohgiraffers.recipeapp.entity.RecipeReport;
import com.ohgiraffers.recipeapp.service.RecipeReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe-reports")
public class RecipeReportController {

    private final RecipeReportService recipeReportService;

    public RecipeReportController(RecipeReportService recipeReportService) {
        this.recipeReportService = recipeReportService;
    }

    /**
     * 모든 레시피 신고 조회
     *
     * @return ResponseEntity<List<RecipeReport>> - 모든 신고 목록
     */
    @GetMapping
    public ResponseEntity<List<RecipeReport>> getAllReports() {
        List<RecipeReport> reports = recipeReportService.getAllReports();
        return ResponseEntity.ok(reports);
    }

    /**
     * 특정 신고 ID로 신고 조회
     *
     * @param id 신고 ID (Path Variable)
     * @return ResponseEntity<RecipeReport> - 신고 데이터
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecipeReport> getReportById(@PathVariable("id") Long id) {
        RecipeReport report = recipeReportService.getReportById(id);
        return ResponseEntity.ok(report);
    }

    /**
     * 특정 레시피에 대한 신고 조회
     *
     * @param recipeId 레시피 ID (Query Parameter)
     * @return ResponseEntity<List<RecipeReport>> - 해당 레시피의 신고 목록
     */
    @GetMapping("/recipe")
    public ResponseEntity<List<RecipeReport>> getReportsByRecipe(@RequestParam("recipeId") Long recipeId) {
        List<RecipeReport> reports = recipeReportService.getReportsByRecipe(recipeId);
        return ResponseEntity.ok(reports);
    }

    /**
     * 특정 회원이 신고한 모든 레시피 신고 조회
     *
     * @param reporterId 회원 ID (Query Parameter)
     * @return ResponseEntity<List<RecipeReport>> - 해당 회원의 신고 목록
     */
    @GetMapping("/reporter")
    public ResponseEntity<List<RecipeReport>> getReportsByReporter(@RequestParam("reporterId") Long reporterId) {
        List<RecipeReport> reports = recipeReportService.getReportsByReporter(reporterId);
        return ResponseEntity.ok(reports);
    }

    /**
     * 새로운 신고 추가
     *
     * @param report 저장할 신고 데이터 (Request Body)
     * @return ResponseEntity<RecipeReport> - 저장된 신고 데이터
     */
    @PostMapping
    public ResponseEntity<RecipeReport> createReport(@RequestBody RecipeReport report) {
        RecipeReport savedReport = recipeReportService.saveReport(report);
        return ResponseEntity.status(201).body(savedReport); // 201 Created
    }

    /**
     * 특정 신고 수정
     *
     * @param id 수정할 신고 ID (Path Variable)
     * @param updatedReport 수정할 신고 데이터 (Request Body)
     * @return ResponseEntity<RecipeReport> - 수정된 신고 데이터
     */
    @PutMapping("/{id}")
    public ResponseEntity<RecipeReport> updateReport(
            @PathVariable("id") Long id,
            @RequestBody RecipeReport updatedReport) {
        RecipeReport report = recipeReportService.updateReport(id, updatedReport);
        return ResponseEntity.ok(report);
    }

    /**
     * 특정 신고 삭제
     *
     * @param id 삭제할 신고 ID (Path Variable)
     * @return ResponseEntity<Void> - 본문 없이 HTTP 상태 코드만 반환
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable("id") Long id) {
        recipeReportService.deleteReport(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}

