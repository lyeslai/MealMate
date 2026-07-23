package com.mealmate.themealdb_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mealmate.themealdb_api.domain.entity.RecipeIngredient;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {
    List<RecipeIngredient> findByRecipeId(Long recipeId);
}