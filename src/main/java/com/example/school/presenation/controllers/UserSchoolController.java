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
