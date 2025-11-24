package com.example.school.domain.repositories;

import com.example.school.domain.entities.ClassRoomSubject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClassRoomSubjectRepositoryInterface {
    ClassRoomSubject save(ClassRoomSubject classRoomSubject);
    Optional<ClassRoomSubject> findById(UUID id);
    List<ClassRoomSubject> findAll();
    List<ClassRoomSubject> findByClassRoomId(UUID classRoomId);
    List<ClassRoomSubject> findBySubjectId(UUID subjectId);
    Optional<ClassRoomSubject> findByClassRoomAndSubject(UUID classRoomId, UUID subjectId);
    void deleteById(UUID id);
    void deleteByClassRoomAndSubject(UUID classRoomId, UUID subjectId);
}

