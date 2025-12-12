package com.example.school.application.services;

import com.example.school.common.dto.AuthResponse;
import com.example.school.common.dto.UserDto;
import com.example.school.common.mapper.UserMapper;
import com.example.school.domain.entities.Otp;
import com.example.school.domain.entities.User;
import com.example.school.domain.repositories.OtpRepositoryInterface;
import com.example.school.domain.repositories.UserRepositoryInterface;
import com.example.school.infrastructure.security.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

/**
 * Service pour gérer les OTP (One-Time Password)
 */
@Service
@RequiredArgsConstructor
public class OtpService {

    private final OtpRepositoryInterface otpRepository;
    private final UserRepositoryInterface userRepository;
    private final EmailService emailService;
    private final JwtService jwtService;
    private static final int OTP_EXPIRATION_MINUTES = 15;
    private static final int OTP_LENGTH = 6;
    private final Random random = new Random();

    /**
     * Génère un code OTP à 6 chiffres
     */
    private String generateOtpCode() {
        int min = (int) Math.pow(10, OTP_LENGTH - 1);
        int max = (int) Math.pow(10, OTP_LENGTH) - 1;
        int otp = random.nextInt(max - min + 1) + min;
        return String.valueOf(otp);
    }

    /**
     * Envoie un OTP à un utilisateur pour réinitialisation de mot de passe
     */
    @Transactional
    public void sendOtpForPasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Aucun utilisateur trouvé avec cet email"));

        // Invalider les anciens OTP non vérifiés pour cet utilisateur
        invalidateOldOtps(user.getId(), "PASSWORD_RESET");

        // Générer un nouveau OTP
        String otpCode = generateOtpCode();
        Otp otp = new Otp();
        otp.setUserId(user.getId());
        otp.setCode(otpCode);
        otp.setPurpose("PASSWORD_RESET");
        otp.setExpiresAt(LocalDateTime.now().plusMinutes(OTP_EXPIRATION_MINUTES));
        otp.setVerified(false);

        otpRepository.save(otp);

        // Envoyer l'email
        emailService.sendOtpEmail(user.getEmail(), otpCode, "la réinitialisation de votre mot de passe");
    }

    /**
     * Envoie un OTP lors de la création du compte
     */
    @Transactional
    public Otp sendOtpForAccountCreation(UUID userId, String email) {
        // Générer un nouveau OTP
        String otpCode = generateOtpCode();
        Otp otp = new Otp();
        otp.setUserId(userId);
        otp.setCode(otpCode);
        otp.setPurpose("ACCOUNT_CREATION");
        otp.setExpiresAt(LocalDateTime.now().plusMinutes(OTP_EXPIRATION_MINUTES));
        otp.setVerified(false);

        Otp saved = otpRepository.save(otp);

        // Envoyer l'email
        emailService.sendOtpEmail(email, otpCode, "la création de votre compte");

        return saved;
    }

    /**
     * Vérifie un OTP en utilisant l'email et retourne un AuthResponse avec le token JWT
     */
    @Transactional
    public AuthResponse verifyOtp(String email, String code, String purpose) {
        // Trouver l'utilisateur par email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Aucun utilisateur trouvé avec cet email"));

        Optional<Otp> otpOpt = otpRepository.findByUserIdAndCodeAndPurpose(user.getId(), code, purpose);

        if (otpOpt.isEmpty()) {
            throw new EntityNotFoundException("Code OTP invalide");
        }

        Otp otp = otpOpt.get();

        // Vérifier si l'OTP a expiré
        if (otp.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new EntityNotFoundException("Code OTP expiré");
        }

        // Vérifier si l'OTP a déjà été utilisé
        if (otp.isVerified()) {
            throw new EntityNotFoundException("Code OTP déjà utilisé");
        }

        // Marquer l'OTP comme vérifié
        otp.setVerified(true);
        otp.setVerifiedAt(LocalDateTime.now());
        otpRepository.save(otp);

        // Générer le token JWT
        String token = jwtService.generateToken(user);

        // Créer le UserDto
        UserDto userDto = UserMapper.toDto(user);

        // Retourner l'AuthResponse avec le token et les infos utilisateur
        return new AuthResponse(token, userDto);
    }

    /**
     * Vérifie si un OTP valide et vérifié existe pour un utilisateur (par email)
     * Utilisé pour vérifier qu'un OTP a été vérifié avant de permettre le reset password
     * Accepte soit PASSWORD_RESET soit ACCOUNT_CREATION
     */
    public boolean hasVerifiedOtp(String email) {
        // Trouver l'utilisateur par email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Aucun utilisateur trouvé avec cet email"));

        // Vérifier d'abord PASSWORD_RESET
        Optional<Otp> passwordResetOtp = otpRepository.findLatestByUserIdAndPurpose(user.getId(), "PASSWORD_RESET");
        if (passwordResetOtp.isPresent()) {
            Otp otp = passwordResetOtp.get();
            if (otp.isVerified() && !otp.getExpiresAt().isBefore(LocalDateTime.now())) {
                return true;
            }
        }

        // Sinon vérifier ACCOUNT_CREATION
        Optional<Otp> accountCreationOtp = otpRepository.findLatestByUserIdAndPurpose(user.getId(), "ACCOUNT_CREATION");
        if (accountCreationOtp.isPresent()) {
            Otp otp = accountCreationOtp.get();
            if (otp.isVerified() && !otp.getExpiresAt().isBefore(LocalDateTime.now())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Vérifie si un OTP valide et vérifié existe pour un utilisateur avec un purpose spécifique
     */
    public boolean hasVerifiedOtp(String email, String purpose) {
        // Trouver l'utilisateur par email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Aucun utilisateur trouvé avec cet email"));

        Optional<Otp> otpOpt = otpRepository.findLatestByUserIdAndPurpose(user.getId(), purpose);

        if (otpOpt.isEmpty()) {
            return false;
        }

        Otp otp = otpOpt.get();

        // Vérifier si l'OTP a été vérifié et n'a pas expiré
        return otp.isVerified() && !otp.getExpiresAt().isBefore(LocalDateTime.now());
    }

    /**
     * Vérifie si un OTP valide (non vérifié) existe pour un utilisateur
     */
    public boolean hasValidOtp(UUID userId, String purpose) {
        Optional<Otp> otpOpt = otpRepository.findLatestByUserIdAndPurpose(userId, purpose);

        if (otpOpt.isEmpty()) {
            return false;
        }

        Otp otp = otpOpt.get();

        // Vérifier si l'OTP n'a pas expiré et n'a pas été vérifié
        return !otp.getExpiresAt().isBefore(LocalDateTime.now()) && !otp.isVerified();
    }

    /**
     * Invalide les anciens OTP non vérifiés pour un utilisateur
     */
    private void invalidateOldOtps(UUID userId, String purpose) {
        // On pourrait supprimer les anciens OTP, mais pour l'instant on les laisse
        // car ils sont déjà invalidés par l'expiration
    }

    /**
     * Nettoie les OTP expirés
     */
    @Transactional
    public void cleanupExpiredOtps() {
        otpRepository.deleteExpiredOtps();
    }
}

