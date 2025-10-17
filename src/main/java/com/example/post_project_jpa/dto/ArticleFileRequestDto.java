package com.example.post_project_jpa.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.post_project_jpa.domain.Article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleFileRequestDto {
    private Long id;
    private String title;
    private String writer;
    private String contents;
    private LocalDateTime regDate;

    private List<ArticleFileDto> files; // 연관된 파일 DTO 리스트


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ArticleFileDto { // static inner class
        private Long id;

        private String fileName;

        private String filePath;

        private Long fileSize;

        private Article article;
    }
}
