package com.example.school.application.services;

import com.example.school.common.dto.CompetenceDto;
import com.example.school.common.mapper.CompetenceMapper;
import com.example.school.domain.entities.Competence;
import com.example.school.domain.entities.Subject;
import com.example.school.domain.repositories.CompetenceRepositoryInterface;
import com.example.school.domain.repositories.SubjectRepositoryInterface;
import com.example.school.domain.services.CompetenceServiceInterface;
import com.example.school.presenation.validators.CompetenceRequestValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompetenceService implements CompetenceServiceInterface {

    private final CompetenceRepositoryInterface competenceRepository;
    private final SubjectRepositoryInterface subjectRepository;

    @Override
    @Transactional
    public CompetenceDto createCompetence(CompetenceRequestValidator request) {
        // Vérifier que la matière existe
        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new EntityNotFoundException("Matière non trouvée"));

        Competence competence = new Competence();
        competence.setSubject(subject);
        competence.setDescription(request.getDescription());

        // Si le front ne fournit pas d'ordre, on le génère automatiquement
        Integer orderNumber = request.getOrderNumber();
        if (orderNumber == null) {
            orderNumber = competenceRepository.getNextOrderNumberForSubject(subject.getId());
        }
        competence.setOrderNumber(orderNumber);

        Competence saved = competenceRepository.save(competence);
        return CompetenceMapper.toDto(saved);
    }

    @Override
    public CompetenceDto getCompetenceById(UUID id) {
        return competenceRepository.findById(id)
                .map(CompetenceMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Compétence non trouvée"));
    }

    @Override
    public List<CompetenceDto> getAllCompetences() {
        return competenceRepository.findAll()
                .stream()
                .map(CompetenceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CompetenceDto> getCompetencesBySubjectId(UUID subjectId) {
        return competenceRepository.findBySubjectId(subjectId)
                .stream()
                .map(CompetenceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CompetenceDto updateCompetence(UUID id, CompetenceRequestValidator request) {
        Competence existing = competenceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Compétence non trouvée"));

        // Vérifier que la matière existe
        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new EntityNotFoundException("Matière non trouvée"));

        existing.setSubject(subject);
        existing.setDescription(request.getDescription());
        existing.setOrderNumber(request.getOrderNumber() != null ? request.getOrderNumber() : existing.getOrderNumber());

        Competence updated = competenceRepository.save(existing);
        return CompetenceMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteCompetence(UUID id) {
        if (!competenceRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException("Compétence non trouvée");
        }
        competenceRepository.deleteById(id);
    }
}

