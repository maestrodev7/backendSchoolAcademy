package com.example.school.application.services;

import com.example.school.domain.entities.*;
import com.example.school.domain.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service chargé d'initialiser automatiquement
 * les niveaux, séries, classes et matières d'une école
 * selon le programme général de l'enseignement secondaire au Cameroun.
 */
@Service
@RequiredArgsConstructor
public class SchoolInitializationService {

    private final ClassLevelRepositoryInterface classLevelRepository;
    private final SeriesRepositoryInterface seriesRepository;
    private final ClassRoomRepositoryInterface classRoomRepository;
    private final SubjectRepositoryInterface subjectRepository;
    private final ClassRoomSubjectRepositoryInterface classRoomSubjectRepository;
    private final SchoolRepositoryInterface schoolRepository;
    private final AcademicYearRepositoryInterface academicYearRepository;

    /**
     * Initialise la structure par défaut pour une école donnée
     * sur une année académique donnée.
     *
     * Cette méthode est idempotente : si certains éléments existent déjà,
     * ils ne seront pas recréés en doublon.
     */
    public void initializeDefaultStructure(UUID schoolId, UUID academicYearId) {
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new EntityNotFoundException("École non trouvée"));

        AcademicYear academicYear = academicYearRepository.findById(academicYearId)
                .orElseThrow(() -> new EntityNotFoundException("Année académique non trouvée"));

        // 1) Créer les niveaux si besoin
        Map<String, ClassLevel> levels = ensureDefaultLevels();

        // 2) Créer les séries si besoin
        Map<String, Series> series = ensureDefaultSeries();

        // 3) Créer les matières si besoin
        Map<String, Subject> subjects = ensureDefaultSubjects();

        // 4) Créer les classes (ClassRoom) pour cette école / année
        Map<String, ClassRoom> classes = ensureDefaultClassesForSchoolYear(
                school,
                academicYear,
                levels,
                series
        );

        // 5) Associer les matières aux classes
        ensureDefaultSubjectsPerClass(classes, subjects);
    }

    private Map<String, ClassLevel> ensureDefaultLevels() {
        // On indexe par nom pour éviter les doublons
        Map<String, ClassLevel> existing = classLevelRepository.findAll()
                .stream()
                .collect(Collectors.toMap(ClassLevel::getName, l -> l, (a, b) -> a));

        List<String> levelNames = Arrays.asList(
                "Sixième",
                "Cinquième",
                "Quatrième",
                "Troisième",
                "Seconde",
                "Première",
                "Terminale"
        );

        for (String name : levelNames) {
            if (!existing.containsKey(name)) {
                ClassLevel level = new ClassLevel();
                level.setName(name);
                ClassLevel saved = classLevelRepository.save(level);
                existing.put(name, saved);
            }
        }
        return existing;
    }

    private Map<String, Series> ensureDefaultSeries() {
        Map<String, Series> existing = seriesRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Series::getCode, s -> s, (a, b) -> a));

        // Séries / filières demandées
        ensureSeries(existing, "GEN", "Général (6ème/5ème sans série)");
        ensureSeries(existing, "C", "Série C");
        ensureSeries(existing, "D", "Série D");
        ensureSeries(existing, "TI", "Série TI");
        ensureSeries(existing, "A4ALL", "A4 Allemand");
        ensureSeries(existing, "A4ESP", "A4 Espagnol");
        ensureSeries(existing, "ALL", "Allemand");
        ensureSeries(existing, "ESP", "Espagnol");

        return existing;
    }

    private void ensureSeries(Map<String, Series> existing, String code, String name) {
        if (!existing.containsKey(code)) {
            Series s = new Series();
            s.setCode(code);
            s.setName(name);
            Series saved = seriesRepository.save(s);
            existing.put(code, saved);
        }
    }

    private Map<String, Subject> ensureDefaultSubjects() {
        Map<String, Subject> existing = subjectRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Subject::getCode, s -> s, (a, b) -> a));

        // Matières de base collège (6ème-3ème)
        ensureSubject(existing, "FR", "Français", "Analyse et expression écrite et orale");
        ensureSubject(existing, "MATH", "Mathématiques", "Arithmétique, géométrie, algèbre");
        ensureSubject(existing, "HG", "Histoire-Géographie", "Étude du passé et des espaces géographiques");
        ensureSubject(existing, "EMC", "Enseignement moral et civique", "Formation du citoyen");
        ensureSubject(existing, "SVT", "Sciences de la vie et de la Terre", null);
        ensureSubject(existing, "PCT", "Physique-Chimie-Technologie", null);
        ensureSubject(existing, "TECHNO", "Technologie", "Initiation aux techniques et outils technologiques");
        ensureSubject(existing, "ANG", "Anglais", "Première langue vivante");
        ensureSubject(existing, "INFO", "Informatique", null);
        ensureSubject(existing, "EPS", "Éducation physique et sportive", "Développement physique et habitudes sportives");

        // Langues supplémentaires à partir de la 4ème
        ensureSubject(existing, "ALL", "Allemand", "Langue vivante 2");
        ensureSubject(existing, "ESP", "Espagnol", "Langue vivante 2");

        // À partir de la seconde : PCT séparé
        ensureSubject(existing, "PHYS", "Physique", null);
        ensureSubject(existing, "CHIM", "Chimie", null);

        return existing;
    }

    private void ensureSubject(Map<String, Subject> existing, String code, String name, String description) {
        if (!existing.containsKey(code)) {
            Subject s = new Subject();
            s.setCode(code);
            s.setName(name);
            s.setDescription(description);
            Subject saved = subjectRepository.save(s);
            existing.put(code, saved);
        }
    }

    /**
     * Crée les classes pour l'école & l'année spécifiées.
     * On indexe les classes par un identifiant logique "NIVEAU|SERIE"
     * par exemple "Sixième|GEN", "Seconde|C", "Première|A4ALL", etc.
     */
    private Map<String, ClassRoom> ensureDefaultClassesForSchoolYear(
            School school,
            AcademicYear academicYear,
            Map<String, ClassLevel> levels,
            Map<String, Series> series
    ) {
        List<ClassRoom> existingForYear = classRoomRepository.findByAcademicYear(academicYear.getId())
                .stream()
                .filter(cr -> cr.getSchool() != null && school.getId().equals(cr.getSchool().getId()))
                .collect(Collectors.toList());

        Map<String, ClassRoom> result = new HashMap<>();
        for (ClassRoom cr : existingForYear) {
            String key = buildClassKey(
                    cr.getClassLevel() != null ? cr.getClassLevel().getName() : null,
                    cr.getSeries() != null ? cr.getSeries().getCode() : null
            );
            if (key != null) {
                result.putIfAbsent(key, cr);
            }
        }

        // 6ème & 5ème : sans série (général)
        createIfAbsent(result, "Sixième", "GEN", school, academicYear, levels, series);
        createIfAbsent(result, "Cinquième", "GEN", school, academicYear, levels, series);

        // 4ème : Allemand & Espagnol
        createIfAbsent(result, "Quatrième", "ALL", school, academicYear, levels, series);
        createIfAbsent(result, "Quatrième", "ESP", school, academicYear, levels, series);

        // 3ème : Allemand & Espagnol (même logique que 4ème)
        createIfAbsent(result, "Troisième", "ALL", school, academicYear, levels, series);
        createIfAbsent(result, "Troisième", "ESP", school, academicYear, levels, series);

        // Seconde : C, A4 Allemand, A4 Espagnol
        createIfAbsent(result, "Seconde", "C", school, academicYear, levels, series);
        createIfAbsent(result, "Seconde", "A4ALL", school, academicYear, levels, series);
        createIfAbsent(result, "Seconde", "A4ESP", school, academicYear, levels, series);

        // Première : C, D, TI, A4 Allemand, A4 Espagnol
        for (String serieCode : Arrays.asList("C", "D", "TI", "A4ALL", "A4ESP")) {
            createIfAbsent(result, "Première", serieCode, school, academicYear, levels, series);
        }

        // Terminale : C, D, TI, A4 Allemand, A4 Espagnol
        for (String serieCode : Arrays.asList("C", "D", "TI", "A4ALL", "A4ESP")) {
            createIfAbsent(result, "Terminale", serieCode, school, academicYear, levels, series);
        }

        return result;
    }

    private void createIfAbsent(
            Map<String, ClassRoom> result,
            String levelName,
            String seriesCode,
            School school,
            AcademicYear academicYear,
            Map<String, ClassLevel> levels,
            Map<String, Series> series
    ) {
        String key = buildClassKey(levelName, seriesCode);
        if (key == null || result.containsKey(key)) {
            return;
        }

        ClassLevel level = levels.get(levelName);
        Series serie = series.get(seriesCode);
        if (level == null || serie == null) {
            return;
        }

        ClassRoom cr = new ClassRoom();
        cr.setLabel(levelName + " " + serie.getCode());
        cr.setSchool(school);
        cr.setAcademicYear(academicYear);
        cr.setClassLevel(level);
        cr.setSeries(serie);

        ClassRoom saved = classRoomRepository.save(cr);
        result.put(key, saved);
    }

    private String buildClassKey(String levelName, String seriesCode) {
        if (levelName == null || seriesCode == null) return null;
        return levelName + "|" + seriesCode;
        }

    /**
     * Associe les matières aux classes selon le niveau/série.
     * Pour simplifier, tous les coefficients sont mis à 1.0 par défaut.
     * L'utilisateur pourra les modifier par la suite.
     */
    private void ensureDefaultSubjectsPerClass(
            Map<String, ClassRoom> classes,
            Map<String, Subject> subjects
    ) {
        for (Map.Entry<String, ClassRoom> entry : classes.entrySet()) {
            String key = entry.getKey();
            ClassRoom classRoom = entry.getValue();

            String[] parts = key.split("\\|");
            if (parts.length != 2) continue;

            String levelName = parts[0];

            // Liste des codes matières pour cette classe
            List<String> subjectCodesForClass = getSubjectCodesForLevel(levelName);

            for (String subjectCode : subjectCodesForClass) {
                Subject s = subjects.get(subjectCode);
                if (s == null) continue;

                // Éviter les doublons
                if (classRoomSubjectRepository
                        .findByClassRoomAndSubject(classRoom.getId(), s.getId())
                        .isPresent()) {
                    continue;
                }

                ClassRoomSubject crs = new ClassRoomSubject();
                crs.setClassRoom(classRoom);
                crs.setSubject(s);
                crs.setCoefficient(1.0); // valeur par défaut

                classRoomSubjectRepository.save(crs);
            }
        }
    }

    private List<String> getSubjectCodesForLevel(String levelName) {
        // 6ème & 5ème : matières de base
        if ("Sixième".equalsIgnoreCase(levelName) || "Cinquième".equalsIgnoreCase(levelName)) {
            return Arrays.asList("FR", "MATH", "HG", "EMC", "SVT", "PCT", "TECHNO", "ANG", "INFO", "EPS");
        }

        // 4ème & 3ème : mêmes matières + Allemand & Espagnol
        if ("Quatrième".equalsIgnoreCase(levelName) || "Troisième".equalsIgnoreCase(levelName)) {
            return Arrays.asList("FR", "MATH", "HG", "EMC", "SVT", "PCT", "TECHNO", "ANG", "INFO", "EPS", "ALL", "ESP");
        }

        // À partir de la Seconde : PCT séparé en Physique + Chimie
        if ("Seconde".equalsIgnoreCase(levelName)
                || "Première".equalsIgnoreCase(levelName)
                || "Terminale".equalsIgnoreCase(levelName)) {
            return Arrays.asList("FR", "MATH", "HG", "EMC", "SVT", "PHYS", "CHIM", "TECHNO", "ANG", "INFO", "EPS", "ALL", "ESP");
        }

        return Collections.emptyList();
    }
}


