package com.example.school.domain.services;

import com.example.school.common.dto.SchoolDto;
import com.example.school.presenation.validators.SchoolRequestValidator;

import java.util.List;
import java.util.UUID;

public interface SchoolServiceInterface {

    SchoolDto createSchool(SchoolRequestValidator request);

    List<SchoolDto> getAllSchools();

    SchoolDto getSchoolById(UUID id);

    SchoolDto updateSchool(UUID id, SchoolRequestValidator request);

    void deleteSchool(UUID id);
}
