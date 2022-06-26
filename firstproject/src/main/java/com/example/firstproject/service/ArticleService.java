package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service // 서비스 선언 (서비스 객체를 스프링부트에 생성)
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if (article.getId() != null){
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        Article articleEntity = dto.toEntity();
        Article target = articleRepository.findById(id).orElse(null);

        if(target == null || id != articleEntity.getId()){
            return null;
        }

        target.patch(articleEntity);
        return articleRepository.save(target);
    }

    public Article delete(Long id) {
        Article target = articleRepository.findById(id).orElse(null);

        if (target == null){
            return null;
        }

        articleRepository.delete(target);
        return target;
    }

    @Transactional // 해당 메서드를 Transaction으로 묶는다.
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // dto 묶음을 entity 묶음으로 변환
         List<Article> articleList = dtos.stream().map(dto -> dto.toEntity())
                .collect(Collectors.toList());
        // entity 묶음을 DB로 저장
        articleList.stream().forEach(article -> articleRepository.save(article));

        // 강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("Transaction Fail")
        );

        // 결과값 반환
        return articleList;
    }
}
