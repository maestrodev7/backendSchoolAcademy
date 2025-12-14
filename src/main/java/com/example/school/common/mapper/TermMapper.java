package com.example.school.common.mapper;

import com.example.school.common.dto.SequenceDto;
import com.example.school.common.dto.TermDto;
import com.example.school.domain.entities.Term;

import java.util.List;
import java.util.stream.Collectors;

public class TermMapper {
    public static TermDto toDto(Term term) {
        if (term == null) return null;
        TermDto dto = new TermDto();
        dto.setId(term.getId());
        dto.setName(term.getName());
        dto.setNumber(term.getNumber());
        dto.setAcademicYearId(term.getAcademicYearId());
        dto.setSchoolId(term.getSchoolId());
        dto.setStartDate(term.getStartDate());
        dto.setEndDate(term.getEndDate());
        dto.setActive(term.isActive());
        
        if (term.getSequences() != null) {
            List<SequenceDto> sequences = term.getSequences().stream()
                    .map(SequenceMapper::toDto)
                    .collect(Collectors.toList());
            dto.setSequences(sequences);
        }
        
        return dto;
    }
}

