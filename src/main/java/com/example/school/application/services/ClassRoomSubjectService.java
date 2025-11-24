package com.example.school.application.services;

import com.example.school.common.dto.ClassRoomSubjectDto;
import com.example.school.common.mapper.SubjectMapper;
import com.example.school.domain.entities.ClassRoom;
import com.example.school.domain.entities.ClassRoomSubject;
import com.example.school.domain.entities.Subject;
import com.example.school.domain.repositories.ClassRoomRepositoryInterface;
import com.example.school.domain.repositories.ClassRoomSubjectRepositoryInterface;
import com.example.school.domain.repositories.SubjectRepositoryInterface;
import com.example.school.domain.services.ClassRoomSubjectServiceInterface;
import com.example.school.presenation.validators.ClassRoomSubjectRequestValidator;
import com.example.school.presenation.validators.UpdateClassRoomSubjectValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassRoomSubjectService implements ClassRoomSubjectServiceInterface {

    private final ClassRoomSubjectRepositoryInterface classRoomSubjectRepository;
    private final SubjectRepositoryInterface subjectRepository;
    private final ClassRoomRepositoryInterface classRoomRepository;

    @Override
    @Transactional
    public ClassRoomSubjectDto create(ClassRoomSubjectRequestValidator request) {
        // Vérifier que la matière existe
        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new EntityNotFoundException("Matière non trouvée"));

        // Vérifier que la classe existe
        ClassRoom classRoom = classRoomRepository.findById(request.getClassRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Classe non trouvée"));

        // Vérifier si l'association existe déjà
        var existing = classRoomSubjectRepository.findByClassRoomAndSubject(
                request.getClassRoomId(), 
                request.getSubjectId()
        );

        ClassRoomSubject association;
        if (existing.isPresent()) {
            // Mettre à jour le coefficient existant (upsert behavior)
            association = existing.get();
            association.setCoefficient(request.getCoefficient());
        } else {
            // Créer une nouvelle association
            association = new ClassRoomSubject();
            association.setSubject(subject);
            association.setClassRoom(classRoom);
            association.setCoefficient(request.getCoefficient());
        }

        ClassRoomSubject saved = classRoomSubjectRepository.save(association);
        return SubjectMapper.toClassRoomSubjectDto(saved);
    }

    @Override
    public ClassRoomSubjectDto getById(UUID id) {
        return classRoomSubjectRepository.findById(id)
                .map(SubjectMapper::toClassRoomSubjectDto)
                .orElseThrow(() -> new EntityNotFoundException("Association matière-classe non trouvée"));
    }

    @Override
    public List<ClassRoomSubjectDto> getAll() {
        return classRoomSubjectRepository.findAll()
                .stream()
                .map(SubjectMapper::toClassRoomSubjectDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ClassRoomSubjectDto update(UUID id, UpdateClassRoomSubjectValidator request) {
        ClassRoomSubject existing = classRoomSubjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Association matière-classe non trouvée"));

        existing.setCoefficient(request.getCoefficient());
        ClassRoomSubject updated = classRoomSubjectRepository.save(existing);
        return SubjectMapper.toClassRoomSubjectDto(updated);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!classRoomSubjectRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException("Association matière-classe non trouvée");
        }
        classRoomSubjectRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByClassRoomAndSubject(UUID classRoomId, UUID subjectId) {
        var existing = classRoomSubjectRepository.findByClassRoomAndSubject(classRoomId, subjectId);
        if (existing.isEmpty()) {
            throw new EntityNotFoundException("Association matière-classe non trouvée");
        }
        classRoomSubjectRepository.deleteByClassRoomAndSubject(classRoomId, subjectId);
    }

    @Override
    public List<ClassRoomSubjectDto> getByClassRoom(UUID classRoomId) {
        return classRoomSubjectRepository.findByClassRoomId(classRoomId)
                .stream()
                .map(SubjectMapper::toClassRoomSubjectDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassRoomSubjectDto> getBySubject(UUID subjectId) {
        return classRoomSubjectRepository.findBySubjectId(subjectId)
                .stream()
                .map(SubjectMapper::toClassRoomSubjectDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClassRoomSubjectDto getByClassRoomAndSubject(UUID classRoomId, UUID subjectId) {
        return classRoomSubjectRepository.findByClassRoomAndSubject(classRoomId, subjectId)
                .map(SubjectMapper::toClassRoomSubjectDto)
                .orElseThrow(() -> new EntityNotFoundException("Association matière-classe non trouvée"));
    }
}

