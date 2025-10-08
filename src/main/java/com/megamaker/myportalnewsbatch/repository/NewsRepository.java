package com.megamaker.myportalnewsbatch.repository;

import com.megamaker.myportalnewsbatch.domain.NaverArticle;

import java.util.List;

public interface NewsRepository {

    int[] saveAll(List<NaverArticle> articleList);
}
