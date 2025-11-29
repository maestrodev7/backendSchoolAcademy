package com.example.school.application.services;

import com.example.school.common.dto.ScheduleDto;
import com.example.school.common.exceptions.BusinessRuleException;
import com.example.school.common.mapper.ScheduleMapper;
import com.example.school.common.mapper.TeacherSubjectMapper;
import com.example.school.domain.entities.AcademicYear;
import com.example.school.domain.entities.Schedule;
import com.example.school.domain.entities.School;
import com.example.school.domain.repositories.*;
import com.example.school.domain.services.ScheduleServiceInterface;
import com.example.school.presenation.validators.ScheduleRequestValidator;
import com.example.school.presenation.validators.UpdateScheduleValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService implements ScheduleServiceInterface {

    private final ScheduleRepositoryInterface scheduleRepository;
    private final ClassRoomRepositoryInterface classRoomRepository;
    private final ClassRoomSubjectRepositoryInterface classRoomSubjectRepository;
    private final TeacherSubjectRepositoryInterface teacherSubjectRepository;
    private final SchoolRepositoryInterface schoolRepository;
    private final SubjectRepositoryInterface subjectRepository;
    private final UserSchoolRepositoryInterface userSchoolRepository;
    private final UserRepositoryInterface userRepository;

    @Override
    @Transactional
    public ScheduleDto create(ScheduleRequestValidator request) {
        // Validation des conflits
        if (hasConflicts(request)) {
            throw new BusinessRuleException("Conflit d'horaires détecté. Un créneau existe déjà à cet horaire.");
        }

        // Vérifier que la classe existe
        classRoomRepository.findById(request.getClassRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Classe non trouvée"));

        // Récupérer ou trouver l'association classe-matière
        UUID classRoomSubjectId = getOrFindClassRoomSubjectId(request);
        
        // Vérifier que l'association classe-matière existe
        classRoomSubjectRepository.findById(classRoomSubjectId)
                .orElseThrow(() -> new EntityNotFoundException("Association classe-matière non trouvée"));

        // Vérifier que l'association enseignant-matière existe
        teacherSubjectRepository.findById(request.getTeacherSubjectId())
                .orElseThrow(() -> new EntityNotFoundException("Association enseignant-matière non trouvée"));

        // Vérifier que l'école existe et récupérer l'année académique active
        School school = schoolRepository.findById(request.getSchoolId())
                .orElseThrow(() -> new EntityNotFoundException("École non trouvée"));
        
        // Récupérer l'année académique active de l'école
        AcademicYear activeYear = school.getAcademicYears()
                .stream()
                .filter(AcademicYear::isActive)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Aucune année académique active pour cette école"));

        // Vérifier que l'heure de fin est après l'heure de début
        if (request.getEndTime().isBefore(request.getStartTime()) || 
            request.getEndTime().equals(request.getStartTime())) {
            throw new BusinessRuleException("L'heure de fin doit être après l'heure de début");
        }

        Schedule schedule = new Schedule();
        schedule.setClassRoomId(request.getClassRoomId());
        schedule.setClassRoomSubjectId(classRoomSubjectId);
        schedule.setTeacherSubjectId(request.getTeacherSubjectId());
        schedule.setSchoolId(request.getSchoolId());
        schedule.setAcademicYearId(activeYear.getId());
        schedule.setDayOfWeek(request.getDayOfWeek());
        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());
        schedule.setRoom(request.getRoom());
        schedule.setNotes(request.getNotes());

        Schedule saved = scheduleRepository.save(schedule);
        return ScheduleMapper.toDto(saved, classRoomRepository, classRoomSubjectRepository,
                teacherSubjectRepository, schoolRepository, new TeacherSubjectMapper(), subjectRepository, userSchoolRepository, userRepository);
    }

    @Override
    public ScheduleDto getById(UUID id) {
        return scheduleRepository.findById(id)
                .map(s -> ScheduleMapper.toDto(s, classRoomRepository, classRoomSubjectRepository,
                        teacherSubjectRepository, schoolRepository, new TeacherSubjectMapper(), subjectRepository, userSchoolRepository, userRepository))
                .orElseThrow(() -> new EntityNotFoundException("Créneau d'emploi du temps non trouvé"));
    }

    @Override
    public List<ScheduleDto> getAll() {
        return scheduleRepository.findAll()
                .stream()
                .map(s -> ScheduleMapper.toDto(s, classRoomRepository, classRoomSubjectRepository,
                        teacherSubjectRepository, schoolRepository, new TeacherSubjectMapper(), subjectRepository, userSchoolRepository, userRepository))
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDto> getByClassRoom(UUID classRoomId) {
        return scheduleRepository.findByClassRoomId(classRoomId)
                .stream()
                .map(s -> ScheduleMapper.toDto(s, classRoomRepository, classRoomSubjectRepository,
                        teacherSubjectRepository, schoolRepository, new TeacherSubjectMapper(), subjectRepository, userSchoolRepository, userRepository))
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDto> getByTeacher(UUID teacherSubjectId) {
        return scheduleRepository.findByTeacherSubjectId(teacherSubjectId)
                .stream()
                .map(s -> ScheduleMapper.toDto(s, classRoomRepository, classRoomSubjectRepository,
                        teacherSubjectRepository, schoolRepository, new TeacherSubjectMapper(), subjectRepository, userSchoolRepository, userRepository))
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDto> getBySchool(UUID schoolId) {
        return scheduleRepository.findBySchoolId(schoolId)
                .stream()
                .map(s -> ScheduleMapper.toDto(s, classRoomRepository, classRoomSubjectRepository,
                        teacherSubjectRepository, schoolRepository, new TeacherSubjectMapper(), subjectRepository, userSchoolRepository, userRepository))
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDto> getByClassRoomAndDay(UUID classRoomId, DayOfWeek dayOfWeek) {
        return scheduleRepository.findByClassRoomIdAndDayOfWeek(classRoomId, dayOfWeek)
                .stream()
                .map(s -> ScheduleMapper.toDto(s, classRoomRepository, classRoomSubjectRepository,
                        teacherSubjectRepository, schoolRepository, new TeacherSubjectMapper(), subjectRepository, userSchoolRepository, userRepository))
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDto> getByTeacherAndDay(UUID teacherSubjectId, DayOfWeek dayOfWeek) {
        return scheduleRepository.findByTeacherSubjectIdAndDayOfWeek(teacherSubjectId, dayOfWeek)
                .stream()
                .map(s -> ScheduleMapper.toDto(s, classRoomRepository, classRoomSubjectRepository,
                        teacherSubjectRepository, schoolRepository, new TeacherSubjectMapper(), subjectRepository, userSchoolRepository, userRepository))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ScheduleDto update(UUID id, UpdateScheduleValidator request) {
        Schedule existing = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Créneau d'emploi du temps non trouvé"));

        // Vérifier que l'heure de fin est après l'heure de début
        if (request.getEndTime().isBefore(request.getStartTime()) || 
            request.getEndTime().equals(request.getStartTime())) {
            throw new BusinessRuleException("L'heure de fin doit être après l'heure de début");
        }

        existing.setDayOfWeek(request.getDayOfWeek());
        existing.setStartTime(request.getStartTime());
        existing.setEndTime(request.getEndTime());
        existing.setRoom(request.getRoom());
        existing.setNotes(request.getNotes());

        Schedule updated = scheduleRepository.save(existing);
        return ScheduleMapper.toDto(updated, classRoomRepository, classRoomSubjectRepository,
                teacherSubjectRepository, schoolRepository, new TeacherSubjectMapper(), subjectRepository, userSchoolRepository, userRepository);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!scheduleRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException("Créneau d'emploi du temps non trouvé");
        }
        scheduleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByClassRoom(UUID classRoomId) {
        scheduleRepository.deleteByClassRoomId(classRoomId);
    }

    @Override
    public boolean hasConflicts(ScheduleRequestValidator request) {
        // Vérifier les conflits pour la classe
        List<Schedule> classConflicts = scheduleRepository.findConflictingSchedulesForClassRoom(
                request.getClassRoomId(), request.getDayOfWeek(),
                request.getStartTime(), request.getEndTime());
        
        if (!classConflicts.isEmpty()) {
            return true;
        }

        // Vérifier les conflits pour l'enseignant
        List<Schedule> teacherConflicts = scheduleRepository.findConflictingSchedulesForTeacher(
                request.getTeacherSubjectId(), request.getDayOfWeek(),
                request.getStartTime(), request.getEndTime());
        
        return !teacherConflicts.isEmpty();
    }

    /**
     * Récupère ou trouve l'ID de l'association classe-matière à partir du classRoomSubjectId ou classRoomId+subjectId
     * Améliore l'UX en permettant de passer directement classRoomId et subjectId
     */
    private UUID getOrFindClassRoomSubjectId(ScheduleRequestValidator request) {
        // Si classRoomSubjectId est fourni, l'utiliser directement
        if (request.getClassRoomSubjectId() != null) {
            return request.getClassRoomSubjectId();
        }
        // Si subjectId est fourni, chercher l'association classe-matière correspondante
        else if (request.getSubjectId() != null) {
            return classRoomSubjectRepository.findByClassRoomAndSubject(request.getClassRoomId(), request.getSubjectId())
                    .map(com.example.school.domain.entities.ClassRoomSubject::getId)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Aucune association classe-matière trouvée pour la classe " + request.getClassRoomId() + 
                            " et la matière " + request.getSubjectId() + ". Veuillez d'abord associer la matière à la classe."));
        }
        // Ni classRoomSubjectId ni subjectId fournis
        else {
            throw new BusinessRuleException("Vous devez fournir soit classRoomSubjectId soit subjectId");
        }
    }
}

