package com.megamaker.myportalnewsbatch.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
public class NaverArticle {
    private final String title;

    private final String originalLink;

    private final String description;

    private final Timestamp pubDate;

    private final String source;

    @Builder
    public NaverArticle(String title, @JsonProperty("originallink") String originalLink, String description, String pubDate) {
        // 네이버에서 검색어에 볼드처리 태그 달아서 없애줌
        this.title = title.replaceAll("</*b>", "").replace("&quot;", "\"");
        this.originalLink = originalLink;
        this.description = description.replaceAll("</*b>", "").replace("&quot;", "\"");

        // 날짜 timestamp 형식으로 변경
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        ZonedDateTime zdt = ZonedDateTime.parse(pubDate, formatter);
        this.pubDate = Timestamp.from(zdt.toInstant());
        this.source = "naver";
    }
}



