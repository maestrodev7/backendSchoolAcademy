package com.example.school.application.services;

import com.example.school.common.dto.UserDto;
import com.example.school.common.exceptions.ResourceAlreadyExistsException;
import com.example.school.common.mapper.UserMapper;
import com.example.school.domain.entities.Role;
import com.example.school.domain.entities.User;
import com.example.school.domain.repositories.UserRepositoryInterface;
import com.example.school.domain.services.UserServiceInterface;
import com.example.school.presenation.validators.UserRequestValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserServiceInterface {

    private final UserRepositoryInterface userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;
    private final EmailService emailService;
    private static final String PASSWORD_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
    private static final int PASSWORD_LENGTH = 12;

    /**
     * Génère un mot de passe aléatoire sécurisé
     */
    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(PASSWORD_CHARS.charAt(random.nextInt(PASSWORD_CHARS.length())));
        }
        return password.toString();
    }

    @Override
    @Transactional
    public UserDto createUser(@NotNull UserRequestValidator request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Cet email est déjà utilisé");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResourceAlreadyExistsException("Ce nom d'utilisateur est déjà utilisé");
        }

        // Générer un mot de passe aléatoire si non fourni
        String rawPassword = request.getPassword();
        if (rawPassword == null || rawPassword.isEmpty()) {
            rawPassword = generateRandomPassword();
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setPasswordChanged(false); // L'utilisateur n'a pas encore changé son mot de passe
        Set<Role> userRoles = request.getRoles()
                .stream()
                .map(s -> s.toUpperCase(Locale.ROOT))
                .map(Role::valueOf)
                .collect(Collectors.toSet());
        user.setRoles(userRoles);

        User saved = userRepository.save(user);

        // Générer et envoyer l'OTP pour la création du compte
        try {
            otpService.sendOtpForAccountCreation(saved.getId(), saved.getEmail());
        } catch (Exception e) {
            log.error("Erreur lors de l'envoi de l'OTP pour l'utilisateur {}: {}", saved.getId(), e.getMessage());
        }

        // Envoyer les identifiants par email
        try {
            emailService.sendCredentialsEmail(saved.getEmail(), saved.getUsername(), rawPassword);
        } catch (Exception e) {
            log.error("Erreur lors de l'envoi des identifiants pour l'utilisateur {}: {}", saved.getId(), e.getMessage());
        }

        return UserMapper.toDto(saved);
    }

    @Override
    public Page<UserDto> filterUsers(String username,
                                     String email,
                                     String role,
                                     String phoneNumber,
                                     Pageable pageable) {
        Page<User> users = userRepository.filterUsers(username, email, role, phoneNumber, pageable);
        return users.map(UserMapper::toDto);
    }


    @Override
    public UserDto getUserById(UUID id) {
        return userRepository.findById(id)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
    }

    @Override
    public UserDto updateUser(UUID id, @NotNull UserRequestValidator request) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));

        existing.setUsername(request.getUsername());
        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setPhoneNumber(request.getPhoneNumber());
        existing.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(request.getPassword()));
            existing.setPasswordChanged(true); // L'utilisateur a changé son mot de passe
        }

        Set<Role> userRoles = request.getRoles()
                .stream()
                .map(s -> s.toUpperCase(Locale.ROOT))
                .map(Role::valueOf)
                .collect(Collectors.toSet());
        existing.setRoles(userRoles);

        User updated = userRepository.save(existing);
        return UserMapper.toDto(updated);
    }

    @Override
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Utilisateur non trouvé");
        }
        userRepository.deleteById(id);
    }
}
