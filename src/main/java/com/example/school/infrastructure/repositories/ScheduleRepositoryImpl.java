package com.example.school.infrastructure.repositories;

import com.example.school.domain.entities.Schedule;
import com.example.school.domain.repositories.ScheduleRepositoryInterface;
import com.example.school.infrastructure.mappers.ScheduleMapper;
import com.example.school.infrastructure.models.ScheduleModel;
import com.example.school.infrastructure.repositories.jpa.JpaScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepositoryInterface {

    private final JpaScheduleRepository jpaScheduleRepository;

    @Override
    public Schedule save(Schedule schedule) {
        ScheduleModel model = ScheduleMapper.toModel(schedule);
        ScheduleModel saved = jpaScheduleRepository.save(model);
        return ScheduleMapper.toDomain(saved);
    }

    @Override
    public Optional<Schedule> findById(UUID id) {
        return jpaScheduleRepository.findById(id)
                .map(ScheduleMapper::toDomain);
    }

    @Override
    public List<Schedule> findAll() {
        return jpaScheduleRepository.findAll()
                .stream()
                .map(ScheduleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Schedule> findByClassRoomId(UUID classRoomId) {
        return jpaScheduleRepository.findByClassRoomIdOrderByDayOfWeekAscStartTimeAsc(classRoomId)
                .stream()
                .map(ScheduleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Schedule> findByTeacherSubjectId(UUID teacherSubjectId) {
        return jpaScheduleRepository.findByTeacherSubjectIdOrderByDayOfWeekAscStartTimeAsc(teacherSubjectId)
                .stream()
                .map(ScheduleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Schedule> findBySchoolId(UUID schoolId) {
        return jpaScheduleRepository.findBySchoolIdOrderByDayOfWeekAscStartTimeAsc(schoolId)
                .stream()
                .map(ScheduleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Schedule> findByClassRoomIdAndDayOfWeek(UUID classRoomId, DayOfWeek dayOfWeek) {
        return jpaScheduleRepository.findByClassRoomIdAndDayOfWeekOrderByStartTimeAsc(classRoomId, dayOfWeek)
                .stream()
                .map(ScheduleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Schedule> findByTeacherSubjectIdAndDayOfWeek(UUID teacherSubjectId, DayOfWeek dayOfWeek) {
        return jpaScheduleRepository.findByTeacherSubjectIdAndDayOfWeekOrderByStartTimeAsc(teacherSubjectId, dayOfWeek)
                .stream()
                .map(ScheduleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Schedule> findConflictingSchedulesForClassRoom(
            UUID classRoomId, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        return jpaScheduleRepository.findConflictingSchedulesForClassRoom(
                classRoomId, dayOfWeek, startTime, endTime)
                .stream()
                .map(ScheduleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Schedule> findConflictingSchedulesForTeacher(
            UUID teacherSubjectId, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        return jpaScheduleRepository.findConflictingSchedulesForTeacher(
                teacherSubjectId, dayOfWeek, startTime, endTime)
                .stream()
                .map(ScheduleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaScheduleRepository.deleteById(id);
    }

    @Override
    public void deleteByClassRoomId(UUID classRoomId) {
        jpaScheduleRepository.findByClassRoomIdOrderByDayOfWeekAscStartTimeAsc(classRoomId)
                .forEach(jpaScheduleRepository::delete);
    }
}

