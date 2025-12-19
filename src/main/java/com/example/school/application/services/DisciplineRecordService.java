package com.example.school.application.services;

import com.example.school.common.dto.DisciplineRecordDto;
import com.example.school.common.mapper.DisciplineRecordMapper;
import com.example.school.domain.entities.ClassRoom;
import com.example.school.domain.entities.DisciplineRecord;
import com.example.school.domain.entities.Term;
import com.example.school.domain.entities.User;
import com.example.school.domain.repositories.ClassRoomRepositoryInterface;
import com.example.school.domain.repositories.DisciplineRecordRepositoryInterface;
import com.example.school.domain.repositories.TermRepositoryInterface;
import com.example.school.domain.repositories.UserRepositoryInterface;
import com.example.school.domain.services.DisciplineRecordServiceInterface;
import com.example.school.presenation.validators.DisciplineRecordRequestValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DisciplineRecordService implements DisciplineRecordServiceInterface {

    private final DisciplineRecordRepositoryInterface disciplineRecordRepository;
    private final UserRepositoryInterface userRepository;
    private final TermRepositoryInterface termRepository;
    private final ClassRoomRepositoryInterface classRoomRepository;

    @Override
    @Transactional
    public DisciplineRecordDto createDisciplineRecord(DisciplineRecordRequestValidator request) {
        // Vérifier que l'élève existe
        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Élève non trouvé"));

        // Vérifier que le trimestre existe
        Term term = termRepository.findById(request.getTermId())
                .orElseThrow(() -> new EntityNotFoundException("Trimestre non trouvé"));

        // Vérifier que la classe existe
        ClassRoom classRoom = classRoomRepository.findById(request.getClassRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Classe non trouvée"));

        // Vérifier si un record existe déjà pour cet élève et ce trimestre
        if (disciplineRecordRepository.findByStudentIdAndTermId(request.getStudentId(), request.getTermId()).isPresent()) {
            throw new jakarta.persistence.EntityExistsException("Un enregistrement disciplinaire existe déjà pour cet élève et ce trimestre");
        }

        DisciplineRecord record = new DisciplineRecord();
        record.setStudent(student);
        record.setTerm(term);
        record.setClassRoom(classRoom);
        record.setUnjustifiedAbsencesHours(request.getUnjustifiedAbsencesHours());
        record.setJustifiedAbsencesHours(request.getJustifiedAbsencesHours());
        record.setLateCount(request.getLateCount());
        record.setDetentionHours(request.getDetentionHours());
        record.setConductWarning(request.getConductWarning());
        record.setConductBlame(request.getConductBlame());
        record.setExclusionDays(request.getExclusionDays());
        record.setPermanentExclusion(request.getPermanentExclusion());

        DisciplineRecord saved = disciplineRecordRepository.save(record);
        return DisciplineRecordMapper.toDto(saved);
    }

    @Override
    public DisciplineRecordDto getDisciplineRecordById(UUID id) {
        return disciplineRecordRepository.findById(id)
                .map(DisciplineRecordMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Enregistrement disciplinaire non trouvé"));
    }

    @Override
    public DisciplineRecordDto getDisciplineRecordByStudentIdAndTermId(UUID studentId, UUID termId) {
        return disciplineRecordRepository.findByStudentIdAndTermId(studentId, termId)
                .map(DisciplineRecordMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Enregistrement disciplinaire non trouvé"));
    }

    @Override
    public List<DisciplineRecordDto> getDisciplineRecordsByStudentIdAndAcademicYearId(UUID studentId, UUID academicYearId) {
        return disciplineRecordRepository.findByStudentIdAndAcademicYearId(studentId, academicYearId)
                .stream()
                .map(DisciplineRecordMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DisciplineRecordDto> getDisciplineRecordsByAcademicYearId(UUID academicYearId) {
        return disciplineRecordRepository.findByAcademicYearId(academicYearId)
                .stream()
                .map(DisciplineRecordMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DisciplineRecordDto updateDisciplineRecord(UUID id, DisciplineRecordRequestValidator request) {
        DisciplineRecord existing = disciplineRecordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Enregistrement disciplinaire non trouvé"));

        existing.setUnjustifiedAbsencesHours(request.getUnjustifiedAbsencesHours());
        existing.setJustifiedAbsencesHours(request.getJustifiedAbsencesHours());
        existing.setLateCount(request.getLateCount());
        existing.setDetentionHours(request.getDetentionHours());
        existing.setConductWarning(request.getConductWarning());
        existing.setConductBlame(request.getConductBlame());
        existing.setExclusionDays(request.getExclusionDays());
        existing.setPermanentExclusion(request.getPermanentExclusion());

        DisciplineRecord updated = disciplineRecordRepository.save(existing);
        return DisciplineRecordMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteDisciplineRecord(UUID id) {
        if (!disciplineRecordRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException("Enregistrement disciplinaire non trouvé");
        }
        disciplineRecordRepository.deleteById(id);
    }
}

