package com.example.school.common.mapper;

import com.example.school.common.dto.ParentEleveDto;
import com.example.school.domain.entities.ParentEleve;

public class ParentEleveMapper {

    public static ParentEleveDto toDto(ParentEleve entity) {
        if (entity == null) return null;

        ParentEleveDto dto = new ParentEleveDto();
        dto.setId(entity.getId());
        dto.setParentId(entity.getParentId());
        dto.setEleveId(entity.getEleveId());
        return dto;
    }

    public static ParentEleve toEntity(ParentEleveDto dto) {
        if (dto == null) return null;

        ParentEleve entity = new ParentEleve();
        entity.setId(dto.getId());
        entity.setParentId(dto.getParentId());
        entity.setEleveId(dto.getEleveId());
        return entity;
    }
}
