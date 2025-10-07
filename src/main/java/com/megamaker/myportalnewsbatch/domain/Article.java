package com.megamaker.myportalnewsbatch.domain;

import lombok.Builder;

@Builder
public record Article(String title, String originalLink, String description, String pubDate) {
}
