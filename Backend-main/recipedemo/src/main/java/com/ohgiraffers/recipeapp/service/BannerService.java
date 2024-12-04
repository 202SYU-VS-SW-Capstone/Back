package com.ohgiraffers.recipeapp.service;

import com.ohgiraffers.recipeapp.entity.Banner;
import com.ohgiraffers.recipeapp.repository.BannerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BannerService {

    private final BannerRepository bannerRepository;

    public BannerService(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    /**
     * 모든 배너 조회
     *
     * @return List<Banner> - 모든 배너 목록
     */
    public List<Banner> getAllBanners() {
        return bannerRepository.findAll();
    }

    /**
     * 특정 회원이 생성한 배너 조회
     *
     * @param memberId 회원 ID
     * @return List<Banner> - 해당 회원이 생성한 배너 목록
     */
    public List<Banner> getBannersByMember(Long memberId) {
        return bannerRepository.findByMemberId(memberId);
    }

    /**
     * 특정 공지사항과 연결된 배너 조회
     *
     * @param noticeId 공지사항 ID
     * @return List<Banner> - 해당 공지사항과 연결된 배너 목록
     */
    public List<Banner> getBannersByNotice(Long noticeId) {
        return bannerRepository.findByNoticeId(noticeId);
    }

    /**
     * ID로 특정 배너 조회
     *
     * @param id 배너 ID
     * @return Banner - 조회된 배너 데이터
     * @throws IllegalArgumentException - 해당 ID의 배너가 없을 경우 예외 발생
     */
    public Banner getBannerById(Long id) {
        return bannerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Banner not found with id: " + id));
    }

    /**
     * 새로운 배너 저장
     *
     * @param banner 저장할 배너 데이터
     * @return Banner - 저장된 배너 데이터
     */
    public Banner saveBanner(Banner banner) {
        banner.setCreatedAt(LocalDate.now()); // 생성 날짜 설정
        return bannerRepository.save(banner);
    }

    /**
     * 특정 배너 수정
     *
     * @param id 수정할 배너 ID
     * @param updatedBanner 수정할 배너 데이터
     * @return Banner - 수정된 배너 데이터
     */
    public Banner updateBanner(Long id, Banner updatedBanner) {
        Banner existingBanner = bannerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Banner with ID " + id + " not found."));

        // 필요한 필드를 업데이트
        existingBanner.setNotice(updatedBanner.getNotice());
        existingBanner.setMember(updatedBanner.getMember());
        existingBanner.setBannerImage(updatedBanner.getBannerImage());
        existingBanner.setCreatedAt(updatedBanner.getCreatedAt());

        return bannerRepository.save(existingBanner);
    }

    /**
     * 특정 배너 삭제
     *
     * @param id 삭제할 배너 ID
     */
    public void deleteBanner(Long id) {
        bannerRepository.deleteById(id);
    }
}

