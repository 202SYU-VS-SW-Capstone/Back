package com.ohgiraffers.recipeapp.controller;

import com.ohgiraffers.recipeapp.entity.MemberImage;
import com.ohgiraffers.recipeapp.service.MemberImageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * MemberImage 컨트롤러
 *
 * - 이미지 업로드, 조회, 삭제를 처리합니다.
 */
@RestController
@RequestMapping("/api/member-images")
public class MemberImageController {

    private final MemberImageService service;

    public MemberImageController(MemberImageService service) {
        this.service = service;
    }

    /**
     * 이미지 업로드 API
     *
     * @param file 업로드할 이미지 파일
     * @return 저장된 이미지 엔티티
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MemberImage> uploadImage(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(service.uploadImage(file));
    }

    /**
     * 이미지 ID로 조회 API
     *
     * @param id 조회할 이미지 ID
     * @return 이미지 리소스
     */
    @GetMapping("/view/{id}")
    public ResponseEntity<Resource> viewImageById(@PathVariable("id") Long id) {
        Resource resource = service.loadImageById(id);
        String fileName = service.getFileNameById(id);

        String contentType = "application/octet-stream";
        try {
            contentType = Files.probeContentType(Paths.get(resource.getURI()));
        } catch (IOException e) {
            System.out.println("Content-Type 설정 실패: " + e.getMessage());
        }

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
     * @return 상태 코드 204 (No Content)
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable("id") Long id) {
        service.deleteImageById(id);
        return ResponseEntity.noContent().build();
    }
}
