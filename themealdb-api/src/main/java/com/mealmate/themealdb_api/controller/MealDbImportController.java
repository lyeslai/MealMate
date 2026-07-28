package com.mealmate.themealdb_api.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mealmate.themealdb_api.service.MealDbImportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/import")
@RequiredArgsConstructor
public class MealDbImportController {
    private final MealDbImportService mealDbImportService;
    
    @PostMapping("/mealdb") 
    public ResponseEntity<MealDbImportService.ImportSummary> importFromDb() {
        MealDbImportService.ImportSummary summary = mealDbImportService.importAll();
        return ResponseEntity.ok(summary);
        }
}