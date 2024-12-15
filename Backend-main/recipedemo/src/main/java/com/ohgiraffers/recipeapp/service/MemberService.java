package com.ohgiraffers.recipeapp.service;

import com.ohgiraffers.recipeapp.entity.Member;
import com.ohgiraffers.recipeapp.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findMemberById(Long id) {
        return memberRepository.findById(id);
    }

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public Member updateMember(Long id, Member updatedMember) {
        return memberRepository.findById(id)
                .map(member -> {
                    member.setUsername(updatedMember.getUsername());
                    member.setEmail(updatedMember.getEmail());
                    member.setPassword(updatedMember.getPassword());
                    member.setRole(updatedMember.getRole());
                    return memberRepository.save(member);
                })
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }


    public Long join(Member member) {
        // 이메일 중복 확인
        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        // 권한 설정 (기본값: ROLE_USER)
        if (member.getRole() == null || member.getRole().isBlank()) {
            member.setRole("ROLE_USER");
        }
        // 권한 받아서 하는 부분까지는 아직 무리라서 이 부분 빼고 연결하면 될듯!!!
        // 정리해서 올려드리기!!!

        // 회원 저장
        Member savedMember = memberRepository.save(member);

        // 저장된 회원의 ID 반환
        return savedMember.getId();
    }

}
