package com.ganesh.recipe.service;

import com.ganesh.recipe.entity.Recipe;
import com.ganesh.recipe.repository.RecipeRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService{

    private final JobLauncher jobLauncher;
    private final Job recipeJob;
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(JobLauncher jobLauncher, Job recipeJob, RecipeRepository recipeRepository) {
        this.jobLauncher = jobLauncher;
        this.recipeJob = recipeJob;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveDataToDB() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt",System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(recipeJob,jobParameters);
    }

    @Override
    public List<Recipe> fetchRecipe(Long page, Long limit) {
        if(page < 0) return new ArrayList<>();
        Pageable pageable = PageRequest.of(Math.toIntExact(page), Math.toIntExact(limit), Sort.by("rating").descending());
        Page<Recipe> entityPage = recipeRepository.findAll(pageable);
        return entityPage.getContent();
    }

    @Override
    public Page<Recipe> filterRecipes(String caloriesFilter,
                                      String title,
                                      String cuisine,
                                      String totalTimeFilter,
                                      String ratingFilter,
                                      int page,
                                      int limit) {

        Specification<Recipe> spec = Specification.where(null);

        // Title (LIKE)
        if (title != null && !title.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }

        // Cuisine (equals)
        if (cuisine != null && !cuisine.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(cb.lower(root.get("cuisine")), cuisine.toLowerCase()));
        }

        // Rating (>, <, >=, <=)
        if (ratingFilter != null && !ratingFilter.isEmpty()) {
            FilterCondition filter = parseFilter(ratingFilter);
            spec = spec.and((root, query, cb) ->
                    buildNumberPredicate(cb, root.get("rating"), filter));
        }

        // Total Time
        if (totalTimeFilter != null && !totalTimeFilter.isEmpty()) {
            FilterCondition filter = parseFilter(totalTimeFilter);
            spec = spec.and((root, query, cb) ->
                    buildNumberPredicate(cb, root.get("total_time"), filter));
        }

        // Calories (inside embedded Nutrients)
        if (caloriesFilter != null && !caloriesFilter.isEmpty()) {
            FilterCondition filter = parseFilter(caloriesFilter);
            spec = spec.and((root, query, cb) ->
                    buildNumberPredicate(cb,
                            root.get("nutrients").get("calories"),
                            filter));
        }

        Pageable pageable = PageRequest.of(page,limit);
        return recipeRepository.findAll(spec,pageable);
    }

    private static class FilterCondition {
        String operator;
        String value;
        public FilterCondition(String operator, String value) {
            this.operator = operator;
            this.value = value;
        }
    }

    // Helper → Parse operators like <=400 , >=4.5 , >300 etc.
    private FilterCondition parseFilter(String expression) {
        String op;
        String val;

        if (expression.startsWith(">=")) { op = ">="; val = expression.substring(2); }
        else if (expression.startsWith("<=")) { op = "<="; val = expression.substring(2); }
        else if (expression.startsWith(">")) { op = ">"; val = expression.substring(1); }
        else if (expression.startsWith("<")) { op = "<"; val = expression.substring(1); }
        else { op = "="; val = expression; }

        return new FilterCondition(op, val.trim());
    }

    // Generic Predicate Builder
    private Predicate buildNumberPredicate(CriteriaBuilder cb,
                                           Path<?> path,
                                           FilterCondition filter) {

        Double num = Double.valueOf(
                filter.value.replaceAll("[^0-9.]", "") // handles "389 kcal"
        );

        return switch (filter.operator) {
            case ">" -> cb.greaterThan(path.as(Double.class), num);
            case "<" -> cb.lessThan(path.as(Double.class), num);
            case ">=" -> cb.greaterThanOrEqualTo(path.as(Double.class), num);
            case "<=" -> cb.lessThanOrEqualTo(path.as(Double.class), num);
            default -> cb.equal(path.as(Double.class), num);
        };
    }
}
