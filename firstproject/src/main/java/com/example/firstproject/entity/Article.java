package com.example.firstproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity // DB가 해당 객체를 인식!
@AllArgsConstructor
@NoArgsConstructor // 디폴트 생성자
@Getter
@Setter
@ToString
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // private key의 역할이고, DB가 id를 자동생성.
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    public void patch(Article article){
        if (article.getTitle() != null){
            this.title = article.getTitle();
        }
        if (article.getContent() != null){
            this.content = article.getContent();
        }
    }
}
