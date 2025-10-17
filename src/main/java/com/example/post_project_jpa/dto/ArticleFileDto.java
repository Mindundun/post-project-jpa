package com.example.post_project_jpa.dto;

import com.example.post_project_jpa.domain.Article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleFileDto {
    private Long id;

    private String fileName;

    private String filePath;

    private Long fileSize;

    private Long article;
}
