package com.mealmate.themealdb_api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserRequestDto {

    @NotBlank
    @Email(message = "Invalid email")
    private String email;

    @NotBlank
    @Size(min= 8, message = "Mot de passe doit avoir au minimum 8 caracteres")
    private String password;


    @NotBlank
    private String pseudo;
    
}
