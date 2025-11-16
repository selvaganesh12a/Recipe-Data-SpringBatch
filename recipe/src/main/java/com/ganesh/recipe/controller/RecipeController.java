package com.ganesh.recipe.controller;

//import com.ganesh.recipe.entity.Recipe;
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

//    @GetMapping("/recipes")
//    public List<Recipe> fetchRecipe(@RequestParam("page") Long page, @RequestParam("limit") Long limit){
//        return recipeService.fetchRecipe(page,limit);
//    }
//
//    @GetMapping("/recipes/search")
//    public Page<Recipe> fetchRecipeByFilters(@RequestParam(defaultValue = "100",required = false) String calories,
//                                             @RequestParam(defaultValue = "title",required = false) String title,
//                                             @RequestParam(defaultValue = "100",required = false) String rating,
//                                             @RequestParam("page") Integer page,
//                                             @RequestParam("limit") Integer limit){
//        return recipeService.fetchRecipeByFilters(calories,title,rating,page,limit);
//    }
}
