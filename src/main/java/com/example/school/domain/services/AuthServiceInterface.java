package com.example.school.domain.services;

import com.example.school.common.dto.AuthResponse;

public interface AuthServiceInterface {
    AuthResponse authenticate(String username, String rawPassword);
    void resetPassword(String email, String newPassword);
}
