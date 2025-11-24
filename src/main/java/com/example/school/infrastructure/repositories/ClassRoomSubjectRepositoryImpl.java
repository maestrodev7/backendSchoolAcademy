package com.example.school.infrastructure.repositories;

import com.example.school.domain.entities.ClassRoomSubject;
import com.example.school.domain.repositories.ClassRoomSubjectRepositoryInterface;
import com.example.school.infrastructure.mappers.ClassRoomSubjectMapper;
import com.example.school.infrastructure.models.ClassRoomSubjectModel;
import com.example.school.infrastructure.repositories.jpa.JpaClassRoomSubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ClassRoomSubjectRepositoryImpl implements ClassRoomSubjectRepositoryInterface {

    private final JpaClassRoomSubjectRepository jpaClassRoomSubjectRepository;

    @Override
    public ClassRoomSubject save(ClassRoomSubject classRoomSubject) {
        ClassRoomSubjectModel model = ClassRoomSubjectMapper.toModel(classRoomSubject);
        ClassRoomSubjectModel saved = jpaClassRoomSubjectRepository.save(model);
        return ClassRoomSubjectMapper.toDomain(saved);
    }

    @Override
    public Optional<ClassRoomSubject> findById(UUID id) {
        return jpaClassRoomSubjectRepository.findById(id)
                .map(ClassRoomSubjectMapper::toDomain);
    }

    @Override
    public List<ClassRoomSubject> findAll() {
        return jpaClassRoomSubjectRepository.findAll()
                .stream()
                .map(ClassRoomSubjectMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassRoomSubject> findByClassRoomId(UUID classRoomId) {
        return jpaClassRoomSubjectRepository.findByClassRoomId(classRoomId)
                .stream()
                .map(ClassRoomSubjectMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassRoomSubject> findBySubjectId(UUID subjectId) {
        return jpaClassRoomSubjectRepository.findBySubjectId(subjectId)
                .stream()
                .map(ClassRoomSubjectMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ClassRoomSubject> findByClassRoomAndSubject(UUID classRoomId, UUID subjectId) {
        return jpaClassRoomSubjectRepository.findByClassRoomAndSubject(classRoomId, subjectId)
                .map(ClassRoomSubjectMapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaClassRoomSubjectRepository.deleteById(id);
    }

    @Override
    public void deleteByClassRoomAndSubject(UUID classRoomId, UUID subjectId) {
        jpaClassRoomSubjectRepository.findByClassRoomAndSubject(classRoomId, subjectId)
                .ifPresent(jpaClassRoomSubjectRepository::delete);
    }
}

