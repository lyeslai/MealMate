package com.mealmate.themealdb_api.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.mealmate.themealdb_api.dto.external.mealdb.MealDbSearchResponse;

@Component
public class MealDbClient {

    private final RestClient restClient = RestClient.create("https://www.themealdb.com/api/json/v1/1");
    public MealDbSearchResponse searchByFirstLetter(char Letter) {
        return restClient.get().uri("/search.php?f={letter}", Letter).retrieve().body(MealDbSearchResponse.class);
    }    
}
