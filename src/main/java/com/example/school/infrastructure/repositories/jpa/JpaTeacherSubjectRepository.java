package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.TeacherSubjectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaTeacherSubjectRepository extends JpaRepository<TeacherSubjectModel, UUID> {
    
    // Trouver toutes les matières d'un enseignant dans une école
    List<TeacherSubjectModel> findByUserSchoolIdAndSchoolId(UUID userSchoolId, UUID schoolId);
    
    // Trouver tous les enseignants d'une matière dans une école
    List<TeacherSubjectModel> findBySubjectIdAndSchoolId(UUID subjectId, UUID schoolId);
    
    // Trouver toutes les associations d'un enseignant (toutes écoles)
    List<TeacherSubjectModel> findByUserSchoolId(UUID userSchoolId);
    
    // Trouver toutes les associations d'une matière (toutes écoles)
    List<TeacherSubjectModel> findBySubjectId(UUID subjectId);
    
    // Trouver toutes les associations d'une école
    List<TeacherSubjectModel> findBySchoolId(UUID schoolId);
    
    // Vérifier si une association existe déjà
    Optional<TeacherSubjectModel> findByUserSchoolIdAndSubjectIdAndSchoolId(
            UUID userSchoolId, UUID subjectId, UUID schoolId);
    
    // Trouver toutes les associations d'une école (pour optimisation future)
    @Query("SELECT ts FROM TeacherSubjectModel ts WHERE ts.schoolId = :schoolId")
    List<TeacherSubjectModel> findBySchoolIdWithDetails(@Param("schoolId") UUID schoolId);
    
    // Trouver toutes les associations d'une année académique
    List<TeacherSubjectModel> findByAcademicYearId(UUID academicYearId);
}

