package com.mealmate.themealdb_api.dto.external.mealdb;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import lombok.*;

@Getter
@Setter
public class MealDbDto {
    
    private String idMeal;
    private String strMeal;
    private String strCategory;
    private String strArea;
    private String strInstructions;
    private String strMealThumb;


    private final Map<String, String> dynamicFields = new LinkedHashMap<>();

    @JsonAnySetter
    public void setDynamicField(String key, String value) {
        dynamicFields.put(key, value);
    }

    public List<String[]> getIngredientsAndMeasures() {
        List<String[]> ingredientsAndMeasures = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            String ingredientKey = "strIngredient" + i;
            String measureKey = "strMeasure" + i;

            String ingredient = dynamicFields.get(ingredientKey);
            String measure = dynamicFields.get(measureKey);

            if (ingredient != null && !ingredient.isEmpty()) {
                ingredientsAndMeasures.add(new String[]{ingredient, measure});
            }
        }

        return ingredientsAndMeasures;
    }
    


}
