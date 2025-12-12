package com.example.school.common.dto;

import com.example.school.domain.entities.Role;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private String email;
    private boolean passwordChanged;
    private Set<Role> roles;
}
