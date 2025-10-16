package com.example.school.common.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SeriesDto {
    private UUID id;
    private String code;
    private String name;
}
