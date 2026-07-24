package com.mealmate.themealdb_api.dto.request;

import java.util.List;
import java.util.Set;

import com.mealmate.themealdb_api.domain.enums.Difficulty;
import com.mealmate.themealdb_api.domain.enums.RecipeType;
import com.mealmate.themealdb_api.domain.enums.TagRegime;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RecipeRequestDto {
    @NotBlank(message = "Le titre est obligatoire")
    private String title;

    private String description;
    
    @NotBlank(message = " Les instructions sont obligatoires")
    private String instructions;

    @PositiveOrZero
    private Integer preparationTime;

    @PositiveOrZero
    private Integer cookTime;

    @NotNull
    @Positive
    private Integer baseServings;

    private Difficulty difficulty;

    @NotNull(message = "Le type de recette est obligatoire")
    private RecipeType recipeType;

    private Set<TagRegime> tagRegimes;
    
    private String imageUrl;
    
    @NotEmpty(message = "une recette doit avoir au moins un ingredient")
    @Valid
    private List<RecipeIngredientRequestDto> ingredients;
 
}
