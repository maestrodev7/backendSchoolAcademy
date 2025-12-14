package com.example.school.domain.services;

import com.example.school.common.dto.TermDto;
import com.example.school.presenation.validators.TermRequestValidator;

import java.util.List;
import java.util.UUID;

public interface TermServiceInterface {
    TermDto createTerm(TermRequestValidator request);
    TermDto getTermById(UUID id);
    List<TermDto> getAllTerms();
    List<TermDto> getTermsByAcademicYear(UUID academicYearId);
    TermDto updateTerm(UUID id, TermRequestValidator request);
    void deleteTerm(UUID id);
    TermDto setActiveTerm(UUID academicYearId, UUID termId);
}

