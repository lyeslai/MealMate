package com.mealmate.themealdb_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mealmate.themealdb_api.domain.enums.RecipeType;
import com.mealmate.themealdb_api.dto.request.RecipeRequestDto;
import com.mealmate.themealdb_api.dto.response.RecipeResponseDto;
import com.mealmate.themealdb_api.service.RecipeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping
    public ResponseEntity<List<RecipeResponseDto>> findAll(
            @RequestParam(required = false) RecipeType type,
            @RequestParam(required = false) String search) {

        if (search != null && !search.isBlank()) {
            return ResponseEntity.ok(recipeService.searchByTitle(search));
        }
        if (type != null) {
            return ResponseEntity.ok(recipeService.findByType(type));
        }
        return ResponseEntity.ok(recipeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RecipeResponseDto> create(@Valid @RequestBody RecipeRequestDto dto) {
        RecipeResponseDto created = recipeService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponseDto> update(@PathVariable Long id, @Valid @RequestBody RecipeRequestDto dto) {
        return ResponseEntity.ok(recipeService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recipeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}