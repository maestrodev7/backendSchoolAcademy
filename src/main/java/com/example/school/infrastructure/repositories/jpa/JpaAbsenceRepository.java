package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.AbsenceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface JpaAbsenceRepository extends JpaRepository<AbsenceModel, UUID> {
    
    // Trouver les absences d'un élève
    List<AbsenceModel> findByStudentId(UUID studentId);
    
    // Trouver les absences d'un élève pour une période
    List<AbsenceModel> findByStudentIdAndDateBetween(UUID studentId, LocalDate startDate, LocalDate endDate);
    
    // Trouver toutes les absences d'une classe
    List<AbsenceModel> findByClassRoomId(UUID classRoomId);
    
    // Trouver les absences d'une classe pour une date
    List<AbsenceModel> findByClassRoomIdAndDate(UUID classRoomId, LocalDate date);
    
    // Trouver les absences d'une classe pour une période
    List<AbsenceModel> findByClassRoomIdAndDateBetween(UUID classRoomId, LocalDate startDate, LocalDate endDate);
    
    // Trouver les absences d'un élève dans une classe pour une période
    List<AbsenceModel> findByStudentIdAndClassRoomIdAndDateBetween(
            UUID studentId, UUID classRoomId, LocalDate startDate, LocalDate endDate);
    
    // Trouver les absences d'une classe pour une matière et une date
    List<AbsenceModel> findByClassRoomIdAndSubjectIdAndDate(UUID classRoomId, UUID subjectId, LocalDate date);
    
    // Trouver les absences d'un élève pour une année académique
    List<AbsenceModel> findByStudentIdAndAcademicYearId(UUID studentId, UUID academicYearId);
    
    // Trouver les absences d'une classe pour une année académique
    List<AbsenceModel> findByClassRoomIdAndAcademicYearId(UUID classRoomId, UUID academicYearId);
    
    // Trouver les absences d'une école pour une période
    List<AbsenceModel> findBySchoolIdAndDateBetween(UUID schoolId, LocalDate startDate, LocalDate endDate);
    
    // Compter le total d'heures d'absence d'un élève pour une période
    @Query("SELECT COALESCE(SUM(a.numberOfHours), 0) FROM AbsenceModel a WHERE a.student.id = :studentId AND a.date BETWEEN :startDate AND :endDate")
    Double sumHoursByStudentAndDateRange(@Param("studentId") UUID studentId, 
                                         @Param("startDate") LocalDate startDate, 
                                         @Param("endDate") LocalDate endDate);
}
