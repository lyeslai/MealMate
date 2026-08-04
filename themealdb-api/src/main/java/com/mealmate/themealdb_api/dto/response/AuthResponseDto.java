package com.mealmate.themealdb_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AuthResponseDto {
    private String token;
    private String email;
    private String pseudo;
}
