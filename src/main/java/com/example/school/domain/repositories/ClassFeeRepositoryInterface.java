package com.example.school.domain.repositories;

import com.example.school.domain.entities.ClassFee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClassFeeRepositoryInterface {
    ClassFee save(ClassFee classFee);

    Optional<ClassFee> findById(UUID id);

    List<ClassFee> findByClassRoom(UUID classRoomId);

    List<ClassFee> findBySchool(UUID schoolId);

    void deleteById(UUID id);
}

