package com.ganesh.recipe.batch;

import com.ganesh.recipe.entity.Recipe;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class RecipeItemProcessor implements ItemProcessor<Recipe,Recipe> {
    @Override
    public Recipe process(Recipe item) throws Exception {
        return item;
    }
}
