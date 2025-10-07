package com.example.school.domain.services;

import com.example.school.common.dto.ParentEleveDto;
import com.example.school.presenation.validators.ParentEleveRequestValidator;

import java.util.List;
import java.util.UUID;

public interface ParentEleveServiceInterface {
    ParentEleveDto create(ParentEleveRequestValidator request);
    List<UUID> findEnfantsByParent(UUID parentId) ;
    List<UUID> findParentsByEleve(UUID eleveId);
    ParentEleveDto getById(UUID id);
    void delete(UUID id);
}
