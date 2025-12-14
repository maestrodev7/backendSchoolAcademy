package com.example.school.common.mapper;

import com.example.school.common.dto.SequenceDto;
import com.example.school.domain.entities.Sequence;

public class SequenceMapper {
    public static SequenceDto toDto(Sequence sequence) {
        if (sequence == null) return null;
        SequenceDto dto = new SequenceDto();
        dto.setId(sequence.getId());
        dto.setName(sequence.getName());
        dto.setNumber(sequence.getNumber());
        dto.setTermId(sequence.getTermId());
        dto.setSchoolId(sequence.getSchoolId());
        dto.setStartDate(sequence.getStartDate());
        dto.setEndDate(sequence.getEndDate());
        dto.setActive(sequence.isActive());
        return dto;
    }
}

