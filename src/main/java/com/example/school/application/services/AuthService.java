package com.example.school.application.services;

import com.example.school.common.dto.AuthResponse;
import com.example.school.common.dto.UserDto;
import com.example.school.common.exceptions.InvalidCredentialsException;
import com.example.school.domain.entities.User;
import com.example.school.domain.repositories.UserRepositoryInterface;
import com.example.school.domain.services.AuthServiceInterface;
import com.example.school.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthServiceInterface {

    private final UserRepositoryInterface userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse authenticate(String username, String rawPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String token = jwtService.generateToken(user);

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return new AuthResponse(token, userDto);
    }


}
