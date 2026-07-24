package com.mealmate.themealdb_api.dto.response;

import com.mealmate.themealdb_api.domain.enums.Unit;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RecipeIngredientResponseDto {
    private Long ingredientId;
    private String ingredientName;
    private Double quantity;
    private Unit unit;
}
