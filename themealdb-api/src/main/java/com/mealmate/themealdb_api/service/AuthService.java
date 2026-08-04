package com.mealmate.themealdb_api.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mealmate.themealdb_api.config.JwtService;
import com.mealmate.themealdb_api.domain.entity.User;
import com.mealmate.themealdb_api.dto.request.LoginUserRequestDto;
import com.mealmate.themealdb_api.dto.request.RegisterUserRequestDto;
import com.mealmate.themealdb_api.dto.response.AuthResponseDto;
import com.mealmate.themealdb_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    

    public AuthResponseDto register(RegisterUserRequestDto requestDto) {
        if(userRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("Compte deja existant veuillez vous connecter");
        }
        User user = User.builder()
            .email(requestDto.getEmail())
            .password(passwordEncoder.encode(requestDto.getPassword()))
            .pseudo(requestDto.getPseudo())
            .build();
        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());
        return AuthResponseDto.builder()
            .token(token)
            .email(user.getEmail())
            .pseudo(user.getPseudo())
            .build();   
        }
    public AuthResponseDto login(LoginUserRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("Utilsateur non trouvé"));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Mot de passe incorrect");
        }

        String token = jwtService.generateToken(user.getEmail());
        return AuthResponseDto.builder()
            .token(token)
            .email(user.getEmail())
            .pseudo(user.getPseudo())
            .build();
    }

}
