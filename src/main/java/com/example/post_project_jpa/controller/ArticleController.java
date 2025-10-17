package com.example.post_project_jpa.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.post_project_jpa.dto.ArticleDto;
import com.example.post_project_jpa.dto.ArticleFileRequestDto;
import com.example.post_project_jpa.service.ArticleService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    // 게시글 등록
    @PostMapping("/articles")
    public ResponseEntity<Map<String, Long>> postArticle(
        @RequestPart(value = "article") ArticleDto articleDto,         // @RequestPart : json -> java 객체인 article 파싱
        @RequestPart(value = "files") List<MultipartFile> files) {      // MultipartFile : 스프링 프레임 워크에서 업로드된 파일을 처리하기 위한 인터페이스
        
        articleDto.setRegDate(LocalDateTime.now());
        
        Long id = articleService.registerArticle(articleDto, files);

        return ResponseEntity.ok().body(Map.of("id", id));
    }
    

}