package com.example.post_project_jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.post_project_jpa.domain.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>{
    // @Query("SELECT a FROM Article a WHERE a.id = :articleId") // 내부조인이 발생하여 추후 튜닝이 어려움
    @Query("SELECT a FROM Article a LEFT OUTER JOIN FETCH a.files WHERE a.id = :articleId")
    Optional<Article> findByArticleId(@Param("articleId") Long id);
    
} 
