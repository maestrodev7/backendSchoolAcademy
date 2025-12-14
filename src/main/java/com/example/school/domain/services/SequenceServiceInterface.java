package com.example.school.domain.services;

import com.example.school.common.dto.SequenceDto;
import com.example.school.presenation.validators.SequenceRequestValidator;

import java.util.List;
import java.util.UUID;

public interface SequenceServiceInterface {
    SequenceDto createSequence(SequenceRequestValidator request);
    SequenceDto getSequenceById(UUID id);
    List<SequenceDto> getAllSequences();
    List<SequenceDto> getSequencesByTerm(UUID termId);
    List<SequenceDto> getSequencesByAcademicYear(UUID academicYearId);
    SequenceDto updateSequence(UUID id, SequenceRequestValidator request);
    void deleteSequence(UUID id);
    SequenceDto setActiveSequence(UUID termId, UUID sequenceId);
}

