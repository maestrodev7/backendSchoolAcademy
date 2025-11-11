package com.example.school.domain.entities;

import lombok.Data;

import java.util.UUID;

@Data
public class FeeType {
    private UUID id;
    private String name;
    private boolean mandatory;
    private School school;
}

