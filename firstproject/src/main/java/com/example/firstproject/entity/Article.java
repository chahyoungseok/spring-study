package com.example.firstproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity // DB가 해당 객체를 인식!
@AllArgsConstructor
@NoArgsConstructor // 디폴트 생성자
@Getter
@ToString
public class Article {

    @Id
    @GeneratedValue // private key의 역할이고, 1,2,3 자동생성해준다.
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

}
