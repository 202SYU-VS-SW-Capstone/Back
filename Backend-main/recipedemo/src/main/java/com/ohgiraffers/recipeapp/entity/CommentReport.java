package com.ohgiraffers.recipeapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "comment_reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id; // 신고 ID

    @ManyToOne
    @JoinColumn(name = "reporter_member_id", nullable = false)
    private Member reporter; // 신고한 회원

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment; // 신고된 댓글

    @Column(nullable = false, columnDefinition = "TEXT")
    private String reportTitle; // 신고 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String reportContent; // 신고 내용

    @Column(nullable = false, name = "reported_at")
    private LocalDate reportedAt; // 신고 날짜
}


