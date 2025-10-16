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
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final UserRepositoryInterface userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(@NotNull UserRequestValidator request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Cet email est déjà utilisé");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResourceAlreadyExistsException("Ce nom d'utilisateur est déjà utilisé");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Set<Role> userRoles = request.getRoles()
                .stream()
                .map(s -> s.toUpperCase(Locale.ROOT))
                .map(Role::valueOf)
                .collect(Collectors.toSet());
        user.setRoles(userRoles);

        User saved = userRepository.save(user);
        return UserMapper.toDto(saved);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
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
