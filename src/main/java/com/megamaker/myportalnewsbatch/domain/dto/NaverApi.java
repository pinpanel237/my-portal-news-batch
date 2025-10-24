package com.megamaker.myportalnewsbatch.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.megamaker.myportalnewsbatch.domain.Article;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@ToString
@Getter
@RequiredArgsConstructor
public class NaverApi {
    private final String lastBuildDate;
    private final Integer total;
    private final Integer start;
    private final Integer display;
    private final List<NaverArticle> items;

    public static Article toArticle(NaverArticle naverArticle) {
        return Article.builder()
                .title(naverArticle.getTitle())
                .originalLink(naverArticle.getOriginalLink())
                .description(naverArticle.getDescription())
                .pubDate(naverArticle.getPubDate())
                .source("naver")
                .build();
    }

    @Getter
    public static class NaverArticle {
        private final String title;

        private final String originalLink;

        private final String description;

        private final Timestamp pubDate;

        @Builder
        public NaverArticle(String title, @JsonProperty("originallink") String originalLink,
                            String description, String pubDate) {
            // 네이버에서 검색어에 볼드처리 태그 달아서 없애줌
            this.title = title.replaceAll("</*b>", "").replace("&quot;", "\"");
            this.description = description.replaceAll("</*b>", "").replace("&quot;", "\"");
            this.originalLink = originalLink;

            // 날짜 timestamp 형식으로 변경
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
            ZonedDateTime zdt = ZonedDateTime.parse(pubDate, formatter);
            this.pubDate = Timestamp.from(zdt.toInstant());
        }
    }

}
