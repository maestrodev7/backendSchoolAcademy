package com.example.school.common.mapper;

import com.example.school.common.dto.FeeTypeDto;
import com.example.school.domain.entities.FeeType;

public final class FeeTypeDtoMapper {

    private FeeTypeDtoMapper() {
    }

    public static FeeTypeDto toDto(FeeType feeType) {
        if (feeType == null) {
            return null;
        }
        FeeTypeDto dto = new FeeTypeDto();
        dto.setId(feeType.getId());
        dto.setName(feeType.getName());
        dto.setMandatory(feeType.isMandatory());
        if (feeType.getSchool() != null) {
            dto.setSchoolId(feeType.getSchool().getId());
        }
        return dto;
    }
}

