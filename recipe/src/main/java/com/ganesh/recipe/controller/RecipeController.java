package com.ganesh.recipe.controller;

//import com.ganesh.recipe.entity.Recipe;
import com.ganesh.recipe.entity.Recipe;
import com.ganesh.recipe.service.RecipeService;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @PostMapping("/saveJson")
    public void saveDataToDB() throws IOException, JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        recipeService.saveDataToDB();
    }

    @GetMapping("/recipes")
    public List<Recipe> fetchRecipe(@RequestParam("page") Long page, @RequestParam("limit") Long limit){
        return recipeService.fetchRecipe(page,limit);
    }

    @GetMapping("/recipes/search")
    public Page<Recipe> fetchRecipeByFilters(@RequestParam(required = false) String calories,
                                             @RequestParam(required = false) String title,
                                             @RequestParam(required = false) String cuisine,
                                             @RequestParam(required = false) String total_time,
                                             @RequestParam(required = false) String rating,
                                             @RequestParam("page") int page,
                                             @RequestParam("limit") int limit){
        return recipeService.filterRecipes(
                calories,
                title,
                cuisine,
                total_time,
                rating,
                page,
                limit
        );
    }
}
