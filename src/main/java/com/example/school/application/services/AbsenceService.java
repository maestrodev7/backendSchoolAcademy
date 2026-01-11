package com.example.school.application.services;

import com.example.school.common.dto.AbsenceDto;
import com.example.school.common.mapper.AbsenceMapper;
import com.example.school.domain.entities.Absence;
import com.example.school.domain.entities.AcademicYear;
import com.example.school.domain.entities.ClassRoom;
import com.example.school.domain.entities.School;
import com.example.school.domain.entities.Subject;
import com.example.school.domain.entities.User;
import com.example.school.domain.repositories.AbsenceRepositoryInterface;
import com.example.school.domain.repositories.AcademicYearRepositoryInterface;
import com.example.school.domain.repositories.ClassRoomRepositoryInterface;
import com.example.school.domain.repositories.SchoolRepositoryInterface;
import com.example.school.domain.repositories.SubjectRepositoryInterface;
import com.example.school.domain.repositories.UserRepositoryInterface;
import com.example.school.domain.services.AbsenceServiceInterface;
import com.example.school.presenation.validators.AbsenceRequestValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AbsenceService implements AbsenceServiceInterface {

    private final AbsenceRepositoryInterface absenceRepository;
    private final SchoolRepositoryInterface schoolRepository;
    private final ClassRoomRepositoryInterface classRoomRepository;
    private final SubjectRepositoryInterface subjectRepository;
    private final UserRepositoryInterface userRepository;
    private final AcademicYearRepositoryInterface academicYearRepository;

    @Override
    @Transactional
    public List<AbsenceDto> createAbsences(AbsenceRequestValidator request) {
        // Vérifier que l'école existe
        School school = schoolRepository.findById(request.getSchoolId())
                .orElseThrow(() -> new EntityNotFoundException("École non trouvée"));

        // Vérifier que la classe existe
        ClassRoom classRoom = classRoomRepository.findById(request.getClassRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Classe non trouvée"));

        // Vérifier que la matière existe
        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new EntityNotFoundException("Matière non trouvée"));

        // Récupérer l'année académique active de l'école
        AcademicYear academicYear = school.getAcademicYears()
                .stream()
                .filter(AcademicYear::isActive)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Aucune année académique active pour cette école"));

        // Créer une absence pour chaque élève
        List<Absence> absences = request.getAbsentStudentIds().stream()
                .map(studentId -> {
                    // Vérifier que l'élève existe
                    User student = userRepository.findById(studentId)
                            .orElseThrow(() -> new EntityNotFoundException("Élève non trouvé: " + studentId));

                    Absence absence = new Absence();
                    absence.setStudent(student);
                    absence.setClassRoom(classRoom);
                    absence.setSchool(school);
                    absence.setSubject(subject);
                    absence.setAcademicYear(academicYear);
                    absence.setScheduleId(request.getScheduleId());
                    absence.setDate(request.getDate());
                    absence.setNumberOfHours(request.getNumberOfHours());
                    absence.setNotes(request.getNotes());

                    return absence;
                })
                .collect(Collectors.toList());

        // Sauvegarder toutes les absences
        List<Absence> savedAbsences = absences.stream()
                .map(absenceRepository::save)
                .collect(Collectors.toList());

        return savedAbsences.stream()
                .map(AbsenceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AbsenceDto getAbsenceById(UUID id) {
        return absenceRepository.findById(id)
                .map(AbsenceMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Absence non trouvée"));
    }

    @Override
    public List<AbsenceDto> getAbsencesByStudent(UUID studentId) {
        return absenceRepository.findByStudentId(studentId)
                .stream()
                .map(AbsenceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AbsenceDto> getAbsencesByStudentAndDateRange(UUID studentId, LocalDate startDate, LocalDate endDate) {
        return absenceRepository.findByStudentIdAndDateBetween(studentId, startDate, endDate)
                .stream()
                .map(AbsenceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AbsenceDto> getAbsencesByClassRoom(UUID classRoomId) {
        return absenceRepository.findByClassRoomId(classRoomId)
                .stream()
                .map(AbsenceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AbsenceDto> getAbsencesByClassRoomAndDate(UUID classRoomId, LocalDate date) {
        return absenceRepository.findByClassRoomIdAndDate(classRoomId, date)
                .stream()
                .map(AbsenceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AbsenceDto> getAbsencesByClassRoomAndDateRange(UUID classRoomId, LocalDate startDate, LocalDate endDate) {
        return absenceRepository.findByClassRoomIdAndDateBetween(classRoomId, startDate, endDate)
                .stream()
                .map(AbsenceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AbsenceDto> getAbsencesByStudentAndClassRoomAndDateRange(UUID studentId, UUID classRoomId, LocalDate startDate, LocalDate endDate) {
        return absenceRepository.findByStudentIdAndClassRoomIdAndDateBetween(studentId, classRoomId, startDate, endDate)
                .stream()
                .map(AbsenceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AbsenceDto> getAbsencesByClassRoomAndSubjectAndDate(UUID classRoomId, UUID subjectId, LocalDate date) {
        return absenceRepository.findByClassRoomIdAndSubjectIdAndDate(classRoomId, subjectId, date)
                .stream()
                .map(AbsenceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AbsenceDto> getAbsencesByStudentAndAcademicYear(UUID studentId, UUID academicYearId) {
        return absenceRepository.findByStudentIdAndAcademicYearId(studentId, academicYearId)
                .stream()
                .map(AbsenceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AbsenceDto> getAbsencesByClassRoomAndAcademicYear(UUID classRoomId, UUID academicYearId) {
        return absenceRepository.findByClassRoomIdAndAcademicYearId(classRoomId, academicYearId)
                .stream()
                .map(AbsenceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Double getTotalAbsenceHoursByStudentAndDateRange(UUID studentId, LocalDate startDate, LocalDate endDate) {
        return absenceRepository.sumHoursByStudentAndDateRange(studentId, startDate, endDate);
    }

    @Override
    @Transactional
    public void deleteAbsence(UUID id) {
        if (!absenceRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException("Absence non trouvée");
        }
        absenceRepository.deleteById(id);
    }
}
