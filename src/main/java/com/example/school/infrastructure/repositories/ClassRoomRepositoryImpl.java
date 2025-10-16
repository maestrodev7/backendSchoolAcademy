package com.example.school.infrastructure.repositories;

import com.example.school.domain.entities.ClassRoom;
import com.example.school.domain.repositories.ClassRoomRepositoryInterface;
import com.example.school.infrastructure.mappers.ClassRoomMapper;
import com.example.school.infrastructure.models.ClassRoomModel;
import com.example.school.infrastructure.repositories.jpa.JpaClassRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ClassRoomRepositoryImpl implements ClassRoomRepositoryInterface {

    private final JpaClassRoomRepository jpaClassRoomRepository;

    @Override
    public ClassRoom save(ClassRoom classRoom) {
        ClassRoomModel model = ClassRoomMapper.toModel(classRoom);
        ClassRoomModel saved = jpaClassRoomRepository.save(model);
        return ClassRoomMapper.toDomain(saved);
    }

    @Override
    public Optional<ClassRoom> findById(UUID id) {
        return jpaClassRoomRepository.findById(id).map(ClassRoomMapper::toDomain);
    }

    @Override
    public List<ClassRoom> findAll() {
        return jpaClassRoomRepository.findAll()
                .stream()
                .map(ClassRoomMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassRoom> findBySchool(UUID schoolId) {
        return jpaClassRoomRepository.findBySchool(schoolId)
                .stream()
                .map(ClassRoomMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassRoom> findByAcademicYear(UUID academicYearId) {
        return jpaClassRoomRepository.findByAcademicYear(academicYearId)
                .stream()
                .map(ClassRoomMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaClassRoomRepository.deleteById(id);
    }
}
