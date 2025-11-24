package com.example.school.infrastructure.repositories;

import com.example.school.domain.entities.Subject;
import com.example.school.domain.repositories.SubjectRepositoryInterface;
import com.example.school.infrastructure.mappers.SubjectMapper;
import com.example.school.infrastructure.models.SubjectModel;
import com.example.school.infrastructure.repositories.jpa.JpaSubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SubjectRepositoryImpl implements SubjectRepositoryInterface {

    private final JpaSubjectRepository jpaSubjectRepository;

    @Override
    public Subject save(Subject subject) {
        SubjectModel model = SubjectMapper.toModel(subject);
        SubjectModel saved = jpaSubjectRepository.save(model);
        return SubjectMapper.toDomain(saved);
    }

    @Override
    public Optional<Subject> findById(UUID id) {
        return jpaSubjectRepository.findByIdWithClassRooms(id)
                .map(SubjectMapper::toDomain);
    }

    @Override
    public Optional<Subject> findByCode(String code) {
        return jpaSubjectRepository.findByCode(code)
                .map(SubjectMapper::toDomain);
    }

    @Override
    public List<Subject> findAll() {
        // Utiliser findAllWithClassRooms() pour charger toutes les relations en une seule requête
        // Cela évite le lazy loading et les problèmes de ConcurrentModificationException
        return jpaSubjectRepository.findAllWithClassRooms()
                .stream()
                .map(SubjectMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaSubjectRepository.deleteById(id);
    }

    @Override
    public boolean existsByCode(String code) {
        return jpaSubjectRepository.existsByCode(code);
    }
}

