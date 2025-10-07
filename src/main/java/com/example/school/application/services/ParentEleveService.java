// src/main/java/com/example/school/application/services/ParentEleveService.java
package com.example.school.application.services;

import com.example.school.common.dto.ParentEleveDto;
import com.example.school.common.mapper.ParentEleveMapper;
import com.example.school.domain.entities.ParentEleve;
import com.example.school.domain.repositories.ParentEleveRepositoryInterface;
import com.example.school.domain.services.ParentEleveServiceInterface;
import com.example.school.presenation.validators.ParentEleveRequestValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParentEleveService implements ParentEleveServiceInterface {

    private final ParentEleveRepositoryInterface repository;

    @Override
    public ParentEleveDto create(@NotNull ParentEleveRequestValidator request) {
        ParentEleve relation = new ParentEleve();
        relation.setParentId(request.getParentId());
        relation.setEleveId(request.getEleveId());

        ParentEleve saved = repository.save(relation);
        return ParentEleveMapper.toDto(saved);
    }

    @Override
    public List<UUID> findEnfantsByParent(UUID parentId) {
        return repository.findEnfantsByParent(parentId);
    }

    @Override
    public List<UUID> findParentsByEleve(UUID eleveId) {
        return repository.findParentsByEleve(eleveId);
    }

    @Override
    public ParentEleveDto getById(UUID id) {
        return repository.findById(id)
                .map(ParentEleveMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Relation Parent-Élève non trouvée"));
    }

    @Override
    public void delete(UUID id) {
        if (repository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Relation Parent-Élève non trouvée");
        }
        repository.deleteById(id);
    }
}
