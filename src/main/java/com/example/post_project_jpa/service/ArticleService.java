package com.example.post_project_jpa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.post_project_jpa.domain.Article;
import com.example.post_project_jpa.domain.ArticleFile;
import com.example.post_project_jpa.dto.ArticleDto;
import com.example.post_project_jpa.dto.ArticleFileDto;
import com.example.post_project_jpa.dto.ArticleFileRequestDto;

@Service
public interface ArticleService {
    
    // 게시글 등록
    public Long registerArticle(ArticleDto articleDto, List<MultipartFile> files);

    // 게시글 상세 조회
    public ArticleDto retrieveArticle(Long id);

    // default method
    default Article dtoToEntity(ArticleDto articleDto){
        List<ArticleFile> files = null;
        
        if (articleDto.getFiles() != null) {
            files = articleDto.getFiles().stream()
                .map(fileDto -> ArticleFile.builder()
                    .id(fileDto.getId())
                    .fileName(fileDto.getFileName())
                    .filePath(fileDto.getFilePath())
                    .fileSize(fileDto.getFileSize())
                    // .articleId(fileDto.getArticleId())
                    // 연관관계 설정 - article은 아직 만들어진 Article이 없으니까 나중에 세팅하거나, builder에선 제외
                    .build())
                .collect(Collectors.toList());
        }

        Article article = Article.builder()
            .id(articleDto.getId())
            .title(articleDto.getTitle())
            .contents(articleDto.getContents())
            .writer(articleDto.getWriter())
            .regDate(articleDto.getRegDate())
            .files(files != null ? files : new ArrayList<>())
            .build();

        // 양방향 연관관계일 경우 files 안의 article 필드도 세팅
        if (files != null) {
            files.forEach(file -> file.setArticleId(article));
        }

        return article;
    }

    default ArticleDto entityToDto(Article article){
        List<ArticleFileDto> fileDtos = article.getFiles().stream()
                                                .map(file -> ArticleFileDto.builder()
                                                    .id(file.getId())
                                                    .fileName(file.getFileName())
                                                    .filePath(file.getFilePath())
                                                    .fileSize(file.getFileSize())
                                                    // .articleId(file.getArticleId())
                                                    .build())
                                                .collect(Collectors.toList());

        return ArticleDto.builder()
            .id(article.getId())
            .title(article.getTitle())
            .contents(article.getContents())
            .writer(article.getWriter())
            .regDate(article.getRegDate())
            .files(fileDtos) 
            .build();
    }

}
