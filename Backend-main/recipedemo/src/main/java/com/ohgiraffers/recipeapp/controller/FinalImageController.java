package com.ohgiraffers.recipeapp.controller;

import com.ohgiraffers.recipeapp.entity.FinalImage;
import com.ohgiraffers.recipeapp.service.FinalImageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * FinalImage 컨트롤러
 *
 * FinalImage와 관련된 RESTful API를 제공합니다.
 */
@RestController
@RequestMapping("/api/final-images")
public class FinalImageController {

    private final FinalImageService finalImageService;

    public FinalImageController(FinalImageService finalImageService) {
        this.finalImageService = finalImageService;
    }

    /**
     * 이미지 업로드 API
     * - consumes = "multipart/form-data"를 명시해 Swagger UI에서 파일 업로드를 지원합니다.
     *
     * @param file 업로드할 이미지 파일
     * @return 저장된 이미지 엔티티
     */
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<FinalImage> uploadImage(@RequestParam("file") MultipartFile file) {
        FinalImage image = finalImageService.uploadImage(file);
        return ResponseEntity.ok(image);
    }

    /**
     * 이미지 ID로 조회 API
     * - Swagger에서 파일명을 반환하도록 설정합니다.
     *
     * @param id 조회할 이미지 ID
     * @return 이미지 리소스
     */
    @GetMapping("/view/{id}")
    public ResponseEntity<Resource> viewImageById(@PathVariable("id") Long id) {
        Resource resource = finalImageService.loadImageById(id);
        String fileName = finalImageService.getFileNameById(id);

        // MIME 타입(Content-Type) 설정
        String contentType = "application/octet-stream"; // 기본 Content-Type
        try {
            contentType = Files.probeContentType(Paths.get(resource.getURI()));
        } catch (IOException e) {
            System.out.println("Content-Type 설정 실패: " + e.getMessage());
        }

        // 파일 이름 UTF-8 인코딩
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename*=UTF-8''" + encodedFileName)
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(resource);
    }

    /**
     * 이미지 삭제 API
     *
     * @param id 삭제할 이미지 ID
     * @return HTTP 상태 코드 204 (No Content)
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable("id") Long id) {
        finalImageService.deleteImageById(id);
        return ResponseEntity.noContent().build();
    }
}
