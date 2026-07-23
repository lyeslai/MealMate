package com.mealmate.themealdb_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mealmate.themealdb_api.domain.entity.Recipe;
import com.mealmate.themealdb_api.domain.enums.RecipeType;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByType(RecipeType type);

    List<Recipe> findByTitleContainingIgnoreCase(String title);

}
