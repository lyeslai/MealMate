package com.mealmate.themealdb_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mealmate.themealdb_api.domain.entity.User;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsEyEmail(String email);
}
