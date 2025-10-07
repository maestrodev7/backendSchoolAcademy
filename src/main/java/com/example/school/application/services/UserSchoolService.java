package com.example.school.application.services;

import com.example.school.common.dto.UserSchoolDto;
import com.example.school.common.mapper.UserSchoolMapper;
import com.example.school.domain.entities.UserSchool;
import com.example.school.domain.repositories.UserSchoolRepositoryInterface;
import com.example.school.domain.services.UserSchoolServiceInterface;
import com.example.school.presenation.validators.UserSchoolRequestValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserSchoolService implements UserSchoolServiceInterface {

    private final UserSchoolRepositoryInterface repository;

    @Override
    public UserSchoolDto create(@NotNull UserSchoolRequestValidator request) {
        UserSchool entity = new UserSchool();
        entity.setUserId(request.getUserId());
        entity.setSchoolId(request.getSchoolId());
        entity.setRole(request.getRole());

        UserSchool saved = repository.save(entity);
        return UserSchoolMapper.toDto(saved);
    }

    @Override
    public UserSchoolDto getById(UUID id) {
        return repository.findById(id)
                .map(UserSchoolMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Relation User-School non trouvée"));
    }

    @Override
    public List<UserSchoolDto> getAll() {
        return repository.findAll()
                .stream()
                .map(UserSchoolMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        if (repository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Relation User-School non trouvée");
        }
        repository.deleteById(id);
    }
}
