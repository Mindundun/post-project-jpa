package com.example.post_project_jpa.domain;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString(exclude = "files")
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String writer;

    private String contents;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

    // 파일
    @Builder.Default // builder 패턴 사용 시 null로 초기화 되지 말라고 작성
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.ALL)
    private List<ArticleFile> files = new ArrayList<>();


    // 비즈니스 메소드
    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContents(String contents){
        this.contents = contents;
    }

    public void changeWriter(String writer){
        this.writer = writer;
    }

    // 연관관계 메소드(양방향일때 필수)
    // public void addFile(ArticleFile file) {
    //     this.files.add(file);
    //     file.setArticle(this);
    // }

    public void addArticleFile(List<ArticleFile> files) {
        // List에 있는 값을 한 개씩 처리
        // 만약 this.files.add(file); 처리 시 2차원 배열로 저장되어버림
        this.files.addAll(files);

        files.forEach(file -> {
            file.setArticle(this);
        });
    }
}
