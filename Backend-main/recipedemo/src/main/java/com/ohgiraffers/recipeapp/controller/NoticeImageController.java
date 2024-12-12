package com.ohgiraffers.recipeapp.controller;

import com.ohgiraffers.recipeapp.entity.NoticeImage;
import com.ohgiraffers.recipeapp.service.NoticeImageService;
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
 * NoticeImage 컨트롤러
 * 공지사항 이미지 업로드, 조회, 삭제를 담당합니다.
 */
@RestController
@RequestMapping("/api/notice-images")
public class NoticeImageController {

    private final NoticeImageService noticeImageService;

    public NoticeImageController(NoticeImageService noticeImageService) {
        this.noticeImageService = noticeImageService;
    }

    /**
     * 공지사항 이미지 업로드 API
     *
     * @param file 업로드할 이미지 파일
     * @return 저장된 이미지 정보
     */
    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<NoticeImage> uploadImage(@RequestParam("file") MultipartFile file) {
        NoticeImage savedImage = noticeImageService.uploadImage(file);
        return ResponseEntity.ok(savedImage);
    }

    /**
     * 이미지 ID를 통해 조회 API
     *
     * @param id 조회할 이미지 ID
     * @return 이미지 리소스
     */
    @GetMapping("/view/{id}")
    public ResponseEntity<Resource> viewImageById(@PathVariable("id") Long id) {
        Resource resource = noticeImageService.loadImageById(id);
        String fileName = noticeImageService.getFileNameById(id);

        // MIME 타입 확인
        String contentType = "application/octet-stream";
        try {
            contentType = Files.probeContentType(Paths.get(resource.getURI()));
        } catch (IOException e) {
            System.out.println("Content-Type 설정 실패: " + e.getMessage());
        }

        // 파일 이름 인코딩
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename*=UTF-8''" + encodedFileName)
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(resource);
    }

    /**
     * 이미지 ID를 통해 삭제 API
     *
     * @param id 삭제할 이미지 ID
     * @return HTTP 상태 코드 204 (No Content)
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteImageById(@PathVariable("id") Long id) {
        noticeImageService.deleteImageById(id);
        return ResponseEntity.noContent().build();
    }
}
