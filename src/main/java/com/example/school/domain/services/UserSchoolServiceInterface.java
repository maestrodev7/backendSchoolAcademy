package com.example.school.domain.services;

import com.example.school.common.dto.UserSchoolDto;
import com.example.school.presenation.validators.UserSchoolRequestValidator;

import java.util.List;
import java.util.UUID;

public interface UserSchoolServiceInterface {
    UserSchoolDto create(UserSchoolRequestValidator request);
    UserSchoolDto getById(UUID id);
    List<UserSchoolDto> getAll();
    void delete(UUID id);
}
