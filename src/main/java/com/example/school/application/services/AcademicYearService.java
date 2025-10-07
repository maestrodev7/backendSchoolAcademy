package com.example.school.application.services;

import com.example.school.common.dto.AcademicYearDto;
import com.example.school.common.exceptions.ResourceAlreadyExistsException;
import com.example.school.common.mapper.AcademicYearMapper;
import com.example.school.domain.entities.AcademicYear;
import com.example.school.domain.repositories.AcademicYearRepositoryInterface;
import com.example.school.domain.services.AcademicYearServiceInterface;
import com.example.school.presenation.validators.AcademicYearRequestValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AcademicYearService implements AcademicYearServiceInterface {

    private final AcademicYearRepositoryInterface academicYearRepository;

    @Override
    public AcademicYearDto createAcademicYear(@NotNull AcademicYearRequestValidator request) {

        AcademicYear year = new AcademicYear();
        year.setStartDate(request.getStartDate());
        year.setEndDate(request.getEndDate());
        year.setActive(request.isActive());

        AcademicYear saved = academicYearRepository.save(year);
        return AcademicYearMapper.toDto(saved);
    }

    @Override
    public List<AcademicYearDto> getAllAcademicYears() {
        return academicYearRepository.findAll()
                .stream()
                .map(AcademicYearMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AcademicYearDto getAcademicYearById(UUID id) {
        return academicYearRepository.findById(id)
                .map(AcademicYearMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Année académique non trouvée"));
    }

    @Override
    public AcademicYearDto updateAcademicYear(UUID id, @NotNull AcademicYearRequestValidator request) {
        AcademicYear existing = academicYearRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Année académique non trouvée"));

        existing.setStartDate(request.getStartDate());
        existing.setEndDate(request.getEndDate());
        existing.setActive(request.isActive());

        AcademicYear updated = academicYearRepository.save(existing);
        return AcademicYearMapper.toDto(updated);
    }

    @Override
    public void deleteAcademicYear(UUID id) {
        if (!academicYearRepository.existsById(id)) {
            throw new EntityNotFoundException("Année académique non trouvée");
        }
        academicYearRepository.deleteById(id);
    }

}
