package com.mealmate.themealdb_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserRequestDto {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    
}
