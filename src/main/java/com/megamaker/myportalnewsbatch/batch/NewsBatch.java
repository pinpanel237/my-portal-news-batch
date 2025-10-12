package com.megamaker.myportalnewsbatch.batch;

import com.megamaker.myportalnewsbatch.domain.NaverArticle;
import com.megamaker.myportalnewsbatch.domain.dto.NaverApi;
import com.megamaker.myportalnewsbatch.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class NewsBatch {
    private final PlatformTransactionManager transactionManager;
    private final JobRepository jobRepository;
    private final NewsRepository newsRepository;
    private final RestTemplate restTemplate;

    @Value("${api.naver.x-naver-client-id}")
    private String xNaverClientId;
    @Value("${api.naver.x-naver-client-secret}")
    private String xNaverClientSecret;

    @Bean
    public Job newsJob(Step naverNewsStep, Step daumNewsStep) {
        return new JobBuilder("newsJob", jobRepository)
                .start(naverNewsStep)
                .next(daumNewsStep)
                .build();
    }

    @Bean
    public Step naverNewsStep() {
        return new StepBuilder("naverNewsStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    URI uri = UriComponentsBuilder
                            .fromUriString("https://openapi.naver.com/v1/search/news.json")
                            .queryParam("query", "오늘")  // 가능한 오늘 관련 뉴스만 가져오도록
                            .queryParam("sort", "date")  // 기사 날짜 내림차순으로
                            .queryParam("display", 100)  // 100개 기사 가져오기
                            .build()
                            .encode()
                            .toUri();

                    RequestEntity<Void> request = RequestEntity.get(uri)
                            .header("X-Naver-Client-Id", xNaverClientId)
                            .header("X-Naver-Client-Secret", xNaverClientSecret)
                            .build();

                    ResponseEntity<NaverApi> result = restTemplate.exchange(request, NaverApi.class);
                    List<NaverArticle> items = result.getBody().getItems();

                    // 응답 내용이 비어있지 않을 때
                    if (items != null && !items.isEmpty()) {
                        newsRepository.saveAll(items);  // 뉴스 기사 저장
                        log.debug("네이버 뉴스 저장 성공!");
                    } else {
                        log.error("네이버 뉴스 API 요청에 실패했습니다.");
                        log.error(result.getBody().toString());
                    }
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    @Bean
    public Step daumNewsStep() {
        return new StepBuilder("daumNewsStep", jobRepository)
                .tasklet(((contribution, chunkContext) -> {
                    return RepeatStatus.FINISHED;
                }), transactionManager)
                .build();
    }
}