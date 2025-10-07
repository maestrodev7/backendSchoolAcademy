package com.example.school.domain.services;

import com.example.school.common.dto.UserDto;
import com.example.school.presenation.validators.UserRequestValidator;

import java.util.List;

public interface UserServiceInterface {
    UserDto createUser(UserRequestValidator request);
    List<UserDto> getAllUsers();
    UserDto getUserById(String id);
    UserDto updateUser(String id, UserRequestValidator request);
    void deleteUser(String id);
}
