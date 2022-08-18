package com.lfpo.spaceflightnews.service;

import com.lfpo.spaceflightnews.model.ArticleModel;
import com.lfpo.spaceflightnews.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.awt.print.Pageable;
import java.util.Optional;

@Service
@Slf4j
@Component
@RequiredArgsConstructor
public class ArticleService {

    //private final SpaceIntegration spaceIntegration;
    private final ArticleRepository articleRepository;

    public Page<ArticleModel> list(Pageable pageable) {
        return articleRepository.findAll((org.springframework.data.domain.Pageable) pageable);
    }

    public ArticleModel detail(Long id) {
        Optional<ArticleModel> optional = articleRepository.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new NoResultException(String.format("Artigo não encontrado: %d", id));
    }

    public ArticleModel create(ArticleModel article) {
        Long maxArticleId = articleRepository.getMaxArticleId();
        article.setId(++maxArticleId);
        return articleRepository.save(article);

    }

    public ArticleModel update(ArticleModel article, Long id) {
        Optional<ArticleModel>  optional = articleRepository.findById(id);
        if(optional.isPresent()) {
            article.setId(id);
            return articleRepository.save(article);
        }
        throw new NoResultException(String.format("Artigo não encontrado: %d", id));
    }

    public void delete(ArticleModel article) {
        articleRepository.delete(article);
    }

 }



