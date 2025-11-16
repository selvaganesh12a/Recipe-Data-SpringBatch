package com.ganesh.recipe.batch;

import com.ganesh.recipe.entity.Recipe;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class RecipeItemReader {
    private final JsonMapLoader loader;
    private final RecipeMapper mapper;

    public RecipeItemReader(JsonMapLoader loader, RecipeMapper mapper) {
        this.loader = loader;
        this.mapper = mapper;
    }

    @Bean
    public ListItemReader<Recipe> jsonItemReader() throws Exception {

        Map<String, Object> rawJson = loader.loadJsonAsMap("recipes.json");

        List<Recipe> recipes = mapper.mapToRecipes(rawJson);

        return new ListItemReader<>(recipes);
    }
}
