package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.ScheduleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaScheduleRepository extends JpaRepository<ScheduleModel, UUID> {
    
    // Trouver l'emploi du temps d'une classe
    List<ScheduleModel> findByClassRoomIdOrderByDayOfWeekAscStartTimeAsc(UUID classRoomId);
    
    // Trouver l'emploi du temps d'un enseignant
    List<ScheduleModel> findByTeacherSubjectIdOrderByDayOfWeekAscStartTimeAsc(UUID teacherSubjectId);
    
    // Trouver l'emploi du temps d'une école
    List<ScheduleModel> findBySchoolIdOrderByDayOfWeekAscStartTimeAsc(UUID schoolId);
    
    // Trouver les créneaux d'un jour spécifique pour une classe
    List<ScheduleModel> findByClassRoomIdAndDayOfWeekOrderByStartTimeAsc(
            UUID classRoomId, DayOfWeek dayOfWeek);
    
    // Trouver les créneaux d'un jour spécifique pour un enseignant
    List<ScheduleModel> findByTeacherSubjectIdAndDayOfWeekOrderByStartTimeAsc(
            UUID teacherSubjectId, DayOfWeek dayOfWeek);
    
    // Vérifier les conflits d'horaires pour une classe
    @Query("SELECT s FROM ScheduleModel s WHERE s.classRoomId = :classRoomId " +
           "AND s.dayOfWeek = :dayOfWeek " +
           "AND ((s.startTime < :endTime AND s.endTime > :startTime))")
    List<ScheduleModel> findConflictingSchedulesForClassRoom(
            @Param("classRoomId") UUID classRoomId,
            @Param("dayOfWeek") DayOfWeek dayOfWeek,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime);
    
    // Vérifier les conflits d'horaires pour un enseignant
    @Query("SELECT s FROM ScheduleModel s WHERE s.teacherSubjectId = :teacherSubjectId " +
           "AND s.dayOfWeek = :dayOfWeek " +
           "AND ((s.startTime < :endTime AND s.endTime > :startTime))")
    List<ScheduleModel> findConflictingSchedulesForTeacher(
            @Param("teacherSubjectId") UUID teacherSubjectId,
            @Param("dayOfWeek") DayOfWeek dayOfWeek,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime);
    
    // Trouver l'emploi du temps d'une classe pour une semaine complète
    @Query("SELECT s FROM ScheduleModel s WHERE s.classRoomId = :classRoomId " +
           "ORDER BY s.dayOfWeek ASC, s.startTime ASC")
    List<ScheduleModel> findByClassRoomIdWithDetails(@Param("classRoomId") UUID classRoomId);
    
    // Trouver l'emploi du temps d'une année académique
    List<ScheduleModel> findByAcademicYearIdOrderByDayOfWeekAscStartTimeAsc(UUID academicYearId);
}

