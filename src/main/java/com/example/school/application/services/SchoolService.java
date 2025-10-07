package com.example.school.application.services;

import com.example.school.common.dto.SchoolDto;
import com.example.school.common.exceptions.ResourceAlreadyExistsException;
import com.example.school.common.mapper.SchoolMapper;
import com.example.school.domain.entities.AcademicYear;
import com.example.school.domain.entities.School;
import com.example.school.domain.repositories.SchoolRepositoryInterface;
import com.example.school.domain.services.SchoolServiceInterface;
import com.example.school.presenation.validators.SchoolRequestValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchoolService implements SchoolServiceInterface {

    private final SchoolRepositoryInterface schoolRepository;

    @Override
    public SchoolDto createSchool(@NotNull SchoolRequestValidator request) {
        if (schoolRepository.existsByName(request.getName())) {
            throw new ResourceAlreadyExistsException("Cette école existe déjà");
        }

        School school = new School();
        school.setName(request.getName());
        school.setAddress(request.getAddress());
        school.setPhoneNumber(request.getPhoneNumber());

        Set<AcademicYear> academicYears = new HashSet<>();
        if (request.getAcademicYearIds() != null) {
            request.getAcademicYearIds().forEach(id -> {
                AcademicYear year = new AcademicYear();
                year.setId(id);
                academicYears.add(year);
            });
        }
        school.setAcademicYears(academicYears);

        School saved = schoolRepository.save(school);
        return SchoolMapper.toDto(saved);
    }

    @Override
    public List<SchoolDto> getAllSchools() {
        System.out.println("tests ecoles: " + schoolRepository.findAll());
        return schoolRepository.findAll()
                .stream()
                .map(SchoolMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SchoolDto getSchoolById(UUID id) {
        return schoolRepository.findById(id)
                .map(SchoolMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("École non trouvée"));
    }

    @Override
    public SchoolDto updateSchool(UUID id, @NotNull SchoolRequestValidator request) {
        School existing = schoolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("École non trouvée"));

        if (!existing.getName().equals(request.getName()) && schoolRepository.existsByName(request.getName())) {
            throw new ResourceAlreadyExistsException("Une école avec ce nom existe déjà");
        }

        existing.setName(request.getName());
        existing.setAddress(request.getAddress());
        existing.setPhoneNumber(request.getPhoneNumber());

        Set<AcademicYear> academicYears = new HashSet<>();
        if (request.getAcademicYearIds() != null) {
            request.getAcademicYearIds().forEach(yearId -> {
                AcademicYear year = new AcademicYear();
                year.setId(yearId);
                academicYears.add(year);
            });
        }
        existing.setAcademicYears(academicYears);

        School updated = schoolRepository.save(existing);
        return SchoolMapper.toDto(updated);
    }

    @Override
    public void deleteSchool(UUID id) {
        if (!schoolRepository.existsById(id)) {
            throw new EntityNotFoundException("École non trouvée");
        }
        schoolRepository.deleteById(id);
    }
}
