package com.mealmate.themealdb_api.dto.request;

import com.mealmate.themealdb_api.domain.enums.Unit;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RecipeIngredientRequestDto {
    @NotNull(message = "L'ingredient est obligatoire")
    private Long ingredientId;

    @NotNull
    @Positive(message = "La quantité doit etre positive")
    private Double quantity;

    @NotNull(message = "L'unité est obligatoire")
    private Unit unit;
}
