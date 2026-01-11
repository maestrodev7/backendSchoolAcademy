package com.example.school.application.services;

import com.example.school.common.dto.CompetenceGradeDto;
import com.example.school.common.dto.DisciplineRecordDto;
import com.example.school.common.dto.GradeDto;
import com.example.school.common.dto.ReportCardDto;
import com.example.school.common.dto.StudentInfoDto;
import com.example.school.common.dto.SubjectGradesDto;
import com.example.school.domain.entities.AcademicYear;
import com.example.school.domain.entities.ClassRoom;
import com.example.school.domain.entities.School;
import com.example.school.domain.entities.StudentRegistration;
import com.example.school.domain.entities.Term;
import com.example.school.domain.entities.Sequence;
import com.example.school.domain.entities.ClassRoomSubject;
import com.example.school.domain.repositories.AcademicYearRepositoryInterface;
import com.example.school.domain.repositories.ClassRoomRepositoryInterface;
import com.example.school.domain.repositories.ClassRoomSubjectRepositoryInterface;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportCardService implements ReportCardServiceInterface {

    private final StudentInfoServiceInterface studentInfoService;
    private final GradeServiceInterface gradeService;
    private final DisciplineRecordServiceInterface disciplineRecordService;
    private final StudentRegistrationRepositoryInterface studentRegistrationRepository;
    private final ClassRoomRepositoryInterface classRoomRepository;
    private final ClassRoomSubjectRepositoryInterface classRoomSubjectRepository;
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

        // Organiser les notes par matière
        List<SubjectGradesDto> subjectGrades = organizeGradesBySubject(grades, classRoomId);

        // Calculer les statistiques générales
        BigDecimal totalGeneral = calculateTotalGeneral(subjectGrades);
        BigDecimal totalCoefficient = calculateTotalCoefficientFromSubjects(subjectGrades);
        BigDecimal averageTrim = calculateAverageTrim(subjectGrades);
        BigDecimal cote = averageTrim; // COTE = moyenne générale
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
        
        // Notes organisées par matière et statistiques
        reportCard.setSubjectGrades(subjectGrades);
        reportCard.setTotalGeneral(totalGeneral);
        reportCard.setTotalCoefficient(totalCoefficient);
        reportCard.setAverageTrim(averageTrim);
        reportCard.setCote(cote);
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

        // Organiser les notes par matière
        List<SubjectGradesDto> subjectGrades = organizeGradesBySubject(grades, classRoomId);

        // Calculer les statistiques générales
        BigDecimal totalGeneral = calculateTotalGeneral(subjectGrades);
        BigDecimal totalCoefficient = calculateTotalCoefficientFromSubjects(subjectGrades);
        BigDecimal averageTrim = calculateAverageTrim(subjectGrades);
        BigDecimal cote = averageTrim; // COTE = moyenne générale
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
        
        // Notes organisées par matière et statistiques
        reportCard.setSubjectGrades(subjectGrades);
        reportCard.setTotalGeneral(totalGeneral);
        reportCard.setTotalCoefficient(totalCoefficient);
        reportCard.setAverageTrim(averageTrim);
        reportCard.setCote(cote);
        reportCard.setTotalGrades(totalGrades);
        
        // Record disciplinaire
        reportCard.setDisciplineRecord(disciplineRecord);
        
        // Absences
        reportCard.setTotalAbsenceHours(totalAbsenceHours);
        
        // Date de génération
        reportCard.setGeneratedDate(LocalDate.now());
        
        return reportCard;
    }

    /**
     * Organise les notes par matière avec leurs compétences
     */
    private List<SubjectGradesDto> organizeGradesBySubject(List<GradeDto> grades, UUID classRoomId) {
        if (grades == null || grades.isEmpty()) {
            return List.of();
        }

        // Récupérer les coefficients des matières pour cette classe
        List<ClassRoomSubject> classRoomSubjects = classRoomSubjectRepository.findByClassRoomId(classRoomId);
        Map<UUID, Double> subjectCoefficientMap = classRoomSubjects.stream()
                .collect(Collectors.toMap(
                        crs -> crs.getSubject().getId(),
                        ClassRoomSubject::getCoefficient,
                        (v1, v2) -> v1
                ));

        // Grouper les notes par matière
        Map<UUID, List<GradeDto>> gradesBySubject = grades.stream()
                .collect(Collectors.groupingBy(GradeDto::getSubjectId, LinkedHashMap::new, Collectors.toList()));

        List<SubjectGradesDto> subjectGradesList = new ArrayList<>();

        for (Map.Entry<UUID, List<GradeDto>> entry : gradesBySubject.entrySet()) {
            UUID subjectId = entry.getKey();
            List<GradeDto> subjectGrades = entry.getValue();

            if (subjectGrades.isEmpty()) {
                continue;
            }

            // Prendre les infos de la matière depuis la première note
            GradeDto firstGrade = subjectGrades.get(0);
            
            SubjectGradesDto subjectGradesDto = new SubjectGradesDto();
            subjectGradesDto.setSubjectId(subjectId);
            subjectGradesDto.setSubjectName(firstGrade.getSubjectName());
            subjectGradesDto.setSubjectCode(null); // Pas disponible dans GradeDto, peut être ajouté si nécessaire
            
            // Récupérer le coefficient de la matière pour cette classe
            Double subjectCoeff = subjectCoefficientMap.getOrDefault(subjectId, 1.0);
            subjectGradesDto.setSubjectCoefficient(BigDecimal.valueOf(subjectCoeff));
            
            // Nom de l'enseignant (prendre le premier, supposant qu'il y a un seul enseignant par matière)
            subjectGradesDto.setTeacherName(firstGrade.getTeacherName());

            // Organiser les compétences avec leurs notes
            List<CompetenceGradeDto> competenceGrades = subjectGrades.stream()
                    .sorted(Comparator.comparing(g -> g.getCompetenceDescription(), Comparator.nullsLast(Comparator.naturalOrder())))
                    .map(grade -> {
                        CompetenceGradeDto compGrade = new CompetenceGradeDto();
                        compGrade.setGradeId(grade.getId());
                        compGrade.setCompetenceId(grade.getCompetenceId());
                        compGrade.setCompetenceDescription(grade.getCompetenceDescription());
                        compGrade.setCompetenceOrderNumber(null); // Pas disponible dans GradeDto
                        compGrade.setNoteN20(grade.getNoteN20());
                        compGrade.setNoteM20(grade.getNoteM20());
                        compGrade.setCoefficient(grade.getCoefficient());
                        compGrade.setMXCoef(grade.getMXCoef());
                        compGrade.setCote(grade.getCote());
                        compGrade.setMinScore(grade.getMinScore());
                        compGrade.setMaxScore(grade.getMaxScore());
                        compGrade.setAppreciation(grade.getAppreciation());
                        compGrade.setTeacherName(grade.getTeacherName());
                        return compGrade;
                    })
                    .collect(Collectors.toList());
            
            subjectGradesDto.setCompetences(competenceGrades);

            // Calculer les statistiques de la matière
            BigDecimal totalScore = subjectGrades.stream()
                    .filter(g -> g.getNoteM20() != null)
                    .map(GradeDto::getNoteM20)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            subjectGradesDto.setTotalScore(totalScore);

            BigDecimal avgScore = competenceGrades.size() > 0 && totalScore.compareTo(BigDecimal.ZERO) > 0
                    ? totalScore.divide(BigDecimal.valueOf(competenceGrades.size()), 2, RoundingMode.HALF_UP)
                    : BigDecimal.ZERO;
            subjectGradesDto.setAverageScore(avgScore);

            BigDecimal totalCoeff = competenceGrades.stream()
                    .filter(cg -> cg.getCoefficient() != null)
                    .map(CompetenceGradeDto::getCoefficient)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            subjectGradesDto.setTotalCoefficient(totalCoeff);

            // Moyenne pondérée : Σ(M x coef) / Σ(coef)
            BigDecimal totalMXCoef = competenceGrades.stream()
                    .filter(cg -> cg.getMXCoef() != null)
                    .map(CompetenceGradeDto::getMXCoef)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal weightedAvg = totalCoeff.compareTo(BigDecimal.ZERO) > 0
                    ? totalMXCoef.divide(totalCoeff, 2, RoundingMode.HALF_UP)
                    : BigDecimal.ZERO;
            subjectGradesDto.setWeightedAverage(weightedAvg);
            subjectGradesDto.setCote(weightedAvg); // COTE = moyenne pondérée

            // Min et Max de la classe (prendre depuis les notes, si disponible)
            BigDecimal minScore = subjectGrades.stream()
                    .filter(g -> g.getMinScore() != null)
                    .map(GradeDto::getMinScore)
                    .min(BigDecimal::compareTo)
                    .orElse(BigDecimal.ZERO);
            BigDecimal maxScore = subjectGrades.stream()
                    .filter(g -> g.getMaxScore() != null)
                    .map(GradeDto::getMaxScore)
                    .max(BigDecimal::compareTo)
                    .orElse(BigDecimal.ZERO);
            subjectGradesDto.setMinScore(minScore);
            subjectGradesDto.setMaxScore(maxScore);

            subjectGradesList.add(subjectGradesDto);
        }

        return subjectGradesList;
    }

    private BigDecimal calculateTotalGeneral(List<SubjectGradesDto> subjectGrades) {
        return subjectGrades.stream()
                .filter(sg -> sg.getWeightedAverage() != null && sg.getSubjectCoefficient() != null)
                .map(sg -> sg.getWeightedAverage().multiply(sg.getSubjectCoefficient()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTotalCoefficientFromSubjects(List<SubjectGradesDto> subjectGrades) {
        return subjectGrades.stream()
                .filter(sg -> sg.getSubjectCoefficient() != null)
                .map(SubjectGradesDto::getSubjectCoefficient)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateAverageTrim(List<SubjectGradesDto> subjectGrades) {
        BigDecimal totalGeneral = calculateTotalGeneral(subjectGrades);
        BigDecimal totalCoefficient = calculateTotalCoefficientFromSubjects(subjectGrades);
        
        if (totalCoefficient.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        
        return totalGeneral.divide(totalCoefficient, 2, RoundingMode.HALF_UP);
    }
}
