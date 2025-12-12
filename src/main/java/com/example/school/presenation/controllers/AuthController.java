package com.example.school.presenation.controllers;

import com.example.school.application.services.AuthService;
import com.example.school.application.services.OtpService;
import com.example.school.common.dto.ApiResponse;
import com.example.school.common.dto.AuthResponse;
import com.example.school.common.exceptions.BusinessRuleException;
import com.example.school.presenation.validators.LoginRequestValidator;
import com.example.school.presenation.validators.ResetPasswordRequestValidator;
import com.example.school.presenation.validators.SendOtpRequestValidator;
import com.example.school.presenation.validators.VerifyOtpRequestValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService authService;
    private final OtpService otpService;

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

    /**
     * Envoie un OTP pour réinitialisation de mot de passe
     */
    @PostMapping("/send-otp")
    public ResponseEntity<ApiResponse<Void>> sendOtp(@Valid @RequestBody SendOtpRequestValidator request) {
        try {
            otpService.sendOtpForPasswordReset(request.getEmail());
            return ResponseEntity.ok(new ApiResponse<>(200, "Code OTP envoyé avec succès", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, "Erreur lors de l'envoi de l'OTP: " + e.getMessage(), null));
        }
    }

    /**
     * Vérifie un OTP et retourne un token JWT avec les informations utilisateur
     */
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<AuthResponse>> verifyOtp(@Valid @RequestBody VerifyOtpRequestValidator request) {
        try {
            AuthResponse authResponse = otpService.verifyOtp(request.getEmail(), request.getCode(), request.getPurpose());
            return ResponseEntity.ok(new ApiResponse<>(200, "OTP vérifié avec succès", authResponse));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, "Erreur lors de la vérification de l'OTP: " + e.getMessage(), null));
        }
    }

    /**
     * Réinitialise le mot de passe (nécessite un OTP vérifié - PASSWORD_RESET ou ACCOUNT_CREATION)
     */
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@Valid @RequestBody ResetPasswordRequestValidator request) {
        try {
            // Vérifier qu'un OTP valide a été vérifié pour cet utilisateur
            // Accepte soit PASSWORD_RESET soit ACCOUNT_CREATION
            if (!otpService.hasVerifiedOtp(request.getEmail())) {
                throw new BusinessRuleException("Vous devez d'abord vérifier un code OTP valide");
            }

            authService.resetPassword(request.getEmail(), request.getNewPassword());
            return ResponseEntity.ok(new ApiResponse<>(200, "Mot de passe réinitialisé avec succès", null));
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, "Erreur lors de la réinitialisation du mot de passe: " + e.getMessage(), null));
        }
    }

}
