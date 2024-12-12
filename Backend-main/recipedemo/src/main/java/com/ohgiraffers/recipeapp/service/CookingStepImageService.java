package com.ohgiraffers.recipeapp.service;

import com.ohgiraffers.recipeapp.entity.CookingStepImage;
import com.ohgiraffers.recipeapp.repository.CookingStepImageRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class CookingStepImageService {

    private final CookingStepImageRepository cookingStepImageRepository;

    // 이미지 저장 경로
    private final Path uploadDir = Paths.get("uploads/cooking_step_images");

    public CookingStepImageService(CookingStepImageRepository cookingStepImageRepository) {
        this.cookingStepImageRepository = cookingStepImageRepository;
    }

    /**
     * 이미지 업로드
     */
    public CookingStepImage uploadImage(MultipartFile file) {
        try {
            // UUID로 파일명 생성
            String uuid = UUID.randomUUID().toString();
            String fileName = uuid + "_" + file.getOriginalFilename();

            // 디렉토리 생성
            Files.createDirectories(uploadDir);
            Files.copy(file.getInputStream(), uploadDir.resolve(fileName));

            // 엔티티 생성 및 저장
            CookingStepImage image = CookingStepImage.builder()
                    .uuid(uuid)
                    .originalName(file.getOriginalFilename())
                    .fileName(fileName)
                    .url("/uploads/cooking_step_images/" + fileName)
                    .uploadDate(LocalDate.now())
                    .build();

            return cookingStepImageRepository.save(image);
        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드 실패", e);
        }
    }

    /**
     * 이미지 로드
     */
    public Resource loadImageById(Long id) {
        try {
            CookingStepImage image = cookingStepImageRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("이미지를 찾을 수 없습니다. ID: " + id));
            Path filePath = uploadDir.resolve(image.getFileName());
            return new UrlResource(filePath.toUri());
        } catch (IOException e) {
            throw new RuntimeException("이미지 로드 실패", e);
        }
    }

    /**
     * 이미지 삭제
     */
    public void deleteImageById(Long id) {
        try {
            CookingStepImage image = cookingStepImageRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("이미지를 찾을 수 없습니다. ID: " + id));

            Path filePath = uploadDir.resolve(image.getFileName());
            Files.deleteIfExists(filePath);
            cookingStepImageRepository.deleteById(id);
        } catch (IOException e) {
            throw new RuntimeException("이미지 삭제 실패", e);
        }
    }
}
