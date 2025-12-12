package com.example.school.domain.entities;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class User {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private boolean passwordChanged = false; // Indique si l'utilisateur a changé son mot de passe initial
    private Set<Role> roles;
}
