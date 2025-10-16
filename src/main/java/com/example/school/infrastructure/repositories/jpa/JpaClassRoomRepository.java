package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.ClassRoomModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface JpaClassRoomRepository extends JpaRepository<ClassRoomModel, UUID> {
    @Query("SELECT c FROM ClassRoomModel c WHERE c.school.id = :schoolId")
    List<ClassRoomModel> findBySchool(UUID schoolId);

    @Query("SELECT c FROM ClassRoomModel c WHERE c.academicYear.id = :academicYearId")
    List<ClassRoomModel> findByAcademicYear(UUID academicYearId);
}
