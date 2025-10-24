package com.megamaker.myportalnewsbatch.domain;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class Article {
    private final String title;

    private final String originalLink;

    private final String description;

    private final Timestamp pubDate;

    private final String source;
}
