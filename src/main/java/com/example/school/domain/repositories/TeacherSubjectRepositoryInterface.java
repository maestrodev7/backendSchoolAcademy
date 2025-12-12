package com.example.school.domain.repositories;

import com.example.school.domain.entities.TeacherSubject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TeacherSubjectRepositoryInterface {
    TeacherSubject save(TeacherSubject teacherSubject);
    Optional<TeacherSubject> findById(UUID id);
    List<TeacherSubject> findAll();
    List<TeacherSubject> findByUserSchoolIdAndSchoolId(UUID userSchoolId, UUID schoolId);
    List<TeacherSubject> findBySubjectIdAndSchoolId(UUID subjectId, UUID schoolId);
    List<TeacherSubject> findByUserSchoolId(UUID userSchoolId);
    List<TeacherSubject> findBySubjectId(UUID subjectId);
    List<TeacherSubject> findBySchoolId(UUID schoolId);
    Optional<TeacherSubject> findByUserSchoolIdAndSubjectIdAndSchoolId(
            UUID userSchoolId, UUID subjectId, UUID schoolId);
    List<TeacherSubject> findByAcademicYear(UUID academicYearId);
    void deleteById(UUID id);
}

