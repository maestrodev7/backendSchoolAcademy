package com.example.school.application.services;

import com.example.school.common.dto.SequenceDto;
import com.example.school.common.exceptions.ResourceAlreadyExistsException;
import com.example.school.common.mapper.SequenceMapper;
import com.example.school.domain.entities.Sequence;
import com.example.school.domain.entities.Term;
import com.example.school.domain.repositories.SchoolRepositoryInterface;
import com.example.school.domain.repositories.SequenceRepositoryInterface;
import com.example.school.domain.repositories.TermRepositoryInterface;
import com.example.school.domain.services.SequenceServiceInterface;
import com.example.school.presenation.validators.SequenceRequestValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SequenceService implements SequenceServiceInterface {

    private final SequenceRepositoryInterface sequenceRepository;
    private final TermRepositoryInterface termRepository;
    private final SchoolRepositoryInterface schoolRepository;

    @Override
    @Transactional
    public SequenceDto createSequence(SequenceRequestValidator request) {
        // Vérifier que l'école existe
        schoolRepository.findById(request.getSchoolId())
                .orElseThrow(() -> new EntityNotFoundException("École non trouvée"));

        // Vérifier que le trimestre existe
        Term term = termRepository.findById(request.getTermId())
                .orElseThrow(() -> new EntityNotFoundException("Trimestre non trouvé"));

        // Vérifier que le trimestre appartient à l'école
        if (!term.getSchoolId().equals(request.getSchoolId())) {
            throw new IllegalArgumentException("Le trimestre n'appartient pas à cette école");
        }

        // Vérifier qu'une séquence avec ce numéro n'existe pas déjà pour ce trimestre
        if (sequenceRepository.findByTermIdAndNumber(request.getTermId(), request.getNumber()).isPresent()) {
            throw new ResourceAlreadyExistsException("Une séquence avec le numéro " + request.getNumber() + " existe déjà pour ce trimestre");
        }

        // Vérifier que la date de fin est après la date de début
        if (request.getEndDate().isBefore(request.getStartDate()) || request.getEndDate().isEqual(request.getStartDate())) {
            throw new IllegalArgumentException("La date de fin doit être après la date de début");
        }

        // Vérifier que les dates de la séquence sont dans la plage du trimestre
        if (request.getStartDate().isBefore(term.getStartDate()) || request.getEndDate().isAfter(term.getEndDate())) {
            throw new IllegalArgumentException("Les dates de la séquence doivent être dans la plage du trimestre");
        }

        Sequence sequence = new Sequence();
        sequence.setName(request.getName());
        sequence.setNumber(request.getNumber());
        sequence.setTermId(request.getTermId());
        sequence.setSchoolId(request.getSchoolId());
        sequence.setStartDate(request.getStartDate());
        sequence.setEndDate(request.getEndDate());
        sequence.setActive(false);

        Sequence saved = sequenceRepository.save(sequence);
        return SequenceMapper.toDto(saved);
    }

    @Override
    public SequenceDto getSequenceById(UUID id) {
        return sequenceRepository.findById(id)
                .map(SequenceMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Séquence non trouvée"));
    }

    @Override
    public List<SequenceDto> getAllSequences() {
        return sequenceRepository.findAll()
                .stream()
                .map(SequenceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SequenceDto> getSequencesByTerm(UUID termId) {
        return sequenceRepository.findByTermId(termId)
                .stream()
                .map(SequenceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SequenceDto> getSequencesByAcademicYear(UUID academicYearId) {
        return sequenceRepository.findByAcademicYearId(academicYearId)
                .stream()
                .map(SequenceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SequenceDto updateSequence(UUID id, SequenceRequestValidator request) {
        Sequence existing = sequenceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Séquence non trouvée"));

        // Vérifier que l'école existe
        schoolRepository.findById(request.getSchoolId())
                .orElseThrow(() -> new EntityNotFoundException("École non trouvée"));

        // Vérifier qu'une autre séquence avec ce numéro n'existe pas déjà pour ce trimestre
        sequenceRepository.findByTermIdAndNumber(request.getTermId(), request.getNumber())
                .ifPresent(sequence -> {
                    if (!sequence.getId().equals(id)) {
                        throw new ResourceAlreadyExistsException("Une séquence avec le numéro " + request.getNumber() + " existe déjà pour ce trimestre");
                    }
                });

        // Vérifier que le trimestre existe
        Term term = termRepository.findById(request.getTermId())
                .orElseThrow(() -> new EntityNotFoundException("Trimestre non trouvé"));

        // Vérifier que le trimestre appartient à l'école
        if (!term.getSchoolId().equals(request.getSchoolId())) {
            throw new IllegalArgumentException("Le trimestre n'appartient pas à cette école");
        }

        // Vérifier que les dates de la séquence sont dans la plage du trimestre
        if (request.getStartDate().isBefore(term.getStartDate()) || request.getEndDate().isAfter(term.getEndDate())) {
            throw new IllegalArgumentException("Les dates de la séquence doivent être dans la plage du trimestre");
        }

        existing.setName(request.getName());
        existing.setNumber(request.getNumber());
        existing.setTermId(request.getTermId());
        existing.setSchoolId(request.getSchoolId());
        existing.setStartDate(request.getStartDate());
        existing.setEndDate(request.getEndDate());

        Sequence updated = sequenceRepository.save(existing);
        return SequenceMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteSequence(UUID id) {
        if (!sequenceRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException("Séquence non trouvée");
        }
        sequenceRepository.deleteById(id);
    }

    @Override
    @Transactional
    public SequenceDto setActiveSequence(UUID termId, UUID sequenceId) {
        // Désactiver toutes les séquences de ce trimestre
        List<Sequence> sequences = sequenceRepository.findByTermId(termId);
        for (Sequence sequence : sequences) {
            if (sequence.isActive()) {
                sequence.setActive(false);
                sequenceRepository.save(sequence);
            }
        }

        // Activer la séquence spécifiée
        Sequence sequence = sequenceRepository.findById(sequenceId)
                .orElseThrow(() -> new EntityNotFoundException("Séquence non trouvée"));

        if (!sequence.getTermId().equals(termId)) {
            throw new IllegalArgumentException("La séquence n'appartient pas à ce trimestre");
        }

        sequence.setActive(true);
        Sequence updated = sequenceRepository.save(sequence);
        return SequenceMapper.toDto(updated);
    }
}

