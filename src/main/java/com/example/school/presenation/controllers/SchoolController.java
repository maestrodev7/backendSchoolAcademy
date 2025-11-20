package com.example.school.presenation.controllers;

import com.example.school.common.dto.ApiResponse;
import com.example.school.common.dto.SchoolDto;
import com.example.school.domain.services.SchoolServiceInterface;
import com.example.school.presenation.validators.SchoolRequestValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/schools")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolServiceInterface schoolService;

    @PostMapping
    public ResponseEntity<ApiResponse<SchoolDto>> createSchool(@Valid @RequestBody SchoolRequestValidator request) {
        SchoolDto school = schoolService.createSchool(request);
        return ResponseEntity.ok(new ApiResponse<>(200, "École créée avec succès", school));
    }
 
    @GetMapping
    public ResponseEntity<ApiResponse<List<SchoolDto>>> getAllSchools() {
        List<SchoolDto> schools = schoolService.getAllSchools();
        System.out.println("Nombre d'écoles récupérées : " + schools.size());
        schools.forEach(s -> System.out.println("École : " + s.getName() + " | Adresse : " + s.getAddress()));

        return ResponseEntity.ok(new ApiResponse<>(200, "Liste des écoles récupérée avec succès", schools));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SchoolDto>> getSchoolById(@PathVariable UUID id) {
        SchoolDto school = schoolService.getSchoolById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "École récupérée avec succès", school));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SchoolDto>> updateSchool(
            @PathVariable UUID id,
            @Valid @RequestBody SchoolRequestValidator request
    ) {
        SchoolDto updatedSchool = schoolService.updateSchool(id, request);
        return ResponseEntity.ok(new ApiResponse<>(200, "École mise à jour avec succès", updatedSchool));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSchool(@PathVariable UUID id) {
        schoolService.deleteSchool(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "École supprimée avec succès", null));
    }

    @PutMapping("/{schoolId}/current-academic-year/{academicYearId}")
    public ResponseEntity<ApiResponse<SchoolDto>> setCurrentAcademicYear(
            @PathVariable UUID schoolId,
            @PathVariable UUID academicYearId) {
        SchoolDto updatedSchool = schoolService.setCurrentAcademicYear(schoolId, academicYearId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Année académique en cours modifiée avec succès", updatedSchool));
    }
}
