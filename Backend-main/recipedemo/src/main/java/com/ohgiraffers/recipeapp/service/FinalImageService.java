package com.ohgiraffers.recipeapp.service;

import com.ohgiraffers.recipeapp.entity.FinalImage;
import com.ohgiraffers.recipeapp.repository.FinalImageRepository;
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

/**
 * FinalImage 엔티티를 위한 서비스 구현체
 *
 * GenericImageService를 구현하여 이미지 업로드, 조회, 삭제 기능을 제공합니다.
 */
@Service
public class FinalImageService implements GenericImageService<FinalImage> {

    private final FinalImageRepository finalImageRepository;

    // 이미지 파일이 저장될 경로
    private final Path uploadDir = Paths.get("uploads/final_images");

    public FinalImageService(FinalImageRepository finalImageRepository) {
        this.finalImageRepository = finalImageRepository;
    }

    /**
     * 이미지 업로드
     *
     * @param file 업로드할 이미지 파일
     * @return 저장된 FinalImage 엔티티 객체
     */
    @Override
    public FinalImage uploadImage(MultipartFile file) {
        try {
            // 파일명 생성: UUID + 원본 파일명
            String uuid = UUID.randomUUID().toString();
            String fileName = uuid + "_" + file.getOriginalFilename();

            // 저장 디렉토리 생성 (존재하지 않을 경우)
            Files.createDirectories(uploadDir);

            // 파일 저장
            Files.copy(file.getInputStream(), uploadDir.resolve(fileName));

            // FinalImage 엔티티 생성 및 저장
            FinalImage image = new FinalImage();
            image.setUuid(uuid);
            image.setOriginalName(file.getOriginalFilename());
            image.setFileName(fileName);
            image.setUrl("/uploads/final_images/" + fileName);
            image.setUploadDate(LocalDate.now());

            return finalImageRepository.save(image);
        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드 실패", e);
        }
    }

    /**
     * ID를 통해 이미지 로드
     *
     * @param id 조회할 이미지의 ID
     * @return 이미지 리소스
     */
    @Override
    public Resource loadImageById(Long id) {
        try {
            FinalImage image = finalImageRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("이미지를 찾을 수 없습니다. ID: " + id));
            Path filePath = uploadDir.resolve(image.getFileName());
            return new UrlResource(filePath.toUri());
        } catch (IOException e) {
            throw new RuntimeException("이미지 로드 실패", e);
        }
    }

    /**
     * ID를 통해 파일 이름 조회
     *
     * @param id 조회할 이미지의 ID
     * @return 이미지 파일 이름
     */
    public String getFileNameById(Long id) {
        FinalImage image = finalImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("이미지를 찾을 수 없습니다. ID: " + id));
        return image.getFileName();
    }

    /**
     * ID를 통해 이미지 삭제
     *
     * @param id 삭제할 이미지의 ID
     */
    @Override
    public void deleteImageById(Long id) {
        try {
            FinalImage image = finalImageRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("이미지를 찾을 수 없습니다. ID: " + id));

            Path filePath = uploadDir.resolve(image.getFileName());
            Files.deleteIfExists(filePath);

            finalImageRepository.deleteById(id);
        } catch (IOException e) {
            throw new RuntimeException("이미지 삭제 실패", e);
        }
    }
}
