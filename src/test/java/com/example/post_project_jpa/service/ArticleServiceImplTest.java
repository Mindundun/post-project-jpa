package com.example.post_project_jpa.service;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.post_project_jpa.dto.ArticleDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ArticleServiceImplTest {

    @Autowired
    private ArticleService articleService;

    // @Test
    // void testRegisterArticle() {
    //     // Given
    //     ArticleDto articleDto = ArticleDto.builder()
    //                                         .title("제발들어가줘")
    //                                         .contents("제발요ㅠㅜㅠㅠ")
    //                                         .writer("박민둔")
    //                                         .regDate(LocalDateTime.now())
    //                                         .build();


    //     // When

    //     Long id = articleService.registerArticle(articleDto);

    //     log.info("============================id : {} ", id);


    //     // Then
    //     assertThat(id).isNotNull();


    // }
}
