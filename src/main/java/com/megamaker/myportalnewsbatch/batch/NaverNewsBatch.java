package com.megamaker.myportalnewsbatch.batch;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
public class NaverNewsBatch {

    private final PlatformTransactionManager transactionManager;

    private final JobRepository jobRepository;

    public NaverNewsBatch(PlatformTransactionManager transactionManager, JobRepository jobRepository) {
        this.transactionManager = transactionManager;
        this.jobRepository = jobRepository;
    }

//    @Bean
//    public Job newsJob() {
//        return new JobBuilder("newsJob", jobRepository)
//                .start(naverNewsStep())
//                .build();
//    }
//
//    @Bean
//    public Step naverNewsStep() {
//
//    }

}