package com.megamaker.myportalnewsbatch.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
public class BatchScheduler {
    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    public BatchScheduler(JobLauncher jobLauncher, JobRegistry jobRegistry) {
        this.jobLauncher = jobLauncher;
        this.jobRegistry = jobRegistry;
    }

    @Scheduled(cron = "0/5 * * * * *", zone = "Asia/Seoul")
    public void naverNewsBatchJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("id", System.currentTimeMillis())
                            .toJobParameters();

            jobLauncher.run(jobRegistry.getJob("newsJob"), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException | NoSuchJobException e) {
            throw new RuntimeException(e);
        }
    }
}
