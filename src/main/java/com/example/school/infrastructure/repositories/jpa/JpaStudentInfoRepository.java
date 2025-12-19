package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.StudentInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaStudentInfoRepository extends JpaRepository<StudentInfoModel, UUID> {
    Optional<StudentInfoModel> findByStudentId(UUID studentId);
    boolean existsByUniqueIdentifier(String uniqueIdentifier);
    Optional<StudentInfoModel> findByUniqueIdentifier(String uniqueIdentifier);

    @Query("SELECT COUNT(s) FROM StudentInfoModel s WHERE s.uniqueIdentifier LIKE CONCAT('STU-', :year, '-%')")
    long countByYear(@Param("year") String year);

    @Query("SELECT s FROM StudentInfoModel s WHERE s.student.id IN :studentIds")
    List<StudentInfoModel> findByStudentIds(@Param("studentIds") List<UUID> studentIds);
}

