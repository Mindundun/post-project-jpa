package com.example.post_project_jpa.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.post_project_jpa.domain.Article;
import com.example.post_project_jpa.domain.ArticleFile;
import com.example.post_project_jpa.dto.ArticleDto;
import com.example.post_project_jpa.dto.ArticleFileDto;
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
    private final ArticleFileService articleFileService;
    private final FileUploadUtils fileUploadUtils;

    
    // 게시글 등록
    @Override
    @Transactional(readOnly = false)
    public Long registerArticle(ArticleDto articleDto, List<MultipartFile> files){

        List<ArticleFileDto> uploadedFiles = fileUploadUtils.uploadFiles(files);

        Article article = dtoToEntity(articleDto);
        
        List<ArticleFile> articleFiles =  uploadedFiles.stream().map(articleFileService::dtoToEntity).collect(Collectors.toList());

        article.addArticleFile(articleFiles);

        return articleRepository.save(article).getId();
    }


    @Override
    public ArticleDto retrieveArticle(Long id) {

        Article article = articleRepository.findByArticleId(id)
            .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다. ID: " + id));
        
        List<ArticleFile> files = article.getFiles();

        ArticleDto articleDto = entityToDto(article);

        List<ArticleFileDto> articleFiles = files.stream().map(articleFileService::entityToDto).collect(Collectors.toList());

        articleDto.getFiles().addAll(articleFiles);

        // log.info("============article : {}",article);  // @ToString 덕분에 대부분 필드 출력됨
        // log.info("============article.getFiles() : {}",article.getFiles());// 연관 컬렉션도 확인

        return articleDto;
    }

    // 게시글 전체 조회
    @Override
    public List<ArticleDto> retrieveArticle() {
        
        List<Article> articles = articleRepository.findAll();


        // 2. Article → ArticleDto 변환 및 파일 포함
        List<ArticleDto> articleDtos = articles.stream().map(article -> {
            ArticleDto articleDto = entityToDto(article); // 게시글 DTO 변환

            // 게시글 파일 변환
            List<ArticleFileDto> fileDtos = article.getFiles().stream()
                    .map(articleFileService::entityToDto)
                    .collect(Collectors.toList());

            articleDto.getFiles().addAll(fileDtos);

            return articleDto;
        }).collect(Collectors.toList());
        

        return articleDtos;
    }

    // 게시글 수정
    @Override
    @Transactional(readOnly = false)
    public ArticleDto modifyArticle(ArticleDto articleDto) {
        // TODO Auto-generated method stub
        return null;
    }

    // 게시글 삭제
    @Override
    @Transactional(readOnly = false)
    public void removeArticle(Long id) {
        
        articleRepository.findByArticleId(id)
            .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다. ID: " + id));

        articleRepository.deleteById(id);
    }
    
    
    
    
    
}


