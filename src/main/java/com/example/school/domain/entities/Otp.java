package com.example.school.domain.entities;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entité OTP pour la vérification et la réinitialisation de mot de passe
 */
@Data
public class Otp {
    private UUID id;
    private UUID userId;
    private String code; // Code OTP à 6 chiffres
    private LocalDateTime expiresAt; // Date d'expiration (généralement 10-15 minutes)
    private boolean verified = false; // Indique si l'OTP a été vérifié avec succès
    private LocalDateTime verifiedAt; // Date de vérification
    private String purpose; // "PASSWORD_RESET", "ACCOUNT_CREATION", etc.
}

