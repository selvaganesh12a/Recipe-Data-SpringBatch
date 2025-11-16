package com.ganesh.recipe.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cuisine;
    private String title;
    private Float rating;
    private Integer prep_time;
    private Integer cook_time;
    private Integer total_time;
    @Embedded // this acts as self join of two entity to form as single entity
    private Nutrients nutrients;
    private String serves;
}
