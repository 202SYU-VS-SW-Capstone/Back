package com.ohgiraffers.recipeapp.controller;

import com.ohgiraffers.recipeapp.entity.CookingStepImage;
import com.ohgiraffers.recipeapp.service.CookingStepImageService;
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
 * CookingStepImage 컨트롤러
 */
@RestController
@RequestMapping("/api/cooking-step-images")
public class CookingStepImageController {

    private final CookingStepImageService cookingStepImageService;

    public CookingStepImageController(CookingStepImageService cookingStepImageService) {
        this.cookingStepImageService = cookingStepImageService;
    }

    /**
     * 이미지 업로드 API
     */
    @PostMapping(value="/upload", consumes = "multipart/form-data")
    public ResponseEntity<CookingStepImage> uploadImage(@RequestParam("file") MultipartFile file) {
        CookingStepImage image = cookingStepImageService.uploadImage(file);
        return ResponseEntity.ok(image);
    }

    /**
     * 이미지 ID로 조회 API
     * - 이미지 깨짐 방지 및 Content-Type 설정
     *
     * @param id 조회할 이미지 ID
     * @return 이미지 리소스
     */
    @GetMapping("/view/{id}")
    public ResponseEntity<Resource> viewImageById(@PathVariable("id") Long id) {
        Resource resource = cookingStepImageService.loadImageById(id);
        String fileName = resource.getFilename();

        // MIME 타입(Content-Type) 설정
        String contentType = "application/octet-stream"; // 기본 Content-Type 설정
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
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable("id") Long id) {
        cookingStepImageService.deleteImageById(id);
        return ResponseEntity.noContent().build();
    }
}
