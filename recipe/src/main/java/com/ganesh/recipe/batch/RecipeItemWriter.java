package com.ganesh.recipe.batch;

import com.ganesh.recipe.entity.Recipe;
import com.ganesh.recipe.repository.RecipeRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class RecipeItemWriter implements ItemWriter<Recipe> {
    private final RecipeRepository recipeRepository;

    public RecipeItemWriter(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void write(Chunk<? extends Recipe> chunk) throws Exception {
        recipeRepository.saveAll(chunk.getItems());
    }
}
