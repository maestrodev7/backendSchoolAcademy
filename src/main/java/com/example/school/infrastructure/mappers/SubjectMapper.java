package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.ClassRoomSubject;
import com.example.school.domain.entities.Subject;
import com.example.school.infrastructure.models.ClassRoomSubjectModel;
import com.example.school.infrastructure.models.SubjectModel;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

public class SubjectMapper {

    public static Subject toDomain(SubjectModel model) {
        if (model == null) return null;
        
        Subject domain = new Subject();
        domain.setId(model.getId());
        domain.setCode(model.getCode());
        domain.setName(model.getName());
        domain.setDescription(model.getDescription());
        
        if (model.getClassRoomSubjects() != null) {
            // Créer une copie snapshot de la collection pour éviter ConcurrentModificationException
            // Hibernate peut modifier la collection pendant le lazy loading
            Set<ClassRoomSubject> classRoomSubjects = new ArrayList<>(model.getClassRoomSubjects())
                    .stream()
                    .map(ClassRoomSubjectMapper::toDomain)
                    .collect(Collectors.toSet());
            domain.setClassRoomSubjects(classRoomSubjects);
        }
        
        return domain;
    }

    public static SubjectModel toModel(Subject domain) {
        if (domain == null) return null;
        
        SubjectModel model = new SubjectModel();
        model.setId(domain.getId());
        model.setCode(domain.getCode());
        model.setName(domain.getName());
        model.setDescription(domain.getDescription());
        
        if (domain.getClassRoomSubjects() != null) {
            // Créer une copie snapshot de la collection pour éviter ConcurrentModificationException
            Set<ClassRoomSubjectModel> classRoomSubjects = new ArrayList<>(domain.getClassRoomSubjects())
                    .stream()
                    .map(ClassRoomSubjectMapper::toModel)
                    .collect(Collectors.toSet());
            model.setClassRoomSubjects(classRoomSubjects);
        }
        
        return model;
    }
}

