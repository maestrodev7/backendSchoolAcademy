package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.ClassRoomSubject;
import com.example.school.domain.entities.Subject;
import com.example.school.infrastructure.models.ClassRoomSubjectModel;
import com.example.school.infrastructure.models.SubjectModel;

public class ClassRoomSubjectMapper {

    public static ClassRoomSubject toDomain(ClassRoomSubjectModel model) {
        if (model == null) return null;
        
        ClassRoomSubject domain = new ClassRoomSubject();
        domain.setId(model.getId());
        
        // Mapper le Subject sans les relations circulaires
        if (model.getSubject() != null) {
            Subject subject = new Subject();
            subject.setId(model.getSubject().getId());
            subject.setCode(model.getSubject().getCode());
            subject.setName(model.getSubject().getName());
            subject.setDescription(model.getSubject().getDescription());
            // Ne pas mapper classRoomSubjects pour éviter la récursion
            domain.setSubject(subject);
        }
        
        domain.setClassRoom(ClassRoomMapper.toDomain(model.getClassRoom()));
        domain.setCoefficient(model.getCoefficient());
        
        return domain;
    }

    public static ClassRoomSubjectModel toModel(ClassRoomSubject domain) {
        if (domain == null) return null;
        
        ClassRoomSubjectModel model = new ClassRoomSubjectModel();
        model.setId(domain.getId());
        
        // Mapper le Subject sans les relations circulaires
        if (domain.getSubject() != null) {
            SubjectModel subjectModel = new SubjectModel();
            subjectModel.setId(domain.getSubject().getId());
            subjectModel.setCode(domain.getSubject().getCode());
            subjectModel.setName(domain.getSubject().getName());
            subjectModel.setDescription(domain.getSubject().getDescription());
            // Ne pas mapper classRoomSubjects pour éviter la récursion
            model.setSubject(subjectModel);
        }
        
        model.setClassRoom(ClassRoomMapper.toModel(domain.getClassRoom()));
        model.setCoefficient(domain.getCoefficient());
        
        return model;
    }
}

