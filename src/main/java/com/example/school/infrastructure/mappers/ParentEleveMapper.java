package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.ParentEleve;
import com.example.school.infrastructure.models.ParentEleveModel;

public class ParentEleveMapper {

    public static ParentEleve toDomain(ParentEleveModel model) {
        ParentEleve pe = new ParentEleve();
        pe.setId(model.getId());
        pe.setParentId(model.getParentId());
        pe.setEleveId(model.getEleveId());
        return pe;
    }

    public static ParentEleveModel toModel(ParentEleve entity) {
        ParentEleveModel model = new ParentEleveModel();
        model.setId(entity.getId());
        model.setParentId(entity.getParentId());
        model.setEleveId(entity.getEleveId());
        return model;
    }
}
