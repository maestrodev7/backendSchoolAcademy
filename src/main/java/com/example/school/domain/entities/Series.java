package com.example.school.domain.entities;

import lombok.Data;
import java.util.UUID;

@Data
public class Series {
    private UUID id;
    private String code;
    private String name;
}
