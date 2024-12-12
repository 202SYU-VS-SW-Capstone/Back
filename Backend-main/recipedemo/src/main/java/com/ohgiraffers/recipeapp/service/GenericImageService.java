package com.ohgiraffers.recipeapp.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * 공통 이미지 서비스 인터페이스
 *
 * 모든 이미지 관련 테이블의 공통 로직을 정의합니다.
 * T는 엔티티 타입으로 각 테이블에 맞는 엔티티가 해당됩니다.
 */
public interface GenericImageService<T> {
    /**
     * 이미지 파일을 업로드하고 데이터베이스에 정보를 저장합니다.
     *
     * @param file 업로드할 이미지 파일
     * @return 저장된 이미지 엔티티 객체
     */
    T uploadImage(MultipartFile file);

    /**
     * ID를 통해 이미지 파일을 로드하여 반환합니다.
     *
     * @param id 로드할 이미지의 ID
     * @return 이미지 리소스
     */
    Resource loadImageById(Long id);

    /**
     * 이미지 파일을 삭제합니다.
     *
     * @param id 삭제할 이미지의 ID
     */
    void deleteImageById(Long id);
}
