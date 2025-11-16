package com.ganesh.recipe.batch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ganesh.recipe.entity.Nutrients;
import com.ganesh.recipe.entity.Recipe;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RecipeMapper {

    private final ObjectMapper mapper = new ObjectMapper();

    public List<Recipe> mapToRecipes(Map<String, Object> rawData) {
        List<Recipe> list = new ArrayList<>();

        for (Object value : rawData.values()) {
            Map<String, Object> recipeMap = (Map<String, Object>) value;

            // Extract top-level fields
            Recipe recipe = new Recipe();
            recipe.setCuisine((String) recipeMap.get("cuisine"));
            recipe.setTitle((String) recipeMap.get("title"));
            recipe.setRating(convertToFloat(recipeMap.get("rating")));
            recipe.setPrep_time(convertToInt(recipeMap.get("prep_time")));
            recipe.setCook_time(convertToInt(recipeMap.get("cook_time")));
            recipe.setTotal_time(convertToInt(recipeMap.get("total_time")));
            recipe.setServes((String) recipeMap.get("serves"));

            // Extract nutrients
            Map<String, Object> nutriMap = (Map<String, Object>) recipeMap.get("nutrients");
            Nutrients nutrients = mapper.convertValue(nutriMap, Nutrients.class);
            recipe.setNutrients(nutrients);

            list.add(recipe);
        }

        return list;
    }

    private Float convertToFloat(Object obj){
        return obj == null ? null : Float.valueOf(obj.toString());
    }

    private Integer convertToInt(Object obj){
        return obj == null ? null : Integer.valueOf(obj.toString());
    }
}
