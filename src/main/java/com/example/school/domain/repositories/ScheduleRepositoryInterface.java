package com.example.school.domain.repositories;

import com.example.school.domain.entities.Schedule;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ScheduleRepositoryInterface {
    Schedule save(Schedule schedule);
    Optional<Schedule> findById(UUID id);
    List<Schedule> findAll();
    List<Schedule> findByClassRoomId(UUID classRoomId);
    List<Schedule> findByTeacherSubjectId(UUID teacherSubjectId);
    List<Schedule> findBySchoolId(UUID schoolId);
    List<Schedule> findByClassRoomIdAndDayOfWeek(UUID classRoomId, DayOfWeek dayOfWeek);
    List<Schedule> findByTeacherSubjectIdAndDayOfWeek(UUID teacherSubjectId, DayOfWeek dayOfWeek);
    List<Schedule> findConflictingSchedulesForClassRoom(
            UUID classRoomId, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime);
    List<Schedule> findConflictingSchedulesForTeacher(
            UUID teacherSubjectId, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime);
    void deleteById(UUID id);
    void deleteByClassRoomId(UUID classRoomId);
}

