package com.example.school.domain.repositories;

import com.example.school.domain.entities.ClassRoom;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClassRoomRepositoryInterface {
    ClassRoom save(ClassRoom classRoom);
    Optional<ClassRoom> findById(UUID id);
    List<ClassRoom> findBySchool(UUID schoolId);
    List<ClassRoom> findByAcademicYear(UUID academicYearId);
    void deleteById(UUID id);
}
