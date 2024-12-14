package com.ohgiraffers.recipeapp.controller;

import com.ohgiraffers.recipeapp.dto.MemberRequestDto;
import com.ohgiraffers.recipeapp.entity.Member;
import com.ohgiraffers.recipeapp.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberApiController {

    private final MemberService memberService;
    private final ModelMapper modelMapper;

    @PostMapping
    @Operation(summary = "회원 추가", description = "새로운 회원을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<Long> addMember(@Valid @RequestBody MemberRequestDto memberDto) {
        try {
            System.out.println("Received Member DTO: " + memberDto);
            Member member = modelMapper.map(memberDto, Member.class);
            System.out.println("Mapped Member: " + member);
            Long id = memberService.join(member);
            System.out.println("Saved Member ID: " + id);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (Exception e) {
            System.err.println("Error during member creation: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
