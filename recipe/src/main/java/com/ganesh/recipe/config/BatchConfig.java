package com.ganesh.recipe.config;

import com.ganesh.recipe.batch.RecipeItemProcessor;
import com.ganesh.recipe.batch.RecipeItemReader;
import com.ganesh.recipe.batch.RecipeItemWriter;
//import com.ganesh.recipe.entity.Recipe;
import com.ganesh.recipe.entity.Recipe;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    private final RecipeItemReader reader;
    private final RecipeItemProcessor processor;
    private final RecipeItemWriter writer;

    public BatchConfig(RecipeItemReader reader, RecipeItemProcessor processor, RecipeItemWriter writer) {
        this.reader = reader;
        this.processor = processor;
        this.writer = writer;
    }

    @Bean
    public Step recipeStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
        return new StepBuilder("recipeStep",jobRepository)
                .<Recipe, Recipe>chunk(10000,transactionManager)
                .reader(reader.jsonItemReader())
                .processor(processor)
                .writer(writer)
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public Job recipeJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
        return new JobBuilder("recipeJob",jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(recipeStep(jobRepository,transactionManager))
                .build();
    }
}
