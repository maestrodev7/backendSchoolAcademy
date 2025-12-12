package com.example.school.infrastructure.repositories;

import com.example.school.domain.entities.TeacherSubject;
import com.example.school.domain.repositories.TeacherSubjectRepositoryInterface;
import com.example.school.infrastructure.mappers.TeacherSubjectMapper;
import com.example.school.infrastructure.models.TeacherSubjectModel;
import com.example.school.infrastructure.repositories.jpa.JpaTeacherSubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TeacherSubjectRepositoryImpl implements TeacherSubjectRepositoryInterface {

    private final JpaTeacherSubjectRepository jpaTeacherSubjectRepository;

    @Override
    public TeacherSubject save(TeacherSubject teacherSubject) {
        TeacherSubjectModel model = TeacherSubjectMapper.toModel(teacherSubject);
        TeacherSubjectModel saved = jpaTeacherSubjectRepository.save(model);
        return TeacherSubjectMapper.toDomain(saved);
    }

    @Override
    public Optional<TeacherSubject> findById(UUID id) {
        return jpaTeacherSubjectRepository.findById(id)
                .map(TeacherSubjectMapper::toDomain);
    }

    @Override
    public List<TeacherSubject> findAll() {
        return jpaTeacherSubjectRepository.findAll()
                .stream()
                .map(TeacherSubjectMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherSubject> findByUserSchoolIdAndSchoolId(UUID userSchoolId, UUID schoolId) {
        return jpaTeacherSubjectRepository.findByUserSchoolIdAndSchoolId(userSchoolId, schoolId)
                .stream()
                .map(TeacherSubjectMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherSubject> findBySubjectIdAndSchoolId(UUID subjectId, UUID schoolId) {
        return jpaTeacherSubjectRepository.findBySubjectIdAndSchoolId(subjectId, schoolId)
                .stream()
                .map(TeacherSubjectMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherSubject> findByUserSchoolId(UUID userSchoolId) {
        return jpaTeacherSubjectRepository.findByUserSchoolId(userSchoolId)
                .stream()
                .map(TeacherSubjectMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherSubject> findBySubjectId(UUID subjectId) {
        return jpaTeacherSubjectRepository.findBySubjectId(subjectId)
                .stream()
                .map(TeacherSubjectMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherSubject> findBySchoolId(UUID schoolId) {
        return jpaTeacherSubjectRepository.findBySchoolId(schoolId)
                .stream()
                .map(TeacherSubjectMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TeacherSubject> findByUserSchoolIdAndSubjectIdAndSchoolId(
            UUID userSchoolId, UUID subjectId, UUID schoolId) {
        return jpaTeacherSubjectRepository.findByUserSchoolIdAndSubjectIdAndSchoolId(
                userSchoolId, subjectId, schoolId)
                .map(TeacherSubjectMapper::toDomain);
    }

    @Override
    public List<TeacherSubject> findByAcademicYear(UUID academicYearId) {
        return jpaTeacherSubjectRepository.findByAcademicYearId(academicYearId)
                .stream()
                .map(TeacherSubjectMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaTeacherSubjectRepository.deleteById(id);
    }
}

