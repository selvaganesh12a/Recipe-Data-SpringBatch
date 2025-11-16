package com.ganesh.recipe.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class Nutrients {
    private String calories;
    private String carbohydrateContent;
    private String cholesterolContent;
    private String fiberContent;
    private String proteinContent;
    private String saturatedFatContent;
    private String sodiumContent;
    private String sugarContent;
    private String fatContent;
    private String unsaturatedFatContent;
}
