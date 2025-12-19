package com.example.school.domain.services;

import com.example.school.common.dto.CompetenceDto;
import com.example.school.presenation.validators.CompetenceRequestValidator;

import java.util.List;
import java.util.UUID;

public interface CompetenceServiceInterface {
    CompetenceDto createCompetence(CompetenceRequestValidator request);
    CompetenceDto getCompetenceById(UUID id);
    List<CompetenceDto> getAllCompetences();
    List<CompetenceDto> getCompetencesBySubjectId(UUID subjectId);
    CompetenceDto updateCompetence(UUID id, CompetenceRequestValidator request);
    void deleteCompetence(UUID id);
}

