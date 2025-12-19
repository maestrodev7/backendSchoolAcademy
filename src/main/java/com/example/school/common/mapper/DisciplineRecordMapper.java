package com.example.school.common.mapper;

import com.example.school.common.dto.DisciplineRecordDto;
import com.example.school.domain.entities.DisciplineRecord;

public class DisciplineRecordMapper {
    public static DisciplineRecordDto toDto(DisciplineRecord record) {
        if (record == null) return null;
        DisciplineRecordDto dto = new DisciplineRecordDto();
        dto.setId(record.getId());
        dto.setStudentId(record.getStudent() != null ? record.getStudent().getId() : null);
        dto.setStudentName(record.getStudent() != null 
                ? (record.getStudent().getFirstName() + " " + record.getStudent().getLastName()).trim() : null);
        dto.setTermId(record.getTerm() != null ? record.getTerm().getId() : null);
        dto.setTermName(record.getTerm() != null ? record.getTerm().getName() : null);
        dto.setClassRoomId(record.getClassRoom() != null ? record.getClassRoom().getId() : null);
        dto.setClassRoomLabel(record.getClassRoom() != null ? record.getClassRoom().getLabel() : null);
        dto.setUnjustifiedAbsencesHours(record.getUnjustifiedAbsencesHours());
        dto.setJustifiedAbsencesHours(record.getJustifiedAbsencesHours());
        dto.setLateCount(record.getLateCount());
        dto.setDetentionHours(record.getDetentionHours());
        dto.setConductWarning(record.getConductWarning());
        dto.setConductBlame(record.getConductBlame());
        dto.setExclusionDays(record.getExclusionDays());
        dto.setPermanentExclusion(record.getPermanentExclusion());
        return dto;
    }
}

