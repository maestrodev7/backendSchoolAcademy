package com.example.school.common.mapper;

import com.example.school.common.dto.UserDto;
import com.example.school.domain.entities.User;

import java.util.Set;

public class UserMapper {

    public static User toDomain(UserDto dto) {
        User user = new User();
        user.setId(dto.getId() != null ? dto.getId(): null);
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setRoles(dto.getRoles());
        return user;
    }

    public static UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId() != null ? user.getId(): null);
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setPassword(user.getPassword());
        dto.setEmail(user.getEmail());
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            dto.setRoles(user.getRoles());
        }
        return dto;
    }

}