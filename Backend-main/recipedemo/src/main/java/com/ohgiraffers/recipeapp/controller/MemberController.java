package com.ohgiraffers.recipeapp.controller;

import com.ohgiraffers.recipeapp.entity.Member;
import com.ohgiraffers.recipeapp.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 모든 회원 조회
     *
     * @return ResponseEntity<List<Member>> - 모든 회원 목록과 HTTP 상태 코드
     */
    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    /**
     * ID로 특정 회원 조회
     *
     * @param id 회원 ID (Path Variable)
     * @return ResponseEntity<Member> - 조회된 회원 데이터와 HTTP 상태 코드
     */
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable("id") Long id) {
        Member member = memberService.getMemberById(id);
        return ResponseEntity.ok(member);
    }

    /**
     * 새로운 회원 추가
     *
     * @param member 저장할 회원 데이터 (Request Body)
     * @return ResponseEntity<Member> - 저장된 회원 데이터와 HTTP 상태 코드
     */
    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        Member savedMember = memberService.saveMember(member);
        return ResponseEntity.status(201).body(savedMember); // 201 Created
    }

    /**
     * 특정 회원 정보 수정
     *
     * @param id 수정할 회원 ID (Path Variable)
     * @param updatedMember 수정할 회원 데이터 (Request Body)
     * @return ResponseEntity<Member> - 수정된 회원 데이터와 HTTP 상태 코드
     */
    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(
            @PathVariable("id") Long id,
            @RequestBody Member updatedMember
    ) {
        Member member = memberService.updateMember(id, updatedMember);
        return ResponseEntity.ok(member);
    }

    /**
     * 특정 회원 삭제
     *
     * @param id 삭제할 회원 ID (Path Variable)
     * @return ResponseEntity<Void> - 본문 없이 HTTP 상태 코드만 반환
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable("id") Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    /**
     * 닉네임으로 회원 조회
     *
     * @param nickname 닉네임 (Query Parameter)
     * @return ResponseEntity<Member> - 조회된 회원 데이터와 HTTP 상태 코드
     */
    @GetMapping("/search")
    public ResponseEntity<Member> getMemberByNickname(@RequestParam(name = "nickname") String nickname) {
        Member member = memberService.getMemberByNickname(nickname);
        return ResponseEntity.ok(member);
    }
}
