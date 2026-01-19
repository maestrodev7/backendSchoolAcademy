package com.example.school.infrastructure.repositories;

import com.example.school.domain.entities.StudentRegistration;
import com.example.school.domain.repositories.StudentRegistrationRepositoryInterface;
import com.example.school.infrastructure.mappers.StudentRegistrationMapper;
import com.example.school.infrastructure.models.StudentRegistrationModel;
import com.example.school.infrastructure.repositories.jpa.JpaStudentRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class StudentRegistrationRepositoryImpl implements StudentRegistrationRepositoryInterface {

    private final JpaStudentRegistrationRepository jpaRepository;

    @Override
    public StudentRegistration save(StudentRegistration registration) {
        StudentRegistrationModel model = StudentRegistrationMapper.toModel(registration);
        StudentRegistrationModel saved = jpaRepository.save(model);
        return StudentRegistrationMapper.toDomain(saved);
    }

    @Override
    public Optional<StudentRegistration> findById(UUID id) {
        return jpaRepository.findById(id).map(StudentRegistrationMapper::toDomain);
    }

    @Override
    public List<StudentRegistration> findByClassRoom(UUID classRoomId) {
        return jpaRepository.findByClassRoom_Id(classRoomId)
                .stream()
                .map(StudentRegistrationMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentRegistration> findByAcademicYear(UUID academicYearId) {
        return jpaRepository.findByAcademicYear_Id(academicYearId)
                .stream()
                .map(StudentRegistrationMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentRegistration> findBySchool(UUID schoolId) {
        return jpaRepository.findByClassRoom_School_Id(schoolId)
                .stream()
                .map(StudentRegistrationMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentRegistration> findByStudent(UUID studentId) {
        return jpaRepository.findByStudent_Id(studentId)
                .stream()
                .map(StudentRegistrationMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<StudentRegistration> findByStudentAndAcademicYear(UUID studentId, UUID academicYearId) {
        return jpaRepository.findByStudent_IdAndAcademicYear_Id(studentId, academicYearId)
                .map(StudentRegistrationMapper::toDomain);
    }

    @Override
    public List<StudentRegistration> findByClassRoomAndAcademicYear(UUID classRoomId, UUID academicYearId) {
        return jpaRepository.findByClassRoom_IdAndAcademicYear_Id(classRoomId, academicYearId)
                .stream()
                .map(StudentRegistrationMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
