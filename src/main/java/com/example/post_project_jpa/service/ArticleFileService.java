package com.example.post_project_jpa.service;

import org.springframework.stereotype.Service;

import com.example.post_project_jpa.domain.ArticleFile;
import com.example.post_project_jpa.dto.ArticleFileDto;

@Service
public class ArticleFileService {

    // DTO -> Entity
    public ArticleFile dtoToEntity(ArticleFileDto file) {
        return ArticleFile.builder()
                    .fileName(file.getFileName())
                    .fileSize(file.getFileSize())
                    .filePath(file.getFilePath())
                    .build();
    }

    public ArticleFileDto entityToDto(ArticleFile file){
        return  ArticleFileDto.builder()
                    .id(file.getId()) // 조회 시 ID 정보 필요
                    .fileName(file.getFileName())
                    .fileSize(file.getFileSize())
                    .filePath(file.getFilePath())
                    .articleId(file.getArticle().getId())
                    .build();
    }
} 