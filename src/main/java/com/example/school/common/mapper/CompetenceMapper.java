package com.example.school.common.mapper;

import com.example.school.common.dto.CompetenceDto;
import com.example.school.domain.entities.Competence;

public class CompetenceMapper {
    public static CompetenceDto toDto(Competence competence) {
        if (competence == null) return null;
        CompetenceDto dto = new CompetenceDto();
        dto.setId(competence.getId());
        dto.setSubjectId(competence.getSubject() != null ? competence.getSubject().getId() : null);
        dto.setSubjectName(competence.getSubject() != null ? competence.getSubject().getName() : null);
        dto.setDescription(competence.getDescription());
        dto.setOrderNumber(competence.getOrderNumber());
        return dto;
    }
}

