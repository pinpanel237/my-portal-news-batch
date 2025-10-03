package com.megamaker.myportalnewsbatch.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
public class BatchScheduler {
    private final Job naverNewsBatchJob;

    @Scheduled(cron = "0 0 * * * *")
    public void naverNewsBatchJob() {

    }
}
