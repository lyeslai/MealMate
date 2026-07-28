package com.mealmate.themealdb_api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mealmate.themealdb_api.client.MealDbClient;
import com.mealmate.themealdb_api.domain.entity.Ingredient;
import com.mealmate.themealdb_api.domain.entity.Recipe;
import com.mealmate.themealdb_api.domain.entity.RecipeIngredient;
import com.mealmate.themealdb_api.dto.external.mealdb.MealDbDto;
import com.mealmate.themealdb_api.dto.external.mealdb.MealDbSearchResponse;
import com.mealmate.themealdb_api.mapper.external.MealDbCategoryMapper;
import com.mealmate.themealdb_api.mapper.external.MealDbMeasureParser;
import com.mealmate.themealdb_api.mapper.external.MealDbMeasureParser.ParsedMeasure;
import com.mealmate.themealdb_api.repository.IngredientRepository;
import com.mealmate.themealdb_api.repository.RecipeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MealDbImportService {
    private final MealDbClient mealDbClient;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    public ImportSummary importAll(){
        int totalImported = 0;
        int totalSkipped = 0; 
        
        for (char letter = 'a'; letter <='z'; letter++) {
            MealDbSearchResponse response = mealDbClient.searchByFirstLetter(letter);
            if (response == null || response.getMeals() == null){
                continue;
            }

            for (MealDbDto mealDto : response.getMeals()) {
                try {
                    if (recipeAlreadyImported(mealDto.getStrMeal())) {
                        totalSkipped++;
                        continue;
                    }
                    importSingleMeal(mealDto);
                    totalImported++;
                } catch (Exception e)  {
                    log.warn ("Echec de l'import de la rcette '{}' : {}", mealDto.getStrMeal(), e);
                    totalSkipped++;
                }
            }
        }
        return new ImportSummary(totalImported, totalSkipped);

    }
    
    @Transactional
    protected void importSingleMeal(MealDbDto mealDto) {
        Recipe recipe = Recipe.builder()
        .title(mealDto.getStrMeal())
        .instructions(mealDto.getStrInstructions())
        .imageUrl(mealDto.getStrMealThumb())
        .recipeType(MealDbCategoryMapper.mapCategories(mealDto.getStrCategory()))
        .baseServings(4).build();
    

    List<RecipeIngredient> recipeIngredients = new ArrayList<>();

    for (String[] pair : mealDto.getIngredientsAndMeasures()) {
            String rawName = pair[0];
            String rawMeasure = pair[1];

            Ingredient ingredient = findOrCreateIngredient(rawName);
            ParsedMeasure parsed = MealDbMeasureParser.parse(rawMeasure);

            RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                    .recipe(recipe)
                    .ingredient(ingredient)
                    .quantity(parsed.quantity())
                    .unit(parsed.unit())
                    .build();

            recipeIngredients.add(recipeIngredient);
        }
        recipe.setRecipeIngredients(recipeIngredients);
        recipeRepository.save(recipe);
    }
    private Ingredient findOrCreateIngredient(String rawName) {
        String normalized = normalize(rawName);

        return ingredientRepository.findByNameIgnoringCase(normalized)
                .orElseGet(() -> ingredientRepository.save(
                        Ingredient.builder().name(normalized).build()
                ));
    }


    private String normalize(String name) {
        return name.trim().toLowerCase();
    }

    private boolean recipeAlreadyImported(String title) {
        return !recipeRepository.findByTitleContainingIgnoreCase(title).isEmpty();
    }

    public record ImportSummary(int imported, int skipped) {}
}
