package com.ohgiraffers.recipeapp.controller;

import com.ohgiraffers.recipeapp.entity.Banner;
import com.ohgiraffers.recipeapp.service.BannerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banners")
public class BannerController {

    private final BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    /**
     * 모든 배너 조회
     *
     * @return ResponseEntity<List<Banner>> - 모든 배너 목록
     */
    @GetMapping
    public ResponseEntity<List<Banner>> getAllBanners() {
        List<Banner> banners = bannerService.getAllBanners();
        return ResponseEntity.ok(banners);
    }

    /**
     * 특정 회원이 생성한 배너 조회
     *
     * @param memberId 회원 ID (Query Parameter)
     * @return ResponseEntity<List<Banner>> - 해당 회원이 생성한 배너 목록
     */
    @GetMapping("/member")
    public ResponseEntity<List<Banner>> getBannersByMember(@RequestParam("memberId") Long memberId) {
        List<Banner> banners = bannerService.getBannersByMember(memberId);
        return ResponseEntity.ok(banners);
    }

    /**
     * 특정 공지사항과 연결된 배너 조회
     *
     * @param noticeId 공지사항 ID (Query Parameter)
     * @return ResponseEntity<List<Banner>> - 해당 공지사항과 연결된 배너 목록
     */
    @GetMapping("/notice")
    public ResponseEntity<List<Banner>> getBannersByNotice(@RequestParam("noticeId") Long noticeId) {
        List<Banner> banners = bannerService.getBannersByNotice(noticeId);
        return ResponseEntity.ok(banners);
    }

    /**
     * ID로 특정 배너 조회
     *
     * @param id 배너 ID (Path Variable)
     * @return ResponseEntity<Banner> - 조회된 배너 데이터
     */
    @GetMapping("/{id}")
    public ResponseEntity<Banner> getBannerById(@PathVariable("id") Long id) {
        Banner banner = bannerService.getBannerById(id);
        return ResponseEntity.ok(banner);
    }

    /**
     * 새로운 배너 추가
     *
     * @param banner 저장할 배너 데이터 (Request Body)
     * @return ResponseEntity<Banner> - 저장된 배너 데이터
     */
    @PostMapping
    public ResponseEntity<Banner> createBanner(@RequestBody Banner banner) {
        Banner savedBanner = bannerService.saveBanner(banner);
        return ResponseEntity.status(201).body(savedBanner); // 201 Created
    }

    /**
     * 특정 배너 수정
     *
     * @param id 배너 ID (Path Variable)
     * @param updatedBanner 수정할 배너 데이터 (Request Body)
     * @return ResponseEntity<Banner> - 수정된 배너 데이터
     */
    @PutMapping("/{id}")
    public ResponseEntity<Banner> updateBanner(
            @PathVariable("id") Long id,
            @RequestBody Banner updatedBanner
    ) {
        Banner banner = bannerService.updateBanner(id, updatedBanner);
        return ResponseEntity.ok(banner);
    }

    /**
     * 특정 배너 삭제
     *
     * @param id 삭제할 배너 ID (Path Variable)
     * @return ResponseEntity<Void> - 본문 없이 HTTP 상태 코드만 반환
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable("id") Long id) {
        bannerService.deleteBanner(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
