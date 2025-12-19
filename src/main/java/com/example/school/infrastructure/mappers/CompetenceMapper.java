package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.Competence;
import com.example.school.domain.entities.Subject;
import com.example.school.infrastructure.models.CompetenceModel;

public class CompetenceMapper {
    public static Competence toDomain(CompetenceModel model) {
        if (model == null) return null;
        Competence competence = new Competence();
        competence.setId(model.getId());

        // Mapper un Subject "léger" sans ses collections pour éviter
        // les ConcurrentModificationException et le chargement en cascade.
        if (model.getSubject() != null) {
            Subject subject = new Subject();
            subject.setId(model.getSubject().getId());
            subject.setCode(model.getSubject().getCode());
            subject.setName(model.getSubject().getName());
            subject.setDescription(model.getSubject().getDescription());
            competence.setSubject(subject);
        } else {
            competence.setSubject(null);
        }

        competence.setDescription(model.getDescription());
        competence.setOrderNumber(model.getOrderNumber());
        return competence;
    }

    public static CompetenceModel toModel(Competence competence) {
        if (competence == null) return null;
        CompetenceModel model = new CompetenceModel();
        model.setId(competence.getId());
        // Ici on n’a besoin que de l’identifiant (et quelques champs simples)
        // du Subject, pas de ses collections.
        if (competence.getSubject() != null) {
            var subjectModel = SubjectMapper.toModel(competence.getSubject());
            // SubjectMapper.toModel ne mappe pas les collections de SubjectModel,
            // donc cela reste sûr de ce côté.
            model.setSubject(subjectModel);
        } else {
            model.setSubject(null);
        }
        model.setDescription(competence.getDescription());
        model.setOrderNumber(competence.getOrderNumber());
        return model;
    }
}

