package com.example.school.domain.services;

import com.example.school.common.dto.ScheduleDto;
import com.example.school.presenation.validators.ScheduleRequestValidator;
import com.example.school.presenation.validators.UpdateScheduleValidator;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

public interface ScheduleServiceInterface {
    ScheduleDto create(ScheduleRequestValidator request);
    ScheduleDto getById(UUID id);
    List<ScheduleDto> getAll();
    List<ScheduleDto> getByClassRoom(UUID classRoomId);
    List<ScheduleDto> getByTeacher(UUID teacherSubjectId);
    List<ScheduleDto> getBySchool(UUID schoolId);
    List<ScheduleDto> getByClassRoomAndDay(UUID classRoomId, DayOfWeek dayOfWeek);
    List<ScheduleDto> getByTeacherAndDay(UUID teacherSubjectId, DayOfWeek dayOfWeek);
    ScheduleDto update(UUID id, UpdateScheduleValidator request);
    void delete(UUID id);
    void deleteByClassRoom(UUID classRoomId);
    // Validation des conflits
    boolean hasConflicts(ScheduleRequestValidator request);
}

