package com.ohgiraffers.recipeapp.controller;

import com.ohgiraffers.recipeapp.entity.Member;
import com.ohgiraffers.recipeapp.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        // 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보 로그 출력
        Object principal = authentication.getPrincipal();
        System.out.println("Principal (User Details): " + authentication.getPrincipal());
        System.out.println("Authorities (Roles): " + authentication.getAuthorities());
        System.out.println("Is Authenticated: " + authentication.isAuthenticated());

        // 멤버 리스트 가져오기
        List<Member> members = memberService.findAllMembers();

        // 멤버 리스트와 Principal 정보 로그 출력
        System.out.println("Members List: " + members);

        // 멤버 리스트 반환
        return new ResponseEntity<>(members, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return memberService.findMemberById(id)
                .map(member -> new ResponseEntity<>(member, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        Member savedMember = memberService.saveMember(member);
        return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member member) {
        return new ResponseEntity<>(memberService.updateMember(id, member), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
