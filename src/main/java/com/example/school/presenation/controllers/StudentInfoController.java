package com.example.school.presenation.controllers;

import com.example.school.common.dto.ApiResponse;
import com.example.school.common.dto.StudentInfoDto;
import com.example.school.domain.services.StudentInfoServiceInterface;
import com.example.school.presenation.validators.StudentInfoRequestValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/student-infos")
@RequiredArgsConstructor
@CrossOrigin
public class StudentInfoController {

    private final StudentInfoServiceInterface studentInfoService;

    @PostMapping
    public ResponseEntity<ApiResponse<StudentInfoDto>> createStudentInfo(@Valid @RequestBody StudentInfoRequestValidator request) {
        StudentInfoDto dto = studentInfoService.createStudentInfo(request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Informations de l'élève créées avec succès", dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentInfoDto>> getStudentInfoById(@PathVariable UUID id) {
        StudentInfoDto dto = studentInfoService.getStudentInfoById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Informations récupérées avec succès", dto));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponse<StudentInfoDto>> getStudentInfoByStudentId(@PathVariable UUID studentId) {
        StudentInfoDto dto = studentInfoService.getStudentInfoByStudentId(studentId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Informations récupérées avec succès", dto));
    }

    @GetMapping("/academic-year/{academicYearId}")
    public ResponseEntity<ApiResponse<List<StudentInfoDto>>> getStudentInfosByAcademicYear(
            @PathVariable UUID academicYearId) {
        List<StudentInfoDto> dtos = studentInfoService.getStudentInfosByAcademicYear(academicYearId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Informations récupérées avec succès", dtos));
    }

    @GetMapping("/class/{classRoomId}/academic-year/{academicYearId}")
    public ResponseEntity<ApiResponse<List<StudentInfoDto>>> getStudentInfosByClassRoomAndAcademicYear(
            @PathVariable UUID classRoomId,
            @PathVariable UUID academicYearId) {
        List<StudentInfoDto> dtos = studentInfoService.getStudentInfosByClassRoomAndAcademicYear(classRoomId, academicYearId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Informations récupérées avec succès", dtos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentInfoDto>> updateStudentInfo(
            @PathVariable UUID id,
            @Valid @RequestBody StudentInfoRequestValidator request) {
        StudentInfoDto dto = studentInfoService.updateStudentInfo(id, request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Informations mises à jour avec succès", dto));
    }
}

