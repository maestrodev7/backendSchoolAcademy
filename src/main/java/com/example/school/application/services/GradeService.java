package com.example.school.application.services;

import com.example.school.common.dto.GradeDto;
import com.example.school.common.exceptions.ResourceAlreadyExistsException;
import com.example.school.common.mapper.GradeMapper;
import com.example.school.domain.entities.Competence;
import com.example.school.domain.entities.Grade;
import com.example.school.domain.entities.Sequence;
import com.example.school.domain.entities.Term;
import com.example.school.domain.entities.User;
import com.example.school.domain.repositories.CompetenceRepositoryInterface;
import com.example.school.domain.repositories.GradeRepositoryInterface;
import com.example.school.domain.repositories.SequenceRepositoryInterface;
import com.example.school.domain.repositories.TermRepositoryInterface;
import com.example.school.domain.repositories.UserRepositoryInterface;
import com.example.school.domain.services.GradeServiceInterface;
import com.example.school.presenation.validators.GradeRequestValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradeService implements GradeServiceInterface {

    private final GradeRepositoryInterface gradeRepository;
    private final CompetenceRepositoryInterface competenceRepository;
    private final TermRepositoryInterface termRepository;
    private final SequenceRepositoryInterface sequenceRepository;
    private final UserRepositoryInterface userRepository;

    @Override
    @Transactional
    public GradeDto createGrade(GradeRequestValidator request) {
        // Vérifier que la compétence existe
        Competence competence = competenceRepository.findById(request.getCompetenceId())
                .orElseThrow(() -> new EntityNotFoundException("Compétence non trouvée"));

        // Vérifier que l'élève existe
        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Élève non trouvé"));

        // Vérifier que le trimestre existe
        Term term = termRepository.findById(request.getTermId())
                .orElseThrow(() -> new EntityNotFoundException("Trimestre non trouvé"));

        // Vérifier la séquence si fournie
        Sequence sequence = null;
        if (request.getSequenceId() != null) {
            sequence = sequenceRepository.findById(request.getSequenceId())
                    .orElseThrow(() -> new EntityNotFoundException("Séquence non trouvée"));
            // Vérifier que la séquence appartient au trimestre
            if (!sequence.getTermId().equals(request.getTermId())) {
                throw new IllegalArgumentException("La séquence n'appartient pas à ce trimestre");
            }
        }

        // Vérifier si une note existe déjà pour cette compétence, élève et trimestre
        if (gradeRepository.findByCompetenceIdAndStudentIdAndTermId(
                request.getCompetenceId(), request.getStudentId(), request.getTermId()).isPresent()) {
            throw new ResourceAlreadyExistsException("Une note existe déjà pour cette compétence, cet élève et ce trimestre");
        }

        // Calculer M x coef si noteM20 et coefficient sont fournis
        BigDecimal mXCoef = null;
        if (request.getNoteM20() != null && request.getCoefficient() != null) {
            mXCoef = request.getNoteM20().multiply(request.getCoefficient());
        }

        Grade grade = new Grade();
        grade.setCompetence(competence);
        grade.setStudent(student);
        grade.setTerm(term);
        grade.setSequence(sequence);
        grade.setNoteN20(request.getNoteN20());
        grade.setNoteM20(request.getNoteM20());
        grade.setCoefficient(request.getCoefficient());
        grade.setMXCoef(mXCoef);
        grade.setCote(request.getCote());
        grade.setMinScore(request.getMinScore());
        grade.setMaxScore(request.getMaxScore());
        grade.setAppreciation(request.getAppreciation());

        if (request.getTeacherId() != null) {
            User teacher = userRepository.findById(request.getTeacherId())
                    .orElseThrow(() -> new EntityNotFoundException("Enseignant non trouvé"));
            grade.setTeacher(teacher);
        }

        Grade saved = gradeRepository.save(grade);
        return GradeMapper.toDto(saved);
    }

    @Override
    public GradeDto getGradeById(UUID id) {
        return gradeRepository.findById(id)
                .map(GradeMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Note non trouvée"));
    }

    @Override
    public List<GradeDto> getAllGrades() {
        return gradeRepository.findAll()
                .stream()
                .map(GradeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GradeDto> getGradesByStudentIdAndTermId(UUID studentId, UUID termId) {
        return gradeRepository.findByStudentIdAndTermId(studentId, termId)
                .stream()
                .map(GradeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GradeDto> getGradesByStudentIdAndSequenceId(UUID studentId, UUID sequenceId) {
        return gradeRepository.findByStudentIdAndSequenceId(studentId, sequenceId)
                .stream()
                .map(GradeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GradeDto> getGradesByStudentIdAndAcademicYearId(UUID studentId, UUID academicYearId) {
        return gradeRepository.findByStudentIdAndAcademicYearId(studentId, academicYearId)
                .stream()
                .map(GradeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GradeDto updateGrade(UUID id, GradeRequestValidator request) {
        Grade existing = gradeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note non trouvée"));

        // Vérifier que la compétence existe
        Competence competence = competenceRepository.findById(request.getCompetenceId())
                .orElseThrow(() -> new EntityNotFoundException("Compétence non trouvée"));

        // Vérifier que le trimestre existe
        Term term = termRepository.findById(request.getTermId())
                .orElseThrow(() -> new EntityNotFoundException("Trimestre non trouvé"));

        // Vérifier la séquence si fournie
        Sequence sequence = null;
        if (request.getSequenceId() != null) {
            sequence = sequenceRepository.findById(request.getSequenceId())
                    .orElseThrow(() -> new EntityNotFoundException("Séquence non trouvée"));
        }

        // Calculer M x coef
        BigDecimal mXCoef = null;
        if (request.getNoteM20() != null && request.getCoefficient() != null) {
            mXCoef = request.getNoteM20().multiply(request.getCoefficient());
        }

        existing.setCompetence(competence);
        existing.setTerm(term);
        existing.setSequence(sequence);
        existing.setNoteN20(request.getNoteN20());
        existing.setNoteM20(request.getNoteM20());
        existing.setCoefficient(request.getCoefficient());
        existing.setMXCoef(mXCoef);
        existing.setCote(request.getCote());
        existing.setMinScore(request.getMinScore());
        existing.setMaxScore(request.getMaxScore());
        existing.setAppreciation(request.getAppreciation());

        if (request.getTeacherId() != null) {
            User teacher = userRepository.findById(request.getTeacherId())
                    .orElseThrow(() -> new EntityNotFoundException("Enseignant non trouvé"));
            existing.setTeacher(teacher);
        }

        Grade updated = gradeRepository.save(existing);
        return GradeMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteGrade(UUID id) {
        if (!gradeRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException("Note non trouvée");
        }
        gradeRepository.deleteById(id);
    }
}

