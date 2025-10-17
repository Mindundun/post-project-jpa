package com.example.post_project_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.post_project_jpa.domain.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>{

    
} 
