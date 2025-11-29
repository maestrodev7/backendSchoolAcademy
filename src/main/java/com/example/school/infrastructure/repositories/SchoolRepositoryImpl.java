package com.example.school.infrastructure.repositories;

import com.example.school.domain.entities.School;
import com.example.school.domain.repositories.SchoolRepositoryInterface;
import com.example.school.infrastructure.mappers.SchoolMapper;
import com.example.school.infrastructure.models.SchoolModel;
import com.example.school.infrastructure.repositories.jpa.JpaSchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SchoolRepositoryImpl implements SchoolRepositoryInterface {

    private final JpaSchoolRepository jpaSchoolRepository;

    @Override
    public School save(School school) {
        SchoolModel model = SchoolMapper.toModel(school);
        SchoolModel saved = jpaSchoolRepository.save(model);
        return SchoolMapper.toDomain(saved);
    }

    @Override
    public Optional<School> findById(UUID id) {
        // Utiliser findByIdWithAcademicYears pour charger les années académiques
        return jpaSchoolRepository.findByIdWithAcademicYears(id).map(SchoolMapper::toDomain);
    }
    @Override
    public List<School> findAll() {
        var results = jpaSchoolRepository.findAllWithAcademicYears();
        System.out.println("Résultats JPA : " + results);

        return results.stream()
                .map(school -> {
                    System.out.println("Mapping school : " + school);
                    return SchoolMapper.toDomain(school);
                })
                .collect(Collectors.toList());
    }



    @Override
    public void deleteById(UUID id) {
        jpaSchoolRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaSchoolRepository.existsById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return jpaSchoolRepository.existsByName(name);
    }
}
