package com.mealmate.themealdb_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mealmate.themealdb_api.domain.entity.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends  JpaRepository<Ingredient, Long> {
    
    Optional<Ingredient> findByNameIgnoringCase(String name);
    
    List<Ingredient> findByCaloriesPer100g();
}
