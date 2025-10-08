package com.megamaker.myportalnewsbatch.repository;

import com.megamaker.myportalnewsbatch.domain.NaverArticle;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class NewsJdbcRepository implements NewsRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public NewsJdbcRepository(DataSource mainDataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(mainDataSource);
    }

    @Override
    public int[] saveAll(List<NaverArticle> articleList) {
        String sql = "INSERT INTO news.articles (title, original_link, description, publish_date) VALUES(:title, :originalLink, :description, :pubDate);";
        SqlParameterSource[] sqlParameterSource = SqlParameterSourceUtils.createBatch(articleList.toArray());
        return jdbcTemplate.batchUpdate(sql, sqlParameterSource);
    }
}
