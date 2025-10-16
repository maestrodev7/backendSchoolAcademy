package com.example.school.application.services;

import com.example.school.common.dto.ClassLevelDto;
import com.example.school.common.exceptions.ResourceAlreadyExistsException;
import com.example.school.common.mapper.ClassLevelMapper;
import com.example.school.domain.entities.ClassLevel;
import com.example.school.domain.repositories.ClassLevelRepositoryInterface;
import com.example.school.domain.services.ClassLevelServiceInterface;
import com.example.school.presenation.validators.ClassLevelRequestValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassLevelService implements ClassLevelServiceInterface {

    private final ClassLevelRepositoryInterface classLevelRepository;

    @Override
    public ClassLevelDto createClassLevel(ClassLevelRequestValidator request) {
        ClassLevel classLevel = new ClassLevel();
        classLevel.setName(request.getName());

        ClassLevel saved = classLevelRepository.save(classLevel);
        return ClassLevelMapper.toDto(saved);
    }

    @Override
    public List<ClassLevelDto> getAllClassLevels() {
        return classLevelRepository.findAll()
                .stream()
                .map(ClassLevelMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClassLevelDto getClassLevelById(UUID id) {
        return classLevelRepository.findById(id)
                .map(ClassLevelMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Niveau de classe non trouvé"));
    }

    @Override
    public ClassLevelDto updateClassLevel(UUID id, ClassLevelRequestValidator request) {
        ClassLevel existing = classLevelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Niveau de classe non trouvé"));

        existing.setName(request.getName());
        ClassLevel updated = classLevelRepository.save(existing);

        return ClassLevelMapper.toDto(updated);
    }

    @Override
    public void deleteClassLevel(UUID id) {
        classLevelRepository.deleteById(id);
    }
}
