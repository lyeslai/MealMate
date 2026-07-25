package com.mealmate.themealdb_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mealmate.themealdb_api.domain.entity.Ingredient;
import com.mealmate.themealdb_api.dto.request.IngredientRequestDto;
import com.mealmate.themealdb_api.dto.response.IngredientResponseDto;
import com.mealmate.themealdb_api.exception.ResourceNotFoundException;
import com.mealmate.themealdb_api.repository.IngredientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public List<IngredientResponseDto> findAll() {
        return ingredientRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public IngredientResponseDto findById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingrédient introuvable avec l'id " + id));
        return toDto(ingredient);
    }

    public IngredientResponseDto create(IngredientRequestDto dto) {
        // Normalisation basique du nom pour éviter les doublons
        String normalizedName = dto.getName().trim().toLowerCase();

        Ingredient ingredient = ingredientRepository.findByNameIgnoringCase(normalizedName)
                .orElseGet(Ingredient::new);

        ingredient.setName(normalizedName);
        ingredient.setCaloriesPer100g(dto.getCaloriesPer100g());
        ingredient.setProteinPer100g(dto.getProteinPer100g());
        ingredient.setCarbsPer100g(dto.getCarbsPer100g());
        ingredient.setFatPer100g(dto.getFatPer100g());

        return toDto(ingredientRepository.save(ingredient));
    }

    public void deleteById(Long id) {
        if (!ingredientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ingrédient introuvable avec l'id " + id);
        }
        ingredientRepository.deleteById(id);
    }

    private IngredientResponseDto toDto(Ingredient ingredient) {
        return IngredientResponseDto.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .caloriesPer100g(ingredient.getCaloriesPer100g())
                .proteinPer100g(ingredient.getProteinPer100g())
                .carbsPer100g(ingredient.getCarbsPer100g())
                .fatPer100g(ingredient.getFatPer100g())
                .build();
    }
}