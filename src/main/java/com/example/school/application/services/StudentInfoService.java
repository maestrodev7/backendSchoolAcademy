package com.example.school.application.services;

import com.example.school.common.dto.StudentInfoDto;
import com.example.school.common.exceptions.ResourceAlreadyExistsException;
import com.example.school.common.mapper.StudentInfoMapper;
import com.example.school.domain.entities.StudentInfo;
import com.example.school.domain.entities.User;
import com.example.school.domain.repositories.StudentInfoRepositoryInterface;
import com.example.school.domain.repositories.UserRepositoryInterface;
import com.example.school.domain.services.StudentInfoServiceInterface;
import com.example.school.presenation.validators.StudentInfoRequestValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentInfoService implements StudentInfoServiceInterface {

    private final StudentInfoRepositoryInterface studentInfoRepository;
    private final UserRepositoryInterface userRepository;

    @Override
    @Transactional
    public StudentInfoDto createStudentInfo(StudentInfoRequestValidator request) {
        // Vérifier que l'élève existe
        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Élève non trouvé"));

        // Vérifier si des informations existent déjà pour cet élève
        if (studentInfoRepository.findByStudentId(request.getStudentId()).isPresent()) {
            throw new ResourceAlreadyExistsException("Des informations existent déjà pour cet élève");
        }

        // Vérifier l'identifiant unique s'il est fourni
        if (request.getUniqueIdentifier() != null && !request.getUniqueIdentifier().isEmpty()) {
            if (studentInfoRepository.existsByUniqueIdentifier(request.getUniqueIdentifier())) {
                throw new ResourceAlreadyExistsException("Cet identifiant unique est déjà utilisé");
            }
        }

        StudentInfo info = new StudentInfo();
        info.setStudent(student);
        info.setUniqueIdentifier(request.getUniqueIdentifier());
        info.setBirthDate(request.getBirthDate());
        info.setBirthPlace(request.getBirthPlace());
        info.setGender(request.getGender());
        info.setRepeating(request.getIsRepeating() != null ? request.getIsRepeating() : false);
        info.setParentNames(request.getParentNames());
        info.setParentContacts(request.getParentContacts());
        info.setPhotoUrl(request.getPhotoUrl());

        StudentInfo saved = studentInfoRepository.save(info);
        return StudentInfoMapper.toDto(saved);
    }

    @Override
    public StudentInfoDto getStudentInfoById(UUID id) {
        return studentInfoRepository.findById(id)
                .map(StudentInfoMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Informations de l'élève non trouvées"));
    }

    @Override
    public StudentInfoDto getStudentInfoByStudentId(UUID studentId) {
        return studentInfoRepository.findByStudentId(studentId)
                .map(StudentInfoMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Informations de l'élève non trouvées"));
    }

    @Override
    @Transactional
    public StudentInfoDto updateStudentInfo(UUID id, StudentInfoRequestValidator request) {
        StudentInfo existing = studentInfoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Informations de l'élève non trouvées"));

        // Vérifier l'identifiant unique s'il est fourni et différent
        if (request.getUniqueIdentifier() != null && !request.getUniqueIdentifier().isEmpty()) {
            if (!request.getUniqueIdentifier().equals(existing.getUniqueIdentifier())) {
                if (studentInfoRepository.existsByUniqueIdentifier(request.getUniqueIdentifier())) {
                    throw new ResourceAlreadyExistsException("Cet identifiant unique est déjà utilisé");
                }
            }
        }

        existing.setUniqueIdentifier(request.getUniqueIdentifier());
        existing.setBirthDate(request.getBirthDate());
        existing.setBirthPlace(request.getBirthPlace());
        existing.setGender(request.getGender());
        if (request.getIsRepeating() != null) {
            existing.setRepeating(request.getIsRepeating());
        }
        existing.setParentNames(request.getParentNames());
        existing.setParentContacts(request.getParentContacts());
        existing.setPhotoUrl(request.getPhotoUrl());

        StudentInfo updated = studentInfoRepository.save(existing);
        return StudentInfoMapper.toDto(updated);
    }
}

