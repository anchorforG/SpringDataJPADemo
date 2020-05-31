package com.example.springdatajpa.repository;

import com.example.springdatajpa.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author G
 */
public interface ArticleRepository extends JpaRepository<Article, Integer> {
}
