package com.example.school.infrastructure.repositories.jpa;

import com.example.school.infrastructure.models.ClassFeeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface JpaClassFeeRepository extends JpaRepository<ClassFeeModel, UUID> {
    List<ClassFeeModel> findByClassRoom_Id(UUID classRoomId);
    List<ClassFeeModel> findByClassRoom_School_Id(UUID schoolId);
}
