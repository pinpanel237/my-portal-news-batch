package com.megamaker.myportalnewsbatch.repository;

import com.megamaker.myportalnewsbatch.domain.Article;

public interface NewsRepository {

    long save(Article article);
}
