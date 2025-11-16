package com.example.school.presenation.controllers;

import com.example.school.common.dto.ApiResponse;
import com.example.school.common.dto.PaymentDto;
import com.example.school.common.dto.StudentRegistrationDto;
import com.example.school.common.dto.PaymentStatusDto;
import com.example.school.domain.services.StudentRegistrationServiceInterface;
import com.example.school.presenation.validators.PaymentRequestValidator;
import com.example.school.presenation.validators.StudentRegistrationRequestValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/registrations")
@RequiredArgsConstructor
public class StudentRegistrationController {

    private final StudentRegistrationServiceInterface studentRegistrationService;

    @PostMapping
    public ResponseEntity<ApiResponse<StudentRegistrationDto>> registerStudent(
            @Valid @RequestBody StudentRegistrationRequestValidator request) {
        StudentRegistrationDto dto = studentRegistrationService.registerStudent(request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Inscription créée avec succès", dto));
    }

    @PostMapping("/{registrationId}/confirm")
    public ResponseEntity<ApiResponse<StudentRegistrationDto>> confirmRegistration(@PathVariable UUID registrationId) {
        StudentRegistrationDto dto = studentRegistrationService.confirmRegistration(registrationId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Inscription confirmée avec succès", dto));
    }

    @GetMapping("/{registrationId}")
    public ResponseEntity<ApiResponse<StudentRegistrationDto>> getRegistration(@PathVariable UUID registrationId) {
        StudentRegistrationDto dto = studentRegistrationService.getRegistrationById(registrationId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Inscription récupérée avec succès", dto));
    }

    @GetMapping("/class/{classRoomId}")
    public ResponseEntity<ApiResponse<List<StudentRegistrationDto>>> getByClass(@PathVariable UUID classRoomId) {
        List<StudentRegistrationDto> data = studentRegistrationService.getRegistrationsByClassRoom(classRoomId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Inscriptions de la classe récupérées avec succès", data));
    }

    @GetMapping("/academic-year/{academicYearId}")
    public ResponseEntity<ApiResponse<List<StudentRegistrationDto>>> getByAcademicYear(@PathVariable UUID academicYearId) {
        List<StudentRegistrationDto> data = studentRegistrationService.getRegistrationsByAcademicYear(academicYearId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Inscriptions de l'année récupérées avec succès", data));
    }

    @GetMapping("/school/{schoolId}")
	public ResponseEntity<ApiResponse<List<StudentRegistrationDto>>> getBySchool(
			@PathVariable UUID schoolId,
			@RequestParam(required = false) UUID classRoomId,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size
	) {
		List<StudentRegistrationDto> data = studentRegistrationService.getRegistrationsBySchool(schoolId, classRoomId, page, size);
		return ResponseEntity.ok(new ApiResponse<>(200, "Inscriptions de l'école récupérées avec succès", data));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponse<List<StudentRegistrationDto>>> getByStudent(@PathVariable UUID studentId) {
        List<StudentRegistrationDto> data = studentRegistrationService.getRegistrationsByStudent(studentId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Inscriptions de l'élève récupérées avec succès", data));
    }

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<ApiResponse<Void>> deleteRegistration(@PathVariable UUID registrationId) {
        studentRegistrationService.deleteRegistration(registrationId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Inscription supprimée avec succès", null));
    }

    @PostMapping("/{registrationId}/payments")
    public ResponseEntity<ApiResponse<PaymentDto>> recordPayment(
            @PathVariable UUID registrationId,
            @Valid @RequestBody PaymentRequestValidator request) {
        PaymentDto dto = studentRegistrationService.recordPayment(registrationId, request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Paiement enregistré avec succès", dto));
    }

    @GetMapping("/{registrationId}/payments")
    public ResponseEntity<ApiResponse<List<PaymentDto>>> getPayments(@PathVariable UUID registrationId) {
        List<PaymentDto> data = studentRegistrationService.getPayments(registrationId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Paiements récupérés avec succès", data));
    }

	@GetMapping("/{registrationId}/payment-status")
	public ResponseEntity<ApiResponse<PaymentStatusDto>> getPaymentStatus(@PathVariable UUID registrationId) {
		PaymentStatusDto dto = studentRegistrationService.getPaymentStatus(registrationId);
		return ResponseEntity.ok(new ApiResponse<>(200, "État des paiements récupéré avec succès", dto));
	}
}

