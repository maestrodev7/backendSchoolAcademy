package com.example.school.common.mapper;

import com.example.school.common.dto.ScheduleDto;
import com.example.school.domain.entities.Schedule;
import com.example.school.domain.repositories.ClassRoomRepositoryInterface;
import com.example.school.domain.repositories.ClassRoomSubjectRepositoryInterface;
import com.example.school.domain.repositories.SchoolRepositoryInterface;
import com.example.school.domain.repositories.SubjectRepositoryInterface;
import com.example.school.domain.repositories.TeacherSubjectRepositoryInterface;
import com.example.school.domain.repositories.UserRepositoryInterface;
import com.example.school.domain.repositories.UserSchoolRepositoryInterface;

import java.time.Duration;

/**
 * Mapper pour enrichir les DTOs Schedule avec toutes les informations nécessaires
 * pour une excellente expérience utilisateur
 */
public class ScheduleMapper {

    public static ScheduleDto toDto(
            Schedule entity,
            ClassRoomRepositoryInterface classRoomRepository,
            ClassRoomSubjectRepositoryInterface classRoomSubjectRepository,
            TeacherSubjectRepositoryInterface teacherSubjectRepository,
            SchoolRepositoryInterface schoolRepository,
            com.example.school.common.mapper.TeacherSubjectMapper teacherSubjectMapper,
            SubjectRepositoryInterface subjectRepository,
            UserSchoolRepositoryInterface userSchoolRepository,
            com.example.school.domain.repositories.UserRepositoryInterface userRepository) {
        
        if (entity == null) return null;

        ScheduleDto dto = new ScheduleDto();
        dto.setId(entity.getId());
        dto.setClassRoomId(entity.getClassRoomId());
        dto.setClassRoomSubjectId(entity.getClassRoomSubjectId());
        dto.setTeacherSubjectId(entity.getTeacherSubjectId());
        dto.setSchoolId(entity.getSchoolId());
        dto.setDayOfWeek(entity.getDayOfWeek());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setRoom(entity.getRoom());
        dto.setNotes(entity.getNotes());

        // Calculer la durée
        if (entity.getStartTime() != null && entity.getEndTime() != null) {
            Duration duration = Duration.between(entity.getStartTime(), entity.getEndTime());
            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();
            if (hours > 0 && minutes > 0) {
                dto.setDuration(hours + "h" + String.format("%02d", minutes));
            } else if (hours > 0) {
                dto.setDuration(hours + "h");
            } else {
                dto.setDuration(minutes + "min");
            }
        }

        // Enrichir avec les informations de la classe
        if (entity.getClassRoomId() != null) {
            classRoomRepository.findById(entity.getClassRoomId())
                    .map(ClassroomMapper::toDto)
                    .ifPresent(dto::setClassRoom);
        }

        // Enrichir avec les informations de l'association classe-matière
        if (entity.getClassRoomSubjectId() != null) {
            classRoomSubjectRepository.findById(entity.getClassRoomSubjectId())
                    .map(classRoomSubject -> SubjectMapper.toClassRoomSubjectDto(classRoomSubject))
                    .ifPresent(dto::setClassRoomSubject);
        }

        // Enrichir avec les informations de l'association enseignant-matière
        if (entity.getTeacherSubjectId() != null) {
            teacherSubjectRepository.findById(entity.getTeacherSubjectId())
                    .map(teacherSubject -> TeacherSubjectMapper.toDto(
                            teacherSubject,
                            userSchoolRepository,
                            subjectRepository,
                            schoolRepository,
                            userRepository))
                    .ifPresent(dto::setTeacherSubject);
        }

        // Enrichir avec les informations de l'école
        if (entity.getSchoolId() != null) {
            schoolRepository.findById(entity.getSchoolId())
                    .map(SchoolMapper::toDto)
                    .ifPresent(dto::setSchool);
        }

        return dto;
    }

    public static ScheduleDto toDto(Schedule entity) {
        if (entity == null) return null;

        ScheduleDto dto = new ScheduleDto();
        dto.setId(entity.getId());
        dto.setClassRoomId(entity.getClassRoomId());
        dto.setClassRoomSubjectId(entity.getClassRoomSubjectId());
        dto.setTeacherSubjectId(entity.getTeacherSubjectId());
        dto.setSchoolId(entity.getSchoolId());
        dto.setDayOfWeek(entity.getDayOfWeek());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setRoom(entity.getRoom());
        dto.setNotes(entity.getNotes());

        // Calculer la durée
        if (entity.getStartTime() != null && entity.getEndTime() != null) {
            Duration duration = Duration.between(entity.getStartTime(), entity.getEndTime());
            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();
            if (hours > 0 && minutes > 0) {
                dto.setDuration(hours + "h" + String.format("%02d", minutes));
            } else if (hours > 0) {
                dto.setDuration(hours + "h");
            } else {
                dto.setDuration(minutes + "min");
            }
        }

        return dto;
    }
}

