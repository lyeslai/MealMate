package com.mealmate.themealdb_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class IngredientRequestDto {
    
    @NotBlank(message ="Le nom de l'ingredient est obligatoire")
    private String name;

    private Double caloriesPer100g;
    private Double proteinPer100g;
    private Double fatPer100g;
    private Double carbsPer100g;
}
