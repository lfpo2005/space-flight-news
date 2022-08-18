package com.lfpo.spaceflightnews;

/*
 * Projeto Spring Boot inicializado com intellij IDE
 * As Seguintes tecnologias foram utilizadas
 *
 * - Spring Data JPA
 * - Hibernate
 * - Spring Web
 * - Postgres
 * - Lombok
 *
 * @author lfpo2005
 *
 * */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.lfpo.spaceflightnews.model.ArticleModel;
import com.lfpo.spaceflightnews.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

import static com.lfpo.spaceflightnews.commons.CommonsSingletons.instanceMapper;
import static com.lfpo.spaceflightnews.commons.CommonsSingletons.restTemplate;

@SpringBootApplication
public class SpaceFlightNewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpaceFlightNewsApplication.class, args);
    }


    @Component
    private static class ImportArticlesTask {


        Logger logger = LoggerFactory.getLogger(ImportArticlesTask.class);

        @Autowired
        private ArticleRepository articleRepository;

        @Value("${application.articles.batch-uri}")
        private String spaceFlightsArticlesURI;

        @Value("${application.articles.count-uri}")
        private String spaceFlightsArticlesCountURI;

        @Value("${application.articles.itens-per-page-import}")
        private int itensPerPageImport;

        @Value("${application.articles.itens-to-import}")
        private int itensToImport;

        @Value("${application.articles.import.active}")
        private boolean importActive;



        @PostConstruct
        public void importArticles() throws JsonProcessingException {
            if(!importActive) {return;}
            if (articleRepository.count() <= 0) {
                logger.info("Iniciando primeira importação de artigos.");
                //Free tier databases in Heroku are limited to 10k rows. Today, spaceflight api has 12k+ rows
                //Integer count = restTemplate().getForObject(spaceFlightsArticlesCountURI, Integer.class);
                Integer count = itensToImport;
                int pages = count / itensPerPageImport;
                int actualPage = 0;
                logger.info(String.format("Serão importados %d artigos em blocos de %d itens", count, itensPerPageImport));
                while(actualPage < pages) {
                    try {
                        logger.info(String.format(spaceFlightsArticlesURI, actualPage * itensPerPageImport));
                        String articlesJSON = restTemplate().getForObject(String.format(spaceFlightsArticlesURI, actualPage * itensPerPageImport),
                                String.class);
                        ArrayList<ArticleModel> articles = instanceMapper().readValue(articlesJSON, new TypeReference<ArrayList<ArticleModel>>() {});
                        articleRepository.saveAll(articles);
                        actualPage++;
                    } catch (Exception e) {
                        logger.error(String.format("Erro ao importar a página: %d",actualPage));
                        logger.error(e.getLocalizedMessage());
                    }
                }
                logger.info(String.format("Artigos importados com sucesso: %d ", count));
            }
        }

    }



//https://github.com/strapi/strapi/blob/master/LICENSE Direitos


}
