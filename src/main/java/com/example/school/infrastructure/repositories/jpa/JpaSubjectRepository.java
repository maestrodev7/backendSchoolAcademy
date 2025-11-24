package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.SubjectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface JpaSubjectRepository extends JpaRepository<SubjectModel, UUID> {
    Optional<SubjectModel> findByCode(String code);
    boolean existsByCode(String code);

    @Query("SELECT DISTINCT s FROM SubjectModel s LEFT JOIN FETCH s.classRoomSubjects WHERE s.id = :id")
    Optional<SubjectModel> findByIdWithClassRooms(@Param("id") UUID id);
    
    @Query("SELECT DISTINCT s FROM SubjectModel s LEFT JOIN FETCH s.classRoomSubjects")
    java.util.List<SubjectModel> findAllWithClassRooms();
}

