package com.megamaker.myportalnewsbatch.domain.dto;

import com.megamaker.myportalnewsbatch.domain.NaverArticle;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@RequiredArgsConstructor
public class NaverApi {
    private final String lastBuildDate;
    private final Integer total;
    private final Integer start;
    private final Integer display;
    private final List<NaverArticle> items;
}
