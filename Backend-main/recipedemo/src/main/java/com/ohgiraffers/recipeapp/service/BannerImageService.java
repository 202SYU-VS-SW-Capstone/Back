package com.ohgiraffers.recipeapp.service;

import com.ohgiraffers.recipeapp.entity.BannerImage;
import com.ohgiraffers.recipeapp.repository.BannerImageRepository;
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
 * BannerImage 서비스 클래스
 * 배너 이미지 업로드, 조회, 삭제를 담당합니다.
 */
@Service
public class BannerImageService implements GenericImageService<BannerImage> {

    private final BannerImageRepository bannerImageRepository;

    // 배너 이미지 저장 경로
    private final Path uploadDir = Paths.get("uploads/banner_images");

    public BannerImageService(BannerImageRepository bannerImageRepository) {
        this.bannerImageRepository = bannerImageRepository;
    }

    /**
     * 배너 이미지 업로드
     *
     * @param file 업로드할 이미지 파일
     * @return 저장된 배너 이미지 엔티티
     */
    @Override
    public BannerImage uploadImage(MultipartFile file) {
        try {
            // 파일 이름 생성
            String uuid = UUID.randomUUID().toString();
            String fileName = uuid + "_" + file.getOriginalFilename();

            // 저장 디렉토리 생성 및 파일 저장
            Files.createDirectories(uploadDir);
            Files.copy(file.getInputStream(), uploadDir.resolve(fileName));

            // BannerImage 엔티티 생성 및 저장
            BannerImage image = BannerImage.builder()
                    .uuid(uuid)
                    .originalName(file.getOriginalFilename())
                    .fileName(fileName)
                    .url("/uploads/banner_images/" + fileName)
                    .uploadDate(LocalDate.now())
                    .build();

            return bannerImageRepository.save(image);
        } catch (IOException e) {
            throw new RuntimeException("배너 이미지 업로드 실패", e);
        }
    }

    /**
     * ID를 통해 배너 이미지 로드
     *
     * @param id 로드할 배너 이미지 ID
     * @return 이미지 리소스
     */
    @Override
    public Resource loadImageById(Long id) {
        try {
            BannerImage image = bannerImageRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("배너 이미지를 찾을 수 없습니다. ID: " + id));
            Path filePath = uploadDir.resolve(image.getFileName());
            return new UrlResource(filePath.toUri());
        } catch (IOException e) {
            throw new RuntimeException("배너 이미지 로드 실패", e);
        }
    }

    /**
     * ID를 통해 파일 이름 조회
     *
     * @param id 조회할 이미지 ID
     * @return 이미지 파일 이름
     */
    public String getFileNameById(Long id) {
        BannerImage image = bannerImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("배너 이미지를 찾을 수 없습니다. ID: " + id));
        return image.getFileName();
    }

    /**
     * ID를 통해 배너 이미지 삭제
     *
     * @param id 삭제할 배너 이미지 ID
     */
    @Override
    public void deleteImageById(Long id) {
        try {
            BannerImage image = bannerImageRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("배너 이미지를 찾을 수 없습니다. ID: " + id));

            // 파일 삭제
            Path filePath = uploadDir.resolve(image.getFileName());
            Files.deleteIfExists(filePath);

            // 데이터베이스에서 엔티티 삭제
            bannerImageRepository.deleteById(id);
        } catch (IOException e) {
            throw new RuntimeException("배너 이미지 삭제 실패", e);
        }
    }
}
