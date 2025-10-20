package com.example.post_project_jpa.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.post_project_jpa.domain.Article;
import com.example.post_project_jpa.dto.ArticleDto;
import com.example.post_project_jpa.dto.ArticleFileDto;
import com.example.post_project_jpa.dto.ArticleFileRequestDto;
import com.example.post_project_jpa.repository.ArticleRepository;
import com.example.post_project_jpa.util.FileUploadUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final FileUploadUtils fileUploadUtils;

    
    // 게시글 등록
    @Override
    @Transactional(readOnly = false)
    public Long registerArticle(ArticleDto articleDto, List<MultipartFile> files){

        // 1. MultipartFile 리스트를 실제 저장하고, 저장된 파일 정보로 ArticleFileDto 리스트 생성
        List<ArticleFileDto> uploadedFiles = fileUploadUtils.uploadFiles(files);
        
        // 2. ArticleDto에 업로드된 파일 리스트 세팅
        articleDto.setFiles(uploadedFiles);
        
        // 3. ArticleDto -> Article 엔티티 변환 (files 필드 포함)
        Article article = dtoToEntity(articleDto);
        
        // 4. Article 저장 (cascade 옵션에 의해 files도 함께 저장됨)
        articleRepository.save(article);
        
        // 5. 저장된 Article ID 반환
        return article.getId();
    }


    @Override
    public ArticleDto retrieveArticle(Long id) {

        Article article = articleRepository.findByArticleId(id)
            .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다. ID: " + id));

          
        return entityToDto(article);
    }
    
    
    
}
