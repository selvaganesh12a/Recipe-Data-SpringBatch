package com.ganesh.recipe.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService{

    private final JobLauncher jobLauncher;
    private final Job recipeJob;

    public RecipeServiceImpl(JobLauncher jobLauncher, Job recipeJob) {
        this.jobLauncher = jobLauncher;
        this.recipeJob = recipeJob;
    }

    @Override
    public void saveDataToDB() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt",System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(recipeJob,jobParameters);
    }
}
