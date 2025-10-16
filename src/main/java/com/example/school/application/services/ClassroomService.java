package com.example.school.application.services;

import com.example.school.common.dto.ClassroomDto;
import com.example.school.common.mapper.ClassroomMapper;
import com.example.school.domain.entities.ClassLevel;
import com.example.school.domain.entities.ClassRoom;
import com.example.school.domain.entities.Series;
import com.example.school.domain.repositories.ClassRoomRepositoryInterface;
import com.example.school.domain.services.ClassroomServiceInterface;
import com.example.school.presenation.validators.ClassroomRequestValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassroomService implements ClassroomServiceInterface {

    private final ClassRoomRepositoryInterface classroomRepository;

    @Override
    public ClassroomDto createClassroom(ClassroomRequestValidator request) {
        ClassRoom classroom = new ClassRoom();
        classroom.setLabel(request.getLabel());

        ClassLevel classLevel = new ClassLevel();
        classLevel.setId(request.getClassLevelId());
        classroom.setClassLevel(classLevel);

        Series series = new Series();
        series.setId(request.getSeriesId());
        classroom.setSeries(series);

        ClassRoom saved = classroomRepository.save(classroom);
        return ClassroomMapper.toDto(saved);
    }

    @Override
    public List<ClassroomDto> getAllClassrooms() {
        return classroomRepository.findAll()
                .stream()
                .map(ClassroomMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClassroomDto getClassroomById(UUID id) {
        return classroomRepository.findById(id)
                .map(ClassroomMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Classe non trouvée"));
    }

    @Override
    public ClassroomDto updateClassroom(UUID id, ClassroomRequestValidator request) {
        ClassRoom existing = classroomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Classe non trouvée"));

        existing.setLabel(request.getLabel());

        ClassLevel classLevel = new ClassLevel();
        classLevel.setId(request.getClassLevelId());
        existing.setClassLevel(classLevel);

        Series series = new Series();
        series.setId(request.getSeriesId());
        existing.setSeries(series);

        ClassRoom updated = classroomRepository.save(existing);
        return ClassroomMapper.toDto(updated);
    }

    @Override
    public void deleteClassroom(UUID id) {
        classroomRepository.deleteById(id);
    }
}
