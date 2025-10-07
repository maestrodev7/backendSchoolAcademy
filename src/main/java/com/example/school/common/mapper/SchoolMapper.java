package com.example.school.common.mapper;

import com.example.school.common.dto.AcademicYearDto;
import com.example.school.common.dto.SchoolDto;
import com.example.school.domain.entities.AcademicYear;
import com.example.school.domain.entities.School;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SchoolMapper {

    /** Map une entité School vers un DTO SchoolDto */
    public static SchoolDto toDto(School school) {
        if (school == null) return null;

        SchoolDto dto = new SchoolDto();
        dto.setId(school.getId());
        dto.setName(school.getName());
        dto.setAddress(school.getAddress());
        dto.setPhoneNumber(school.getPhoneNumber());
        dto.setEmail(school.getEmail());

        // Map les AcademicYears vers des DTO indépendants (sans boucle)
        if (school.getAcademicYears() != null) {
            dto.setAcademicYears(
                    school.getAcademicYears().stream()
                            .map(SchoolMapper::toDto)
                            .collect(Collectors.toSet())
            );
        } else {
            dto.setAcademicYears(new HashSet<>());
        }

        return dto;
    }

    /** Map un AcademicYear vers AcademicYearDto */
    private static AcademicYearDto toDto(AcademicYear ay) {
        if (ay == null) return null;

        AcademicYearDto ayDto = new AcademicYearDto();
        ayDto.setId(ay.getId());
        ayDto.setStartDate(ay.getStartDate());
        ayDto.setEndDate(ay.getEndDate());
        ayDto.setActive(ay.isActive());

        // Ne jamais inclure ay.getSchools() pour éviter la récursion infinie
        return ayDto;
    }

    /** Map un DTO SchoolDto vers une entité School */
    public static School toEntity(SchoolDto dto) {
        if (dto == null) return null;

        School school = new School();
        school.setId(dto.getId());
        school.setName(dto.getName());
        school.setAddress(dto.getAddress());
        school.setPhoneNumber(dto.getPhoneNumber());
        school.setEmail(dto.getEmail());

        if (dto.getAcademicYears() != null) {
            Set<AcademicYear> academicYears = dto.getAcademicYears().stream()
                    .map(SchoolMapper::toEntity)
                    .collect(Collectors.toSet());
            school.setAcademicYears(academicYears);
        } else {
            school.setAcademicYears(new HashSet<>());
        }

        return school;
    }

    /** Map un AcademicYearDto vers AcademicYear */
    private static AcademicYear toEntity(AcademicYearDto dto) {
        if (dto == null) return null;

        AcademicYear ay = new AcademicYear();
        ay.setId(dto.getId());
        ay.setStartDate(dto.getStartDate());
        ay.setEndDate(dto.getEndDate());
        ay.setActive(dto.isActive());

        // Ne jamais inclure schools pour éviter la récursion infinie
        ay.setSchools(new HashSet<>());

        return ay;
    }
}
