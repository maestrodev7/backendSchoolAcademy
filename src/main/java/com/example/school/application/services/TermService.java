package com.example.school.application.services;

import com.example.school.common.dto.TermDto;
import com.example.school.common.exceptions.ResourceAlreadyExistsException;
import com.example.school.common.mapper.TermMapper;
import com.example.school.domain.entities.Term;
import com.example.school.domain.repositories.AcademicYearRepositoryInterface;
import com.example.school.infrastructure.repositories.AcademicYearRepositoryImpl;
import com.example.school.domain.repositories.SchoolRepositoryInterface;
import com.example.school.domain.repositories.TermRepositoryInterface;
import com.example.school.domain.services.TermServiceInterface;
import com.example.school.presenation.validators.TermRequestValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TermService implements TermServiceInterface {

    private final TermRepositoryInterface termRepository;
    private final AcademicYearRepositoryInterface academicYearRepository;
    private final AcademicYearRepositoryImpl academicYearRepositoryImpl;
    private final SchoolRepositoryInterface schoolRepository;

    @Override
    @Transactional
    public TermDto createTerm(TermRequestValidator request) {
        // Vérifier que l'école existe
        schoolRepository.findById(request.getSchoolId())
                .orElseThrow(() -> new EntityNotFoundException("École non trouvée"));

        // Vérifier que l'année académique existe
        academicYearRepository.findById(request.getAcademicYearId())
                .orElseThrow(() -> new EntityNotFoundException("Année académique non trouvée"));

        // Vérifier que l'année académique appartient à l'école
        if (!academicYearRepositoryImpl.existsByAcademicYearIdAndSchoolId(request.getAcademicYearId(), request.getSchoolId())) {
            throw new IllegalArgumentException("L'année académique n'appartient pas à cette école");
        }

        // Vérifier qu'un trimestre avec ce numéro n'existe pas déjà pour cette année et cette école
        if (termRepository.findByAcademicYearIdAndNumber(request.getAcademicYearId(), request.getNumber()).isPresent()) {
            throw new ResourceAlreadyExistsException("Un trimestre avec le numéro " + request.getNumber() + " existe déjà pour cette année académique");
        }

        // Vérifier que la date de fin est après la date de début
        if (request.getEndDate().isBefore(request.getStartDate()) || request.getEndDate().isEqual(request.getStartDate())) {
            throw new IllegalArgumentException("La date de fin doit être après la date de début");
        }

        Term term = new Term();
        term.setName(request.getName());
        term.setNumber(request.getNumber());
        term.setAcademicYearId(request.getAcademicYearId());
        term.setSchoolId(request.getSchoolId());
        term.setStartDate(request.getStartDate());
        term.setEndDate(request.getEndDate());
        term.setActive(false);

        Term saved = termRepository.save(term);
        return TermMapper.toDto(saved);
    }

    @Override
    public TermDto getTermById(UUID id) {
        return termRepository.findById(id)
                .map(TermMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Trimestre non trouvé"));
    }

    @Override
    public List<TermDto> getAllTerms() {
        return termRepository.findAll()
                .stream()
                .map(TermMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TermDto> getTermsByAcademicYear(UUID academicYearId) {
        return termRepository.findByAcademicYearId(academicYearId)
                .stream()
                .map(TermMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TermDto updateTerm(UUID id, TermRequestValidator request) {
        Term existing = termRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trimestre non trouvé"));

        // Vérifier que l'école existe
        schoolRepository.findById(request.getSchoolId())
                .orElseThrow(() -> new EntityNotFoundException("École non trouvée"));

        // Vérifier que l'année académique existe
        academicYearRepository.findById(request.getAcademicYearId())
                .orElseThrow(() -> new EntityNotFoundException("Année académique non trouvée"));

        // Vérifier que l'année académique appartient à l'école
        if (!academicYearRepositoryImpl.existsByAcademicYearIdAndSchoolId(request.getAcademicYearId(), request.getSchoolId())) {
            throw new IllegalArgumentException("L'année académique n'appartient pas à cette école");
        }

        // Vérifier qu'un autre trimestre avec ce numéro n'existe pas déjà pour cette année
        termRepository.findByAcademicYearIdAndNumber(request.getAcademicYearId(), request.getNumber())
                .ifPresent(term -> {
                    if (!term.getId().equals(id)) {
                        throw new ResourceAlreadyExistsException("Un trimestre avec le numéro " + request.getNumber() + " existe déjà pour cette année académique");
                    }
                });

        existing.setName(request.getName());
        existing.setNumber(request.getNumber());
        existing.setAcademicYearId(request.getAcademicYearId());
        existing.setSchoolId(request.getSchoolId());
        existing.setStartDate(request.getStartDate());
        existing.setEndDate(request.getEndDate());

        Term updated = termRepository.save(existing);
        return TermMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteTerm(UUID id) {
        if (!termRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException("Trimestre non trouvé");
        }
        termRepository.deleteById(id);
    }

    @Override
    @Transactional
    public TermDto setActiveTerm(UUID academicYearId, UUID termId) {
        // Désactiver tous les trimestres de cette année académique
        List<Term> terms = termRepository.findByAcademicYearId(academicYearId);
        for (Term term : terms) {
            if (term.isActive()) {
                term.setActive(false);
                termRepository.save(term);
            }
        }

        // Activer le trimestre spécifié
        Term term = termRepository.findById(termId)
                .orElseThrow(() -> new EntityNotFoundException("Trimestre non trouvé"));

        if (!term.getAcademicYearId().equals(academicYearId)) {
            throw new IllegalArgumentException("Le trimestre n'appartient pas à cette année académique");
        }

        term.setActive(true);
        Term updated = termRepository.save(term);
        return TermMapper.toDto(updated);
    }
}

