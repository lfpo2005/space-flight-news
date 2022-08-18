package com.lfpo.spaceflightnews.repository;

import com.lfpo.spaceflightnews.model.ArticleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;

@Repository
@Transactional
public interface ArticleRepository extends JpaRepository<ArticleModel, Long> {

    @Query(value = "SELECT MAX(article_id) FROM article_model", nativeQuery = true)
    Long getMaxArticleId();
}


