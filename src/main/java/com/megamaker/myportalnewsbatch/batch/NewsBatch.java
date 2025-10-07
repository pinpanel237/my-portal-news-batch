package com.megamaker.myportalnewsbatch.batch;

import com.megamaker.myportalnewsbatch.domain.Article;
import com.megamaker.myportalnewsbatch.repository.NewsRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
public class NewsBatch {
    private final PlatformTransactionManager transactionManager;
    private final JobRepository jobRepository;
    private final NewsRepository newsRepository;

    public NewsBatch(PlatformTransactionManager transactionManager, JobRepository jobRepository, NewsRepository newsRepository) {
        this.transactionManager = transactionManager;
        this.jobRepository = jobRepository;
        this.newsRepository = newsRepository;
    }

    @Bean
    public Job newsJob(Step naverNewsStep) {
        return new JobBuilder("newsJob", jobRepository)
                .start(naverNewsStep)
                .build();
    }

    @Bean
    public Step naverNewsStep() {
        return new StepBuilder("naverNewsStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    Article test = Article.builder()  // 테스트 데이터
                            .title("asdf")
                            .build();
                    newsRepository.save(test);  // 뉴스 기사 저장
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

}