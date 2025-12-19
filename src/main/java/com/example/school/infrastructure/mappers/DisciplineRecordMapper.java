package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.DisciplineRecord;
import com.example.school.infrastructure.models.DisciplineRecordModel;

public class DisciplineRecordMapper {
    public static DisciplineRecord toDomain(DisciplineRecordModel model) {
        if (model == null) return null;
        DisciplineRecord record = new DisciplineRecord();
        record.setId(model.getId());
        record.setStudent(model.getStudent() != null ? UserMapper.toDomain(model.getStudent()) : null);
        record.setTerm(model.getTerm() != null ? TermMapper.toDomain(model.getTerm()) : null);
        record.setClassRoom(model.getClassRoom() != null ? ClassRoomMapper.toDomain(model.getClassRoom()) : null);
        record.setUnjustifiedAbsencesHours(model.getUnjustifiedAbsencesHours());
        record.setJustifiedAbsencesHours(model.getJustifiedAbsencesHours());
        record.setLateCount(model.getLateCount());
        record.setDetentionHours(model.getDetentionHours());
        record.setConductWarning(model.getConductWarning());
        record.setConductBlame(model.getConductBlame());
        record.setExclusionDays(model.getExclusionDays());
        record.setPermanentExclusion(model.getPermanentExclusion());
        return record;
    }

    public static DisciplineRecordModel toModel(DisciplineRecord record) {
        if (record == null) return null;
        DisciplineRecordModel model = new DisciplineRecordModel();
        model.setId(record.getId());
        model.setStudent(record.getStudent() != null ? UserMapper.toModel(record.getStudent()) : null);
        model.setTerm(record.getTerm() != null ? TermMapper.toModel(record.getTerm()) : null);
        model.setClassRoom(record.getClassRoom() != null ? ClassRoomMapper.toModel(record.getClassRoom()) : null);
        model.setUnjustifiedAbsencesHours(record.getUnjustifiedAbsencesHours());
        model.setJustifiedAbsencesHours(record.getJustifiedAbsencesHours());
        model.setLateCount(record.getLateCount());
        model.setDetentionHours(record.getDetentionHours());
        model.setConductWarning(record.getConductWarning());
        model.setConductBlame(record.getConductBlame());
        model.setExclusionDays(record.getExclusionDays());
        model.setPermanentExclusion(record.getPermanentExclusion());
        return model;
    }
}

