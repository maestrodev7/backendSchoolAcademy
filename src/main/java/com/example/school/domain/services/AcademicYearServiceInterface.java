package com.example.school.domain.services;

import com.example.school.common.dto.AcademicYearDto;
import com.example.school.presenation.validators.AcademicYearRequestValidator;

import java.util.List;
import java.util.UUID;

public interface AcademicYearServiceInterface {

    AcademicYearDto createAcademicYear(AcademicYearRequestValidator request);

    List<AcademicYearDto> getAllAcademicYears();

    AcademicYearDto getAcademicYearById(UUID id);

    AcademicYearDto updateAcademicYear(UUID id, AcademicYearRequestValidator request);

    void deleteAcademicYear(UUID id);

}
