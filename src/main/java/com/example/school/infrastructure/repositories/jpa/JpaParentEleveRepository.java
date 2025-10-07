package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.ParentEleveModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaParentEleveRepository extends JpaRepository<ParentEleveModel, UUID> {

    @Query("SELECT pe.eleveId FROM ParentEleveModel pe WHERE pe.parentId = :parentId")
    List<UUID> findEnfantsByParent(@Param("parentId") UUID parentId);

    @Query("SELECT pe.parentId FROM ParentEleveModel pe WHERE pe.eleveId = :eleveId")
    List<UUID> findParentsByEleve(@Param("eleveId") UUID eleveId);
}
