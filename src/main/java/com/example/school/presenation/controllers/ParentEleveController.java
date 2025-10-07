package com.example.school.presenation.controllers;

import com.example.school.common.dto.ApiResponse;
import com.example.school.common.dto.ParentEleveDto;
import com.example.school.domain.services.ParentEleveServiceInterface;
import com.example.school.presenation.validators.ParentEleveRequestValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/parent-eleves")
@RequiredArgsConstructor
public class ParentEleveController {

    private final ParentEleveServiceInterface parentEleveService;

    @PostMapping
    public ResponseEntity<ApiResponse<ParentEleveDto>> createRelation(
            @Valid @RequestBody ParentEleveRequestValidator request
    ) {
        ParentEleveDto dto = parentEleveService.create(request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Relation Parent-Élève créée avec succès", dto));
    }
    @GetMapping("/parent/{parentId}/enfants")
    public ResponseEntity<ApiResponse<List<UUID>>> getEnfantsByParent(@PathVariable UUID parentId) {
        List<UUID> enfantsIds = parentEleveService.findEnfantsByParent(parentId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Identifiants des enfants du parent récupérés avec succès", enfantsIds));
    }

    @GetMapping("/eleve/{eleveId}/parents")
    public ResponseEntity<ApiResponse<List<UUID>>> getParentsByEleve(@PathVariable UUID eleveId) {
        List<UUID> parentIds = parentEleveService.findParentsByEleve(eleveId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Identifiants des parents de l'élève récupérés avec succès", parentIds));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ParentEleveDto>> getRelationById(@PathVariable UUID id) {
        ParentEleveDto dto = parentEleveService.getById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Relation Parent-Élève récupérée avec succès", dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRelation(@PathVariable UUID id) {
        parentEleveService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Relation Parent-Élève supprimée avec succès", null));
    }
}
