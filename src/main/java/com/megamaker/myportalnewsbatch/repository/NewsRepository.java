package com.megamaker.myportalnewsbatch.repository;

import com.megamaker.myportalnewsbatch.domain.Article;

import java.util.List;

public interface NewsRepository {

    void saveAll(List<Article> articleList);
}
