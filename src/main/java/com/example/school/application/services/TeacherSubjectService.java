package com.example.school.application.services;

import com.example.school.common.dto.TeacherSubjectDto;
import com.example.school.common.exceptions.BusinessRuleException;
import com.example.school.common.exceptions.ResourceAlreadyExistsException;
import com.example.school.common.mapper.TeacherSubjectMapper;
import com.example.school.domain.entities.TeacherSubject;
import com.example.school.domain.entities.AcademicYear;
import com.example.school.domain.entities.School;
import com.example.school.domain.repositories.*;
import com.example.school.domain.services.TeacherSubjectServiceInterface;
import com.example.school.presenation.validators.TeacherSubjectRequestValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherSubjectService implements TeacherSubjectServiceInterface {

    private final TeacherSubjectRepositoryInterface teacherSubjectRepository;
    private final UserSchoolRepositoryInterface userSchoolRepository;
    private final SubjectRepositoryInterface subjectRepository;
    private final SchoolRepositoryInterface schoolRepository;
    private final UserRepositoryInterface userRepository;

    @Override
    @Transactional
    public TeacherSubjectDto create(TeacherSubjectRequestValidator request) {
        // Récupérer ou trouver l'association User-School
        var userSchool = getOrFindUserSchool(request);
        
        if (!"ENSEIGNANT".equalsIgnoreCase(userSchool.getRole())) {
            throw new BusinessRuleException("L'utilisateur doit avoir le rôle ENSEIGNANT");
        }

        // Vérifier que l'école correspond
        if (!request.getSchoolId().equals(userSchool.getSchoolId())) {
            throw new BusinessRuleException("L'école de l'association User-School ne correspond pas à l'école spécifiée");
        }

        // Vérifier que la matière existe
        subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new EntityNotFoundException("Matière non trouvée"));

        // Vérifier que l'école existe et récupérer l'année académique active
        School school = schoolRepository.findById(request.getSchoolId())
                .orElseThrow(() -> new EntityNotFoundException("École non trouvée"));
        
        // Récupérer l'année académique active de l'école
        AcademicYear activeYear = school.getAcademicYears()
                .stream()
                .filter(AcademicYear::isActive)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Aucune année académique active pour cette école"));

        // Utiliser le userSchoolId de l'association trouvée
        UUID finalUserSchoolId = userSchool.getId();
        
        // Vérifier si l'association existe déjà (pour la même année académique)
        var existing = teacherSubjectRepository.findByUserSchoolIdAndSubjectIdAndSchoolId(
                finalUserSchoolId, request.getSubjectId(), request.getSchoolId());

        TeacherSubject association;
        if (existing.isPresent()) {
            // Mettre à jour l'association existante
            association = existing.get();
            association.setSpecialization(request.getSpecialization());
            association.setExperienceYears(request.getExperienceYears());
            association.setAcademicYearId(activeYear.getId());
        } else {
            // Créer une nouvelle association
            association = new TeacherSubject();
            association.setUserSchoolId(finalUserSchoolId);
            association.setSubjectId(request.getSubjectId());
            association.setSchoolId(request.getSchoolId());
            association.setAcademicYearId(activeYear.getId());
            association.setSpecialization(request.getSpecialization());
            association.setExperienceYears(request.getExperienceYears());
        }

        TeacherSubject saved = teacherSubjectRepository.save(association);
        return TeacherSubjectMapper.toDto(saved, userSchoolRepository, subjectRepository, schoolRepository, userRepository);
    }

    /**
     * Récupère ou trouve l'association User-School à partir du userId ou userSchoolId
     * Améliore l'UX en permettant de passer directement userId
     */
    private com.example.school.domain.entities.UserSchool getOrFindUserSchool(TeacherSubjectRequestValidator request) {
        com.example.school.domain.entities.UserSchool userSchool;
        
        // Si userSchoolId est fourni, l'utiliser directement
        if (request.getUserSchoolId() != null) {
            userSchool = userSchoolRepository.findById(request.getUserSchoolId())
                    .orElseThrow(() -> new EntityNotFoundException("Association User-School non trouvée avec l'ID fourni: " + request.getUserSchoolId()));
        }
        // Si userId est fourni, chercher l'association User-School correspondante
        else if (request.getUserId() != null) {
            userSchool = userSchoolRepository.findByUserIdAndSchoolId(request.getUserId(), request.getSchoolId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Aucune association User-School trouvée pour l'utilisateur " + request.getUserId() + 
                            " et l'école " + request.getSchoolId() + ". Veuillez d'abord associer l'utilisateur à l'école."));
        }
        // Ni userId ni userSchoolId fournis
        else {
            throw new BusinessRuleException("Vous devez fournir soit userId soit userSchoolId");
        }
        
        return userSchool;
    }

    @Override
    public TeacherSubjectDto getById(UUID id) {
        return teacherSubjectRepository.findById(id)
                .map(ts -> TeacherSubjectMapper.toDto(ts, userSchoolRepository, subjectRepository, schoolRepository, userRepository))
                .orElseThrow(() -> new EntityNotFoundException("Association enseignant-matière non trouvée"));
    }

    @Override
    public List<TeacherSubjectDto> getAll() {
        return teacherSubjectRepository.findAll()
                .stream()
                .map(ts -> TeacherSubjectMapper.toDto(ts, userSchoolRepository, subjectRepository, schoolRepository, userRepository))
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherSubjectDto> getBySchool(UUID schoolId) {
        return teacherSubjectRepository.findBySchoolId(schoolId)
                .stream()
                .map(ts -> TeacherSubjectMapper.toDto(ts, userSchoolRepository, subjectRepository, schoolRepository, userRepository))
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherSubjectDto> getByTeacher(UUID userSchoolId) {
        return teacherSubjectRepository.findByUserSchoolId(userSchoolId)
                .stream()
                .map(ts -> TeacherSubjectMapper.toDto(ts, userSchoolRepository, subjectRepository, schoolRepository, userRepository))
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherSubjectDto> getBySubject(UUID subjectId) {
        return teacherSubjectRepository.findBySubjectId(subjectId)
                .stream()
                .map(ts -> TeacherSubjectMapper.toDto(ts, userSchoolRepository, subjectRepository, schoolRepository, userRepository))
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherSubjectDto> getByTeacherAndSchool(UUID userSchoolId, UUID schoolId) {
        return teacherSubjectRepository.findByUserSchoolIdAndSchoolId(userSchoolId, schoolId)
                .stream()
                .map(ts -> TeacherSubjectMapper.toDto(ts, userSchoolRepository, subjectRepository, schoolRepository, userRepository))
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherSubjectDto> getBySubjectAndSchool(UUID subjectId, UUID schoolId) {
        return teacherSubjectRepository.findBySubjectIdAndSchoolId(subjectId, schoolId)
                .stream()
                .map(ts -> TeacherSubjectMapper.toDto(ts, userSchoolRepository, subjectRepository, schoolRepository, userRepository))
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherSubjectDto> getByAcademicYear(UUID academicYearId) {
        return teacherSubjectRepository.findByAcademicYear(academicYearId)
                .stream()
                .map(ts -> TeacherSubjectMapper.toDto(ts, userSchoolRepository, subjectRepository, schoolRepository, userRepository))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TeacherSubjectDto update(UUID id, TeacherSubjectRequestValidator request) {
        TeacherSubject existing = teacherSubjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Association enseignant-matière non trouvée"));

        existing.setSpecialization(request.getSpecialization());
        existing.setExperienceYears(request.getExperienceYears());

        TeacherSubject updated = teacherSubjectRepository.save(existing);
        return TeacherSubjectMapper.toDto(updated, userSchoolRepository, subjectRepository, schoolRepository, userRepository);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!teacherSubjectRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException("Association enseignant-matière non trouvée");
        }
        teacherSubjectRepository.deleteById(id);
    }
}

