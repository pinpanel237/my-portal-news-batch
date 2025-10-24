package com.megamaker.myportalnewsbatch.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.megamaker.myportalnewsbatch.domain.Article;
import com.megamaker.myportalnewsbatch.domain.ArticleConvertible;
import lombok.*;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@ToString
@Getter
@NoArgsConstructor
public class NaverApi {
    private String lastBuildDate;
    private Integer total;
    private Integer start;
    private Integer display;
    private List<NaverArticle> items;

    @Getter
    @NoArgsConstructor
    public static class NaverArticle implements ArticleConvertible {
        private String title;

        private String originalLink;

        private String description;

        private Timestamp pubDate;

        private static final DateTimeFormatter NAVER_DATE_FORMATTER =
                DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);

        @Builder
        public NaverArticle(String title, @JsonProperty("originallink") String originalLink,
                            String description, String pubDate) {
            // 네이버에서 검색어에 볼드처리 태그 달아서 없애줌
            this.title = title.replaceAll("</?b>", "").replace("&quot;", "\"");
            this.description = description.replaceAll("</?b>", "").replace("&quot;", "\"");
            this.originalLink = originalLink;

            // 날짜 timestamp 형식으로 변경
            ZonedDateTime zdt = ZonedDateTime.parse(pubDate, NAVER_DATE_FORMATTER);
            this.pubDate = Timestamp.from(zdt.toInstant());
        }

        @Override
        public Article toArticle() {
            return Article.builder()
                    .title(this.getTitle())
                    .originalLink(this.getOriginalLink())
                    .description(this.getDescription())
                    .pubDate(this.getPubDate())
                    .source("naver")
                    .build();
        }
    }
}
