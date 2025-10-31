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

    // 게시글 목록 조회
    public List<ArticleDto> retrieveArticle();

    // 게시글 수정
    public ArticleDto modifyArticle(ArticleDto articleDto);

    // 게시글 삭제
    public void removeArticle(Long id);


    // default method
    default Article dtoToEntity(ArticleDto article){
        return Article.builder()
                .title(article.getTitle())
                .writer(article.getWriter())
                .contents(article.getContents())
                .regDate(article.getRegDate())
                .build();
    }

    default ArticleDto entityToDto(Article article){
        return ArticleDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .writer(article.getWriter())
                .contents(article.getContents())
                .regDate(article.getRegDate())
                .build();
    }
}