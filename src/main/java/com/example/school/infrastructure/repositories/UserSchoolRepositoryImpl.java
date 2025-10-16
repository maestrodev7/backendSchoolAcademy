package com.example.school.infrastructure.repositories;

import com.example.school.domain.entities.UserSchool;
import com.example.school.domain.repositories.UserSchoolRepositoryInterface;
import com.example.school.infrastructure.mappers.UserSchoolMapper;
import com.example.school.infrastructure.models.UserSchoolModel;
import com.example.school.infrastructure.repositories.jpa.JpaUserSchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserSchoolRepositoryImpl implements UserSchoolRepositoryInterface {

    private final JpaUserSchoolRepository jpaRepository;

    @Override
    public UserSchool save(UserSchool userSchool) {
        UserSchoolModel model = UserSchoolMapper.toModel(userSchool);
        UserSchoolModel saved = jpaRepository.save(model);
        return UserSchoolMapper.toDomain(saved);
    }

    @Override
    public Optional<UserSchool> findById(UUID id) {
        return jpaRepository.findById(id).map(UserSchoolMapper::toDomain);
    }

    @Override
    public List<UserSchool> findAll() {
        return jpaRepository.findAll().stream()
                .map(UserSchoolMapper::toDomain)
                .collect(Collectors.toList());
    }

    public List<UserSchool> findAdminsOrPromoteurs() {
        return jpaRepository.findAllAdminsOrPromoteurs()
                .stream()
                .map(UserSchoolMapper::toDomain)
                .collect(Collectors.toList());
    }

    public List<UserSchool> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId)
                .stream()
                .map(UserSchoolMapper::toDomain)
                .collect(Collectors.toList());
    }



    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
