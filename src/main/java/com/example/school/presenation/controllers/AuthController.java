package com.example.school.presenation.controllers;

import com.example.school.application.services.AuthService;
import com.example.school.common.dto.ApiResponse;
import com.example.school.common.dto.AuthResponse;
import com.example.school.common.dto.UserDto;
import com.example.school.presenation.validators.LoginRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequestValidator request) {
        try {
            AuthResponse authResponse = authService.authenticate(request.getUsername(), request.getPassword());
            ApiResponse<AuthResponse> response = new ApiResponse<>(200, "Connexion réussie", authResponse);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<AuthResponse> response = new ApiResponse<>(401, "Nom d'utilisateur ou mot de passe incorrect", null);
            return ResponseEntity.status(401).body(response);
        }
    }

}
