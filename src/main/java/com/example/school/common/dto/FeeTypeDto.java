package com.example.school.common.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class FeeTypeDto {
    private UUID id;
    private String name;
    private boolean mandatory;
    private UUID schoolId;
}

