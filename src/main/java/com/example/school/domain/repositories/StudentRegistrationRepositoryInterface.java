package com.example.school.domain.repositories;

import com.example.school.domain.entities.StudentRegistration;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRegistrationRepositoryInterface {
    StudentRegistration save(StudentRegistration registration);

    Optional<StudentRegistration> findById(UUID id);

    List<StudentRegistration> findByClassRoom(UUID classRoomId);

    List<StudentRegistration> findByAcademicYear(UUID academicYearId);

    List<StudentRegistration> findBySchool(UUID schoolId);

    List<StudentRegistration> findByStudent(UUID studentId);

    Optional<StudentRegistration> findByStudentAndAcademicYear(UUID studentId, UUID academicYearId);

    List<StudentRegistration> findByClassRoomAndAcademicYear(UUID classRoomId, UUID academicYearId);

    void deleteById(UUID id);
}

