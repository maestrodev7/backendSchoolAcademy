package com.example.school.domain.services;

import com.example.school.common.dto.UserDto;
import com.example.school.presenation.validators.UserRequestValidator;

import java.util.List;
import java.util.UUID;

public interface UserServiceInterface {
    UserDto createUser(UserRequestValidator request);
    List<UserDto> getAllUsers();
    UserDto getUserById(UUID id);
    UserDto updateUser(UUID id, UserRequestValidator request);
    void deleteUser(UUID id);
}
