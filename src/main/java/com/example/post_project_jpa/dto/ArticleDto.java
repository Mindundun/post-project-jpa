package com.example.post_project_jpa.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDto {
    private Long id;
    private String title;
    private String writer;
    private String contents;
    private LocalDateTime regDate;

    @Builder.Default
    private List<ArticleFileDto> files = new ArrayList<>(); // 연관된 파일 DTO 리스트
}

    

