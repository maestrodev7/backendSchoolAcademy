package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.Sequence;
import com.example.school.infrastructure.models.SequenceModel;

public class SequenceMapper {
    public static Sequence toDomain(SequenceModel model) {
        if (model == null) return null;
        Sequence sequence = new Sequence();
        sequence.setId(model.getId());
        sequence.setName(model.getName());
        sequence.setNumber(model.getNumber());
        sequence.setTermId(model.getTermId());
        sequence.setSchoolId(model.getSchoolId());
        sequence.setStartDate(model.getStartDate());
        sequence.setEndDate(model.getEndDate());
        sequence.setActive(model.isActive());
        return sequence;
    }

    public static SequenceModel toModel(Sequence sequence) {
        if (sequence == null) return null;
        SequenceModel model = new SequenceModel();
        model.setId(sequence.getId());
        model.setName(sequence.getName());
        model.setNumber(sequence.getNumber());
        model.setTermId(sequence.getTermId());
        model.setSchoolId(sequence.getSchoolId());
        model.setStartDate(sequence.getStartDate());
        model.setEndDate(sequence.getEndDate());
        model.setActive(sequence.isActive());
        return model;
    }
}

