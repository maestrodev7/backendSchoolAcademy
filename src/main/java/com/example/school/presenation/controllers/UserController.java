package com.example.school.presenation.controllers;

import com.example.school.common.dto.ApiResponse;
import com.example.school.common.dto.UserDto;
import com.example.school.domain.services.UserServiceInterface;
import com.example.school.presenation.validators.UserRequestValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceInterface userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserDto>> createUser(@Valid @RequestBody UserRequestValidator request) {
        UserDto user = userService.createUser(request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Utilisateur créé avec succès", user));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse<>(200, "Liste des utilisateurs récupérée avec succès", users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable String id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Utilisateur récupéré avec succès", user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(
            @PathVariable String id,
            @Valid @RequestBody UserRequestValidator request
    ) {
        UserDto updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Utilisateur mis à jour avec succès", updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Utilisateur supprimé avec succès", null));
    }
}
