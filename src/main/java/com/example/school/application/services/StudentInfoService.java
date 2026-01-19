package com.example.school.application.services;

import com.example.school.common.dto.StudentInfoDto;
import com.example.school.common.exceptions.BusinessRuleException;
import com.example.school.common.exceptions.ResourceAlreadyExistsException;
import com.example.school.common.mapper.StudentInfoMapper;
import com.example.school.domain.entities.StudentInfo;
import com.example.school.domain.entities.StudentRegistration;
import com.example.school.domain.entities.User;
import com.example.school.domain.repositories.StudentInfoRepositoryInterface;
import com.example.school.domain.repositories.StudentRegistrationRepositoryInterface;
import com.example.school.domain.repositories.UserRepositoryInterface;
import com.example.school.domain.services.StudentInfoServiceInterface;
import com.example.school.presenation.validators.StudentInfoRequestValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentInfoService implements StudentInfoServiceInterface {

    private final StudentInfoRepositoryInterface studentInfoRepository;
    private final UserRepositoryInterface userRepository;
    private final StudentRegistrationRepositoryInterface studentRegistrationRepository;

    @Override
    @Transactional
    public StudentInfoDto createStudentInfo(StudentInfoRequestValidator request) {
        // Vérifier que les champs requis sont présents
        if (request.getParentId() == null) {
            throw new BusinessRuleException("L'ID du parent est requis");
        }
        if (request.getClassRoomId() == null) {
            throw new BusinessRuleException("L'ID de la classe est requis");
        }

        // Vérifier que l'élève existe
        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Élève non trouvé"));

        // Vérifier si des informations existent déjà pour cet élève
        if (studentInfoRepository.findByStudentId(request.getStudentId()).isPresent()) {
            throw new ResourceAlreadyExistsException("Des informations existent déjà pour cet élève");
        }

        // Vérifier que l'élève est bien inscrit dans la classe demandée
        List<StudentRegistration> registrations = studentRegistrationRepository.findByStudent(request.getStudentId());
        boolean isRegisteredInClass = registrations.stream()
                .anyMatch(reg -> reg.getClassRoom() != null 
                        && reg.getClassRoom().getId().equals(request.getClassRoomId()));
        
        if (!isRegisteredInClass) {
            throw new BusinessRuleException("L'élève n'est pas inscrit dans cette classe");
        }

        // Vérifier que le parent existe
        User parent = userRepository.findById(request.getParentId())
                .orElseThrow(() -> new EntityNotFoundException("Parent non trouvé"));

        // Générer l'identifiant unique
        String uniqueIdentifier = generateUniqueIdentifier();
        // Vérifier l'unicité (au cas où il y aurait une collision)
        int retryCount = 0;
        while (studentInfoRepository.existsByUniqueIdentifier(uniqueIdentifier) && retryCount < 10) {
            uniqueIdentifier = generateUniqueIdentifier();
            retryCount++;
        }
        if (studentInfoRepository.existsByUniqueIdentifier(uniqueIdentifier)) {
            throw new BusinessRuleException("Impossible de générer un identifiant unique. Veuillez réessayer.");
        }

        // Construire les informations parent automatiquement
        String parentNames = buildParentNames(parent);
        String parentContacts = buildParentContacts(parent);

        StudentInfo info = new StudentInfo();
        info.setStudent(student);
        info.setUniqueIdentifier(uniqueIdentifier);
        info.setBirthDate(request.getBirthDate());
        info.setBirthPlace(request.getBirthPlace());
        info.setGender(request.getGender());
        info.setRepeating(request.getIsRepeating() != null ? request.getIsRepeating() : false);
        info.setParentNames(parentNames);
        info.setParentContacts(parentContacts);
        info.setPhotoUrl(request.getPhotoUrl());

        StudentInfo saved = studentInfoRepository.save(info);
        return StudentInfoMapper.toDto(saved);
    }

    private String generateUniqueIdentifier() {
        int year = java.time.LocalDate.now().getYear();
        String yearStr = String.valueOf(year);
        
        // Compter combien d'identifiants existent déjà pour cette année
        long countForYear = studentInfoRepository.countByYear(yearStr);
        long next = countForYear + 1;
        
        String sequence = String.format("%03d", next); // 001, 002, ...
        return "STU-" + yearStr + "-" + sequence;
    }

    private String buildParentNames(User parent) {
        String firstName = parent.getFirstName() != null ? parent.getFirstName() : "";
        String lastName = parent.getLastName() != null ? parent.getLastName() : "";
        return (firstName + " " + lastName).trim();
    }

    private String buildParentContacts(User parent) {
        StringBuilder contacts = new StringBuilder();
        if (parent.getPhoneNumber() != null && !parent.getPhoneNumber().isEmpty()) {
            contacts.append("Tel: ").append(parent.getPhoneNumber());
        }
        if (parent.getEmail() != null && !parent.getEmail().isEmpty()) {
            if (contacts.length() > 0) {
                contacts.append(", ");
            }
            contacts.append("Email: ").append(parent.getEmail());
        }
        return contacts.toString();
    }

    @Override
    public StudentInfoDto getStudentInfoById(UUID id) {
        return studentInfoRepository.findById(id)
                .map(StudentInfoMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Informations de l'élève non trouvées"));
    }

    @Override
    public StudentInfoDto getStudentInfoByStudentId(UUID studentId) {
        return studentInfoRepository.findByStudentId(studentId)
                .map(StudentInfoMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Informations de l'élève non trouvées"));
    }

    @Override
    @Transactional
    public StudentInfoDto updateStudentInfo(UUID id, StudentInfoRequestValidator request) {
        StudentInfo existing = studentInfoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Informations de l'élève non trouvées"));

        // Si un nouveau parent est fourni, mettre à jour les infos parent
        if (request.getParentId() != null) {
            User parent = userRepository.findById(request.getParentId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent non trouvé"));
            existing.setParentNames(buildParentNames(parent));
            existing.setParentContacts(buildParentContacts(parent));
        }

        existing.setBirthDate(request.getBirthDate());
        existing.setBirthPlace(request.getBirthPlace());
        existing.setGender(request.getGender());
        if (request.getIsRepeating() != null) {
            existing.setRepeating(request.getIsRepeating());
        }
        existing.setPhotoUrl(request.getPhotoUrl());

        StudentInfo updated = studentInfoRepository.save(existing);
        return StudentInfoMapper.toDto(updated);
    }

    @Override
    public List<StudentInfoDto> getStudentInfosByAcademicYear(UUID academicYearId) {
        // Récupérer toutes les inscriptions pour cette année académique
        List<StudentRegistration> registrations = studentRegistrationRepository.findByAcademicYear(academicYearId);
        
        // Extraire les IDs des élèves
        List<UUID> studentIds = registrations.stream()
                .map(reg -> reg.getStudent().getId())
                .distinct()
                .collect(Collectors.toList());

        if (studentIds.isEmpty()) {
            return List.of();
        }

        // Récupérer les StudentInfo pour ces élèves
        List<StudentInfo> studentInfos = studentInfoRepository.findByStudentIds(studentIds);

        // Trier par nom d'élève (ordre alphabétique) pour un affichage cohérent côté front
        return studentInfos.stream()
                .map(StudentInfoMapper::toDto)
                .sorted(Comparator.comparing(
                        StudentInfoDto::getStudentName,
                        Comparator.nullsLast(String::compareToIgnoreCase)
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentInfoDto> getStudentInfosByClassRoomAndAcademicYear(UUID classRoomId, UUID academicYearId) {
        // Récupérer les inscriptions pour cette classe
        List<StudentRegistration> registrations = studentRegistrationRepository.findByClassRoom(classRoomId);
        
        // Filtrer par année académique
        List<UUID> studentIds = registrations.stream()
                .filter(reg -> reg.getAcademicYear() != null 
                        && reg.getAcademicYear().getId().equals(academicYearId))
                .map(reg -> reg.getStudent().getId())
                .distinct()
                .collect(Collectors.toList());

        if (studentIds.isEmpty()) {
            return List.of();
        }

        // Récupérer les StudentInfo pour ces élèves
        List<StudentInfo> studentInfos = studentInfoRepository.findByStudentIds(studentIds);

        // Trier les élèves par ordre alphabétique (par nom complet)
        return studentInfos.stream()
                .map(StudentInfoMapper::toDto)
                .sorted(Comparator.comparing(
                        StudentInfoDto::getStudentName,
                        Comparator.nullsLast(String::compareToIgnoreCase)
                ))
                .collect(Collectors.toList());
    }
}

