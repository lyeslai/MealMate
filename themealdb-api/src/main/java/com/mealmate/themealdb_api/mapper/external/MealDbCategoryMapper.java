package com.mealmate.themealdb_api.mapper.external;

import java.util.Map;

import com.mealmate.themealdb_api.domain.enums.RecipeType;

public class MealDbCategoryMapper {
    
    private static final Map<String, RecipeType> MAPPING = Map.ofEntries(
        Map.entry("Breakfast", RecipeType.PETIT_DEJENEUNER),
        Map.entry("Dessert", RecipeType.DESSERT),
        Map.entry("Starter", RecipeType.ENTREE),
        Map.entry("Chicken", RecipeType.PLAT),
        Map.entry("Side", RecipeType.ENCAS),
        Map.entry("Lamb", RecipeType.PLAT),
        Map.entry("Beef", RecipeType.PLAT),
        Map.entry("Pork", RecipeType.PLAT),
        Map.entry("Goat", RecipeType.PLAT),
        Map.entry("Seafood", RecipeType.PLAT),
        Map.entry("Pasta", RecipeType.PLAT),
        Map.entry("Vegetarian", RecipeType.PLAT),
        Map.entry("Vegan", RecipeType.PLAT),
        Map.entry("Miscellaneous", RecipeType.AUTRE)
        
    );


    public static RecipeType mapCategories(String mealDbCategory) {
        if (mealDbCategory == null) return RecipeType.AUTRE;
        return MAPPING.getOrDefault(mealDbCategory, RecipeType.AUTRE);
    }
    private MealDbCategoryMapper() {
        // Private constructor to prevent instantiation
    }
}
