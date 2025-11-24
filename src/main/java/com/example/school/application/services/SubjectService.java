package com.example.school.application.services;

import com.example.school.common.dto.SubjectDto;
import com.example.school.common.exceptions.ResourceAlreadyExistsException;
import com.example.school.common.mapper.SubjectMapper;
import com.example.school.domain.entities.ClassRoomSubject;
import com.example.school.domain.entities.Subject;
import com.example.school.domain.repositories.ClassRoomSubjectRepositoryInterface;
import com.example.school.domain.repositories.SubjectRepositoryInterface;
import com.example.school.domain.services.SubjectServiceInterface;
import com.example.school.presenation.validators.SubjectRequestValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectService implements SubjectServiceInterface {

    private final SubjectRepositoryInterface subjectRepository;
    private final ClassRoomSubjectRepositoryInterface classRoomSubjectRepository;

    @Override
    public SubjectDto createSubject(SubjectRequestValidator request) {
        if (subjectRepository.existsByCode(request.getCode())) {
            throw new ResourceAlreadyExistsException("Une matière avec ce code existe déjà");
        }

        Subject subject = new Subject();
        subject.setCode(request.getCode());
        subject.setName(request.getName());
        subject.setDescription(request.getDescription());

        Subject saved = subjectRepository.save(subject);
        return SubjectMapper.toDto(saved);
    }

    @Override
    public List<SubjectDto> getAllSubjects() {
        return subjectRepository.findAll()
                .stream()
                .map(SubjectMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SubjectDto getSubjectById(UUID id) {
        return subjectRepository.findById(id)
                .map(SubjectMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Matière non trouvée"));
    }

    @Override
    public SubjectDto updateSubject(UUID id, SubjectRequestValidator request) {
        Subject existing = subjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Matière non trouvée"));

        // Vérifier si le code change et s'il existe déjà
        if (!existing.getCode().equals(request.getCode()) && 
            subjectRepository.existsByCode(request.getCode())) {
            throw new ResourceAlreadyExistsException("Une matière avec ce code existe déjà");
        }

        existing.setCode(request.getCode());
        existing.setName(request.getName());
        existing.setDescription(request.getDescription());

        Subject updated = subjectRepository.save(existing);
        return SubjectMapper.toDto(updated);
    }

    @Override
    public void deleteSubject(UUID id) {
        if (!subjectRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException("Matière non trouvée");
        }
        subjectRepository.deleteById(id);
    }

    @Override
    public List<SubjectDto> getSubjectsByClassroom(UUID classroomId) {
        List<ClassRoomSubject> associations = classRoomSubjectRepository.findByClassRoomId(classroomId);
        
        return associations.stream()
                .map(ClassRoomSubject::getSubject)
                .map(SubjectMapper::toDto)
                .collect(Collectors.toList());
    }
}

