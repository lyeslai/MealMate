package com.mealmate.themealdb_api.dto.external.mealdb;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MealDbSearchResponse {
    private List<MealDbDto> meals;
}
