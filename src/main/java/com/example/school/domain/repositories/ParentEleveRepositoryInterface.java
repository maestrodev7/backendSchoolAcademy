package com.example.school.domain.repositories;

import com.example.school.domain.entities.ParentEleve;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParentEleveRepositoryInterface {
    ParentEleve save(ParentEleve relation);
    List<UUID>  findEnfantsByParent(UUID parentId);
    List<UUID> findParentsByEleve(UUID eleveId);
    Optional<ParentEleve> findById(UUID id);
    void deleteById(UUID id);
}
