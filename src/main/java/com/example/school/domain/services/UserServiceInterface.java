package com.example.school.domain.services;

import com.example.school.common.dto.UserDto;
import com.example.school.presenation.validators.UserRequestValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserServiceInterface {
    UserDto createUser(UserRequestValidator request);
    public Page<UserDto> filterUsers(String username,
                                     String email,
                                     String role,
                                     String phoneNumber,
                                     Pageable pageable);
    UserDto getUserById(UUID id);
    UserDto updateUser(UUID id, UserRequestValidator request);
    void deleteUser(UUID id);
}
