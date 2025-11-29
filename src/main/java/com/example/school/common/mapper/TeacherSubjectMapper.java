package com.example.school.common.mapper;

import com.example.school.common.dto.TeacherSubjectDto;
import com.example.school.common.dto.UserDto;
import com.example.school.common.mapper.UserMapper;
import com.example.school.domain.entities.TeacherSubject;
import com.example.school.domain.repositories.SchoolRepositoryInterface;
import com.example.school.domain.repositories.SubjectRepositoryInterface;
import com.example.school.domain.repositories.UserRepositoryInterface;
import com.example.school.domain.repositories.UserSchoolRepositoryInterface;

/**
 * Mapper pour enrichir les DTOs TeacherSubject avec toutes les informations nécessaires
 * pour une excellente expérience utilisateur
 */
public class TeacherSubjectMapper {

    public static TeacherSubjectDto toDto(
            TeacherSubject entity,
            UserSchoolRepositoryInterface userSchoolRepository,
            SubjectRepositoryInterface subjectRepository,
            SchoolRepositoryInterface schoolRepository,
            UserRepositoryInterface userRepository) {
        
        if (entity == null) return null;

        TeacherSubjectDto dto = new TeacherSubjectDto();
        dto.setId(entity.getId());
        dto.setUserSchoolId(entity.getUserSchoolId());
        dto.setSubjectId(entity.getSubjectId());
        dto.setSchoolId(entity.getSchoolId());
        dto.setSpecialization(entity.getSpecialization());
        dto.setExperienceYears(entity.getExperienceYears());

        // Enrichir avec les informations de l'enseignant via UserSchool
        if (entity.getUserSchoolId() != null) {
            userSchoolRepository.findById(entity.getUserSchoolId())
                    .ifPresent(userSchool -> {
                        // Charger directement l'utilisateur depuis le UserRepository
                        if (userSchool.getUserId() != null) {
                            userRepository.findById(userSchool.getUserId())
                                    .map(UserMapper::toDto)
                                    .ifPresent(dto::setTeacher);
                        }
                    });
        }

        // Enrichir avec les informations de la matière
        if (entity.getSubjectId() != null) {
            subjectRepository.findById(entity.getSubjectId())
                    .map(SubjectMapper::toDto)
                    .ifPresent(dto::setSubject);
        }

        // Enrichir avec les informations de l'école
        if (entity.getSchoolId() != null) {
            schoolRepository.findById(entity.getSchoolId())
                    .map(SchoolMapper::toDto)
                    .ifPresent(dto::setSchool);
        }

        return dto;
    }

    public static TeacherSubjectDto toDto(TeacherSubject entity) {
        if (entity == null) return null;

        TeacherSubjectDto dto = new TeacherSubjectDto();
        dto.setId(entity.getId());
        dto.setUserSchoolId(entity.getUserSchoolId());
        dto.setSubjectId(entity.getSubjectId());
        dto.setSchoolId(entity.getSchoolId());
        dto.setSpecialization(entity.getSpecialization());
        dto.setExperienceYears(entity.getExperienceYears());

        return dto;
    }
}

