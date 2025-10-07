package com.example.school.infrastructure.repositories;

import com.example.school.domain.entities.ParentEleve;
import com.example.school.domain.repositories.ParentEleveRepositoryInterface;
import com.example.school.infrastructure.mappers.ParentEleveMapper;
import com.example.school.infrastructure.models.ParentEleveModel;
import com.example.school.infrastructure.repositories.jpa.JpaParentEleveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ParentEleveRepositoryImpl implements ParentEleveRepositoryInterface {

    private final JpaParentEleveRepository jpaRepository;

    @Override
    public ParentEleve save(ParentEleve relation) {
        ParentEleveModel model = ParentEleveMapper.toModel(relation);
        ParentEleveModel saved = jpaRepository.save(model);
        return ParentEleveMapper.toDomain(saved);
    }

    @Override
    public List<UUID> findEnfantsByParent(UUID parentId) {
        return jpaRepository.findEnfantsByParent(parentId);
    }

    @Override
    public List<UUID> findParentsByEleve(UUID eleveId) {
        return jpaRepository.findParentsByEleve(eleveId);
    }

    @Override
    public Optional<ParentEleve> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(ParentEleveMapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
