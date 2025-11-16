package com.ganesh.recipe.service;

import com.ganesh.recipe.entity.Recipe;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.data.domain.Page;

import java.io.File;
import java.util.List;

public interface RecipeService {
    public void saveDataToDB() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException;

    public List<Recipe> fetchRecipe(Long page, Long limit);

    public Page<Recipe> filterRecipes(String calories, String title, String cuisine, String totalTime, String rating, int page, int limit);
}
