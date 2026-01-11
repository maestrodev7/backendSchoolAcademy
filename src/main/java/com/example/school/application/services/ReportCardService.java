package com.example.school.application.services;

import com.example.school.common.dto.DisciplineRecordDto;
import com.example.school.common.dto.GradeDto;
import com.example.school.common.dto.ReportCardDto;
import com.example.school.common.dto.StudentInfoDto;
import com.example.school.domain.entities.AcademicYear;
import com.example.school.domain.entities.ClassRoom;
import com.example.school.domain.entities.School;
import com.example.school.domain.entities.StudentRegistration;
import com.example.school.domain.entities.Term;
import com.example.school.domain.entities.Sequence;
import com.example.school.domain.repositories.AcademicYearRepositoryInterface;
import com.example.school.domain.repositories.ClassRoomRepositoryInterface;
import com.example.school.domain.repositories.SchoolRepositoryInterface;
import com.example.school.domain.repositories.StudentRegistrationRepositoryInterface;
import com.example.school.domain.repositories.TermRepositoryInterface;
import com.example.school.domain.repositories.SequenceRepositoryInterface;
import com.example.school.domain.services.DisciplineRecordServiceInterface;
import com.example.school.domain.services.GradeServiceInterface;
import com.example.school.domain.services.ReportCardServiceInterface;
import com.example.school.domain.services.StudentInfoServiceInterface;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportCardService implements ReportCardServiceInterface {

    private final StudentInfoServiceInterface studentInfoService;
    private final GradeServiceInterface gradeService;
    private final DisciplineRecordServiceInterface disciplineRecordService;
    private final StudentRegistrationRepositoryInterface studentRegistrationRepository;
    private final ClassRoomRepositoryInterface classRoomRepository;
    private final SchoolRepositoryInterface schoolRepository;
    private final AcademicYearRepositoryInterface academicYearRepository;
    private final TermRepositoryInterface termRepository;
    private final SequenceRepositoryInterface sequenceRepository;

    @Override
    public ReportCardDto generateReportCardForTerm(UUID studentId, UUID classRoomId, UUID academicYearId, UUID termId) {
        // Vérifier que l'inscription existe
        StudentRegistration registration = studentRegistrationRepository
                .findByStudentAndAcademicYear(studentId, academicYearId)
                .orElseThrow(() -> new EntityNotFoundException("Inscription non trouvée pour cet élève et cette année académique"));

        // Vérifier que la classe correspond
        if (!registration.getClassRoom().getId().equals(classRoomId)) {
            throw new EntityNotFoundException("L'élève n'est pas inscrit dans cette classe pour cette année académique");
        }

        // Récupérer les informations de base
        StudentInfoDto studentInfo = studentInfoService.getStudentInfoByStudentId(studentId);
        ClassRoom classRoom = classRoomRepository.findById(classRoomId)
                .orElseThrow(() -> new EntityNotFoundException("Classe non trouvée"));
        School school = schoolRepository.findById(classRoom.getSchool().getId())
                .orElseThrow(() -> new EntityNotFoundException("École non trouvée"));
        AcademicYear academicYear = academicYearRepository.findById(academicYearId)
                .orElseThrow(() -> new EntityNotFoundException("Année académique non trouvée"));
        Term term = termRepository.findById(termId)
                .orElseThrow(() -> new EntityNotFoundException("Trimestre non trouvé"));

        // Récupérer les notes pour le trimestre
        List<GradeDto> grades = gradeService.getGradesByStudentIdAndTermId(studentId, termId);

        // Calculer les statistiques des notes
        BigDecimal averageScore = calculateAverageScore(grades);
        BigDecimal totalCoefficient = calculateTotalCoefficient(grades);
        Integer totalGrades = grades.size();

        // Récupérer le record disciplinaire pour le trimestre
        DisciplineRecordDto disciplineRecord = null;
        try {
            disciplineRecord = disciplineRecordService.getDisciplineRecordByStudentIdAndTermId(studentId, termId);
        } catch (EntityNotFoundException e) {
            // Pas de record disciplinaire pour ce trimestre, c'est ok
        }

        // Calculer le total d'heures d'absence pour la période du trimestre
        Double totalAbsenceHours = 0.0;
        if (term.getStartDate() != null && term.getEndDate() != null) {
            // TODO: Implémenter le calcul des absences pour la période
            // Pour l'instant, on laisse à 0
        }

        // Construire le DTO
        ReportCardDto reportCard = new ReportCardDto();
        
        // Informations de l'élève
        reportCard.setStudentId(studentInfo.getStudentId());
        reportCard.setStudentName(studentInfo.getStudentName());
        reportCard.setStudentUniqueIdentifier(studentInfo.getUniqueIdentifier());
        reportCard.setStudentBirthDate(studentInfo.getBirthDate());
        reportCard.setStudentBirthPlace(studentInfo.getBirthPlace());
        reportCard.setStudentGender(studentInfo.getGender());
        reportCard.setStudentIsRepeating(studentInfo.getIsRepeating());
        reportCard.setStudentPhotoUrl(studentInfo.getPhotoUrl());
        
        // Informations parentales
        reportCard.setParentNames(studentInfo.getParentNames());
        reportCard.setParentContacts(studentInfo.getParentContacts());
        
        // Informations de la classe et école
        reportCard.setClassRoomId(classRoom.getId());
        reportCard.setClassRoomLabel(classRoom.getLabel());
        reportCard.setSchoolId(school.getId());
        reportCard.setSchoolName(school.getName());
        reportCard.setSchoolAddress(school.getAddress());
        reportCard.setSchoolPhoneNumber(school.getPhoneNumber());
        reportCard.setSchoolEmail(school.getEmail());
        
        // Informations de l'année académique
        reportCard.setAcademicYearId(academicYear.getId());
        if (academicYear.getStartDate() != null && academicYear.getEndDate() != null) {
            reportCard.setAcademicYearLabel(academicYear.getStartDate() + " - " + academicYear.getEndDate());
        }
        
        // Informations du trimestre
        reportCard.setTermId(term.getId());
        reportCard.setTermName(term.getName());
        reportCard.setSequenceId(null);
        reportCard.setSequenceName(null);
        
        // Notes et statistiques
        reportCard.setGrades(grades);
        reportCard.setAverageScore(averageScore);
        reportCard.setTotalCoefficient(totalCoefficient);
        reportCard.setTotalGrades(totalGrades);
        
        // Record disciplinaire
        reportCard.setDisciplineRecord(disciplineRecord);
        
        // Absences
        reportCard.setTotalAbsenceHours(totalAbsenceHours);
        
        // Date de génération
        reportCard.setGeneratedDate(LocalDate.now());
        
        return reportCard;
    }

    @Override
    public ReportCardDto generateReportCardForSequence(UUID studentId, UUID classRoomId, UUID academicYearId, UUID sequenceId) {
        // Vérifier que l'inscription existe
        StudentRegistration registration = studentRegistrationRepository
                .findByStudentAndAcademicYear(studentId, academicYearId)
                .orElseThrow(() -> new EntityNotFoundException("Inscription non trouvée pour cet élève et cette année académique"));

        // Vérifier que la classe correspond
        if (!registration.getClassRoom().getId().equals(classRoomId)) {
            throw new EntityNotFoundException("L'élève n'est pas inscrit dans cette classe pour cette année académique");
        }

        // Récupérer les informations de base
        StudentInfoDto studentInfo = studentInfoService.getStudentInfoByStudentId(studentId);
        ClassRoom classRoom = classRoomRepository.findById(classRoomId)
                .orElseThrow(() -> new EntityNotFoundException("Classe non trouvée"));
        School school = schoolRepository.findById(classRoom.getSchool().getId())
                .orElseThrow(() -> new EntityNotFoundException("École non trouvée"));
        AcademicYear academicYear = academicYearRepository.findById(academicYearId)
                .orElseThrow(() -> new EntityNotFoundException("Année académique non trouvée"));
        Sequence sequence = sequenceRepository.findById(sequenceId)
                .orElseThrow(() -> new EntityNotFoundException("Séquence non trouvée"));

        // Récupérer le trimestre associé à la séquence pour le record disciplinaire
        Term term = null;
        if (sequence.getTermId() != null) {
            term = termRepository.findById(sequence.getTermId())
                    .orElse(null); // Si le term n'existe pas, on continue sans
        }

        // Récupérer les notes pour la séquence
        List<GradeDto> grades = gradeService.getGradesByStudentIdAndSequenceId(studentId, sequenceId);

        // Calculer les statistiques des notes
        BigDecimal averageScore = calculateAverageScore(grades);
        BigDecimal totalCoefficient = calculateTotalCoefficient(grades);
        Integer totalGrades = grades.size();

        // Récupérer le record disciplinaire pour le trimestre (pas de record par séquence)
        DisciplineRecordDto disciplineRecord = null;
        if (term != null) {
            try {
                disciplineRecord = disciplineRecordService.getDisciplineRecordByStudentIdAndTermId(studentId, term.getId());
            } catch (EntityNotFoundException e) {
                // Pas de record disciplinaire pour ce trimestre, c'est ok
            }
        }

        // Calculer le total d'heures d'absence pour la période de la séquence
        Double totalAbsenceHours = 0.0;
        if (sequence.getStartDate() != null && sequence.getEndDate() != null) {
            // TODO: Implémenter le calcul des absences pour la période
            // Pour l'instant, on laisse à 0
        }

        // Construire le DTO
        ReportCardDto reportCard = new ReportCardDto();
        
        // Informations de l'élève
        reportCard.setStudentId(studentInfo.getStudentId());
        reportCard.setStudentName(studentInfo.getStudentName());
        reportCard.setStudentUniqueIdentifier(studentInfo.getUniqueIdentifier());
        reportCard.setStudentBirthDate(studentInfo.getBirthDate());
        reportCard.setStudentBirthPlace(studentInfo.getBirthPlace());
        reportCard.setStudentGender(studentInfo.getGender());
        reportCard.setStudentIsRepeating(studentInfo.getIsRepeating());
        reportCard.setStudentPhotoUrl(studentInfo.getPhotoUrl());
        
        // Informations parentales
        reportCard.setParentNames(studentInfo.getParentNames());
        reportCard.setParentContacts(studentInfo.getParentContacts());
        
        // Informations de la classe et école
        reportCard.setClassRoomId(classRoom.getId());
        reportCard.setClassRoomLabel(classRoom.getLabel());
        reportCard.setSchoolId(school.getId());
        reportCard.setSchoolName(school.getName());
        reportCard.setSchoolAddress(school.getAddress());
        reportCard.setSchoolPhoneNumber(school.getPhoneNumber());
        reportCard.setSchoolEmail(school.getEmail());
        
        // Informations de l'année académique
        reportCard.setAcademicYearId(academicYear.getId());
        if (academicYear.getStartDate() != null && academicYear.getEndDate() != null) {
            reportCard.setAcademicYearLabel(academicYear.getStartDate() + " - " + academicYear.getEndDate());
        }
        
        // Informations de la séquence
        reportCard.setTermId(term != null ? term.getId() : null);
        reportCard.setTermName(term != null ? term.getName() : null);
        reportCard.setSequenceId(sequence.getId());
        reportCard.setSequenceName(sequence.getName());
        
        // Notes et statistiques
        reportCard.setGrades(grades);
        reportCard.setAverageScore(averageScore);
        reportCard.setTotalCoefficient(totalCoefficient);
        reportCard.setTotalGrades(totalGrades);
        
        // Record disciplinaire
        reportCard.setDisciplineRecord(disciplineRecord);
        
        // Absences
        reportCard.setTotalAbsenceHours(totalAbsenceHours);
        
        // Date de génération
        reportCard.setGeneratedDate(LocalDate.now());
        
        return reportCard;
    }

    private BigDecimal calculateAverageScore(List<GradeDto> grades) {
        if (grades == null || grades.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal totalWeightedScore = BigDecimal.ZERO;
        BigDecimal totalCoefficient = BigDecimal.ZERO;

        for (GradeDto grade : grades) {
            if (grade.getNoteM20() != null && grade.getCoefficient() != null) {
                BigDecimal weightedScore = grade.getNoteM20().multiply(grade.getCoefficient());
                totalWeightedScore = totalWeightedScore.add(weightedScore);
                totalCoefficient = totalCoefficient.add(grade.getCoefficient());
            }
        }

        if (totalCoefficient.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return totalWeightedScore.divide(totalCoefficient, 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateTotalCoefficient(List<GradeDto> grades) {
        if (grades == null || grades.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return grades.stream()
                .filter(grade -> grade.getCoefficient() != null)
                .map(GradeDto::getCoefficient)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
