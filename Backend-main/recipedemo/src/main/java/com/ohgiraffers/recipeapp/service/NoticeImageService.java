package com.ohgiraffers.recipeapp.service;

import com.ohgiraffers.recipeapp.entity.NoticeImage;
import com.ohgiraffers.recipeapp.repository.NoticeImageRepository;
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
 * NoticeImage 서비스 클래스
 * 공지사항 이미지 업로드, 조회, 삭제를 담당합니다.
 */
@Service
public class NoticeImageService implements GenericImageService<NoticeImage> {

    private final NoticeImageRepository noticeImageRepository;

    // 이미지 파일이 저장될 경로
    private final Path uploadDir = Paths.get("uploads/notice_images");

    public NoticeImageService(NoticeImageRepository noticeImageRepository) {
        this.noticeImageRepository = noticeImageRepository;
    }

    /**
     * 이미지 파일 업로드
     *
     * @param file 업로드할 이미지 파일
     * @return 저장된 이미지 엔티티 객체
     */
    @Override
    public NoticeImage uploadImage(MultipartFile file) {
        try {
            // UUID로 고유 파일 이름 생성
            String uuid = UUID.randomUUID().toString();
            String fileName = uuid + "_" + file.getOriginalFilename();

            // 디렉토리 생성 및 파일 저장
            Files.createDirectories(uploadDir);
            Files.copy(file.getInputStream(), uploadDir.resolve(fileName));

            // NoticeImage 엔티티 생성 및 저장
            NoticeImage image = NoticeImage.builder()
                    .uuid(uuid)
                    .originalName(file.getOriginalFilename())
                    .fileName(fileName)
                    .url("/uploads/notice_images/" + fileName)
                    .uploadDate(LocalDate.now())
                    .build();

            return noticeImageRepository.save(image);
        } catch (IOException e) {
            throw new RuntimeException("공지사항 이미지 업로드 실패", e);
        }
    }

    /**
     * ID를 통해 이미지 로드
     *
     * @param id 조회할 이미지의 ID
     * @return 이미지 리소스 객체
     */
    @Override
    public Resource loadImageById(Long id) {
        try {
            NoticeImage image = noticeImageRepository.findById(id)
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
        NoticeImage image = noticeImageRepository.findById(id)
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
            NoticeImage image = noticeImageRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("이미지를 찾을 수 없습니다. ID: " + id));

            // 파일 삭제
            Path filePath = uploadDir.resolve(image.getFileName());
            Files.deleteIfExists(filePath);

            // 데이터베이스에서 엔티티 삭제
            noticeImageRepository.deleteById(id);
        } catch (IOException e) {
            throw new RuntimeException("이미지 삭제 실패", e);
        }
    }
}
