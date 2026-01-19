package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.StudentRegistrationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface JpaStudentRegistrationRepository extends JpaRepository<StudentRegistrationModel, UUID> {
    List<StudentRegistrationModel> findByClassRoom_Id(UUID classRoomId);

    List<StudentRegistrationModel> findByClassRoom_School_Id(UUID schoolId);

    List<StudentRegistrationModel> findByAcademicYear_Id(UUID academicYearId);

    List<StudentRegistrationModel> findByStudent_Id(UUID studentId);

    java.util.Optional<StudentRegistrationModel> findByStudent_IdAndAcademicYear_Id(UUID studentId, UUID academicYearId);

    List<StudentRegistrationModel> findByClassRoom_IdAndAcademicYear_Id(UUID classRoomId, UUID academicYearId);
}
