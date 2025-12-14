package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.Sequence;
import com.example.school.domain.entities.Term;
import com.example.school.infrastructure.models.SequenceModel;
import com.example.school.infrastructure.models.TermModel;

import java.util.Set;
import java.util.stream.Collectors;

public class TermMapper {
    public static Term toDomain(TermModel model) {
        if (model == null) return null;
        Term term = new Term();
        term.setId(model.getId());
        term.setName(model.getName());
        term.setNumber(model.getNumber());
        term.setAcademicYearId(model.getAcademicYearId());
        term.setSchoolId(model.getSchoolId());
        term.setStartDate(model.getStartDate());
        term.setEndDate(model.getEndDate());
        term.setActive(model.isActive());
        
        if (model.getSequences() != null) {
            Set<Sequence> sequences = model.getSequences().stream()
                    .map(SequenceMapper::toDomain)
                    .collect(Collectors.toSet());
            term.setSequences(sequences);
        }
        
        return term;
    }

    public static TermModel toModel(Term term) {
        if (term == null) return null;
        TermModel model = new TermModel();
        model.setId(term.getId());
        model.setName(term.getName());
        model.setNumber(term.getNumber());
        model.setAcademicYearId(term.getAcademicYearId());
        model.setSchoolId(term.getSchoolId());
        model.setStartDate(term.getStartDate());
        model.setEndDate(term.getEndDate());
        model.setActive(term.isActive());
        
        if (term.getSequences() != null) {
            Set<SequenceModel> sequences = term.getSequences().stream()
                    .map(SequenceMapper::toModel)
                    .collect(Collectors.toSet());
            model.setSequences(sequences);
        }
        
        return model;
    }
}

