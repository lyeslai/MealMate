package com.mealmate.themealdb_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mealmate.themealdb_api.domain.entity.Ingredient;
import com.mealmate.themealdb_api.domain.entity.Recipe;
import com.mealmate.themealdb_api.domain.entity.RecipeIngredient;
import com.mealmate.themealdb_api.domain.enums.RecipeType;
import com.mealmate.themealdb_api.dto.request.RecipeIngredientRequestDto;
import com.mealmate.themealdb_api.dto.request.RecipeRequestDto;
import com.mealmate.themealdb_api.dto.response.RecipeResponseDto;
import com.mealmate.themealdb_api.exception.ResourceNotFoundException;
import com.mealmate.themealdb_api.mapper.RecipeMapper;
import com.mealmate.themealdb_api.repository.IngredientRepository;
import com.mealmate.themealdb_api.repository.RecipeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeMapper recipeMapper;

    public List<RecipeResponseDto> findAll() {
        return recipeRepository.findAll().stream()
                .map(recipeMapper::toDto)
                .toList();
    }

    public RecipeResponseDto findById(Long id) {
        Recipe recipe = getRecipeOrThrow(id);
        return recipeMapper.toDto(recipe);
    }

    public List<RecipeResponseDto> findByType(RecipeType recipeType) {
        return recipeRepository.findByRecipeType(recipeType).stream()
                .map(recipeMapper::toDto)
                .toList();
    }

    public List<RecipeResponseDto> searchByTitle(String title) {
        return recipeRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(recipeMapper::toDto)
                .toList();
    }

    @Transactional
    public RecipeResponseDto create(RecipeRequestDto dto) {
        Recipe recipe = Recipe.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .instructions(dto.getInstructions())
                .preparationTime(dto.getPreparationTime())
                .cookTime(dto.getCookTime())
                .baseServings(dto.getBaseServings())
                .difficulty(dto.getDifficulty())
                .recipeType(dto.getRecipeType())
                .tagRegimes(dto.getTagRegimes())
                .imageUrl(dto.getImageUrl())
                .build();

        attachIngredients(recipe, dto.getIngredients());

        Recipe saved = recipeRepository.save(recipe);
        return recipeMapper.toDto(saved);
    }

    @Transactional
    public RecipeResponseDto update(Long id, RecipeRequestDto dto) {
        Recipe recipe = getRecipeOrThrow(id);

        recipe.setTitle(dto.getTitle());
        recipe.setDescription(dto.getDescription());
        recipe.setInstructions(dto.getInstructions());
        recipe.setPreparationTime(dto.getPreparationTime());
        recipe.setCookTime(dto.getCookTime());
        recipe.setBaseServings(dto.getBaseServings());
        recipe.setDifficulty(dto.getDifficulty());
        recipe.setRecipeType(dto.getRecipeType());
        recipe.setTagRegimes(dto.getTagRegimes());
        recipe.setImageUrl(dto.getImageUrl());

        // On repart de zéro sur les ingrédients (orphanRemoval s'occupe du nettoyage)
        recipe.getRecipeIngredients().clear();
        attachIngredients(recipe, dto.getIngredients());

        Recipe saved = recipeRepository.save(recipe);
        return recipeMapper.toDto(saved);
    }

    public void deleteById(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recette introuvable avec l'id " + id);
        }
        recipeRepository.deleteById(id);
    }

    private void attachIngredients(Recipe recipe, List<RecipeIngredientRequestDto> ingredientDtos) {
        for (RecipeIngredientRequestDto ingredientDto : ingredientDtos) {
            Ingredient ingredient = ingredientRepository.findById(ingredientDto.getIngredientId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Ingrédient introuvable avec l'id " + ingredientDto.getIngredientId()));

            RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                    .recipe(recipe)
                    .ingredient(ingredient)
                    .quantity(ingredientDto.getQuantity())
                    .unit(ingredientDto.getUnit())
                    .build();

            recipe.getRecipeIngredients().add(recipeIngredient);
        }
    }

    private Recipe getRecipeOrThrow(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recette introuvable avec l'id " + id));
    }
}