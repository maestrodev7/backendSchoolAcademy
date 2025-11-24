package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.ClassRoomSubjectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaClassRoomSubjectRepository extends JpaRepository<ClassRoomSubjectModel, UUID> {
    
    @Query("SELECT crs FROM ClassRoomSubjectModel crs " +
           "LEFT JOIN FETCH crs.subject " +
           "LEFT JOIN FETCH crs.classRoom " +
           "WHERE crs.classRoom.id = :classRoomId")
    List<ClassRoomSubjectModel> findByClassRoomId(@Param("classRoomId") UUID classRoomId);

    @Query("SELECT crs FROM ClassRoomSubjectModel crs " +
           "LEFT JOIN FETCH crs.subject " +
           "LEFT JOIN FETCH crs.classRoom " +
           "WHERE crs.subject.id = :subjectId")
    List<ClassRoomSubjectModel> findBySubjectId(@Param("subjectId") UUID subjectId);

    @Query("SELECT crs FROM ClassRoomSubjectModel crs " +
           "LEFT JOIN FETCH crs.subject " +
           "LEFT JOIN FETCH crs.classRoom " +
           "WHERE crs.classRoom.id = :classRoomId AND crs.subject.id = :subjectId")
    Optional<ClassRoomSubjectModel> findByClassRoomAndSubject(
            @Param("classRoomId") UUID classRoomId, 
            @Param("subjectId") UUID subjectId);
}

