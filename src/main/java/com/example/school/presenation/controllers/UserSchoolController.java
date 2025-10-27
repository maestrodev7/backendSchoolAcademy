package com.example.school.presenation.controllers;

import com.example.school.common.dto.ApiResponse;
import com.example.school.common.dto.UserSchoolDto;
import com.example.school.domain.services.UserSchoolServiceInterface;
import com.example.school.presenation.validators.UserSchoolRequestValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user-schools")
@RequiredArgsConstructor
public class UserSchoolController {

    private final UserSchoolServiceInterface userSchoolService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserSchoolDto>> createUserSchool(
            @Valid @RequestBody UserSchoolRequestValidator request
    ) {
        UserSchoolDto dto = userSchoolService.create(request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Relation User-School créée avec succès", dto));
    }

    @GetMapping("/user/{userId}/schools")
    public ResponseEntity<ApiResponse<List<UserSchoolDto>>> getSchoolsByUserId(@PathVariable UUID userId) {
        List<UserSchoolDto> dtos = userSchoolService.getSchoolsByUserId(userId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Écoles de l'utilisateur récupérées avec succès", dtos));
    }

    @GetMapping("/schools-with-admins")
    public ResponseEntity<ApiResponse<List<UserSchoolDto>>> getSchoolsWithAdmins() {
        List<UserSchoolDto> dtos = userSchoolService.getSchoolsWithAdmins();
        return ResponseEntity.ok(new ApiResponse<>(200, "Liste des écoles avec leurs admins récupérée avec succès", dtos));
    }


    @GetMapping("/school/{schoolId}/teachers")
    public ResponseEntity<ApiResponse<List<UserSchoolDto>>> getTeachersBySchoolId(@PathVariable UUID schoolId) {
        List<UserSchoolDto> dtos = userSchoolService.getTeachersBySchoolId(schoolId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Enseignants de l'école récupérés avec succès", dtos));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserSchoolDto>>> getAllUserSchools() {
        List<UserSchoolDto> dtos = userSchoolService.getAll();
        return ResponseEntity.ok(new ApiResponse<>(200, "Relations User-School récupérées avec succès", dtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserSchoolDto>> getUserSchoolById(@PathVariable UUID id) {
        UserSchoolDto dto = userSchoolService.getById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Relation User-School récupérée avec succès", dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUserSchool(@PathVariable UUID id) {
        userSchoolService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Relation User-School supprimée avec succès", null));
    }
}
