package com.megamaker.myportalnewsbatch.repository;

import com.megamaker.myportalnewsbatch.domain.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class NewsJdbcRepository implements NewsRepository {
    private final SimpleJdbcInsert jdbcInsert;

    public NewsJdbcRepository(DataSource mainDataSource) {
        this.jdbcInsert = new SimpleJdbcInsert(mainDataSource)
                .withSchemaName("news")
                .withTableName("articles")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public long save(Article article) {
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(article);
        Number number = jdbcInsert.executeAndReturnKey(param);
        return number.longValue();
    }
}
