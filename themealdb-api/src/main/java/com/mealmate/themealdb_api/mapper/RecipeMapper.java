package com.mealmate.themealdb_api.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mealmate.themealdb_api.domain.entity.Recipe;
import com.mealmate.themealdb_api.domain.entity.RecipeIngredient;
import com.mealmate.themealdb_api.dto.response.RecipeIngredientResponseDto;
import com.mealmate.themealdb_api.dto.response.RecipeResponseDto;

@Component
public class RecipeMapper {
    
    public RecipeResponseDto toDto(Recipe recipe) {
        List<RecipeIngredientResponseDto> ingredientDtos = recipe.getRecipeIngredients().stream()
                .map(this::toIngredientDto)
                .toList();

                return RecipeResponseDto.builder()
                        .id(recipe.getId())
                        .title(recipe.getTitle())
                        .description(recipe.getDescription())
                        .instructions(recipe.getInstructions())
                        .preparationTime(recipe.getPreparationTime())
                        .cookTime(recipe.getCookTime())
                        .baseServings(recipe.getBaseServings())
                        .difficulty(recipe.getDifficulty())
                        .recipeType(recipe.getRecipeType())
                        .tagRegimes(recipe.getTagRegimes())
                        .imageUrl(recipe.getImageUrl())
                        .caloriePerServings(recipe.getCaloriePerServing())
                        .ingredients(ingredientDtos)
                        .build();
    }

    private RecipeIngredientResponseDto toIngredientDto(RecipeIngredient recipeIngredient) {
        return RecipeIngredientResponseDto.builder()
                .ingredientId(recipeIngredient.getIngredient().getId())
                .ingredientName(recipeIngredient.getIngredient().getName())
                .quantity(recipeIngredient.getQuantity())
                .unit(recipeIngredient.getUnit())
                .build();
    }
}


