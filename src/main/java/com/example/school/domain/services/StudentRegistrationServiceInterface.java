package com.example.school.domain.services;

import com.example.school.common.dto.PaymentDto;
import com.example.school.common.dto.StudentRegistrationDto;
import com.example.school.presenation.validators.PaymentRequestValidator;
import com.example.school.presenation.validators.StudentRegistrationRequestValidator;

import java.util.List;
import java.util.UUID;

public interface StudentRegistrationServiceInterface {
    StudentRegistrationDto registerStudent(StudentRegistrationRequestValidator request);

    StudentRegistrationDto confirmRegistration(UUID registrationId);

    StudentRegistrationDto getRegistrationById(UUID registrationId);

    List<StudentRegistrationDto> getRegistrationsByClassRoom(UUID classRoomId);

    List<StudentRegistrationDto> getRegistrationsByAcademicYear(UUID academicYearId);

    List<StudentRegistrationDto> getRegistrationsBySchool(UUID schoolId);

    List<StudentRegistrationDto> getRegistrationsByStudent(UUID studentId);

    void deleteRegistration(UUID registrationId);

    PaymentDto recordPayment(UUID registrationId, PaymentRequestValidator request);

    List<PaymentDto> getPayments(UUID registrationId);
}

