package com.mealmate.themealdb_api.dto.response;

import java.util.List;
import java.util.Set;

import com.mealmate.themealdb_api.domain.enums.Difficulty;
import com.mealmate.themealdb_api.domain.enums.RecipeType;
import com.mealmate.themealdb_api.domain.enums.TagRegime;

import lombok.*;


@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RecipeResponseDto {
    private Long id;
    private String title;
    private String description;
    private String instructions;
    private Integer preparationTime;
    private Integer cookTime;
    private Integer baseServings;
    private Difficulty difficulty;
    private RecipeType recipeType;
    private Set<TagRegime> tagRegimes;
    private String imageUrl;
    private Double caloriePerServings;
    private List<RecipeIngredientResponseDto> ingredients;
}
