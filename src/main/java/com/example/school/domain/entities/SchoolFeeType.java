package com.example.school.domain.entities;

import lombok.Data;

import java.util.UUID;

@Data
public class SchoolFeeType {
    private UUID id;
    private String label;
    private boolean mandatory;
    private School school;
}
