package com.example.school.application.services;

import com.example.school.common.dto.PaymentDto;
import com.example.school.common.dto.PaymentStatusDto;
import com.example.school.common.dto.PaymentStatusItemDto;
import com.example.school.common.dto.StudentRegistrationDto;
import com.example.school.common.exceptions.BusinessRuleException;
import com.example.school.common.exceptions.ResourceAlreadyExistsException;
import com.example.school.common.mapper.PaymentDtoMapper;
import com.example.school.common.mapper.StudentRegistrationMapper;
import com.example.school.domain.entities.ClassFee;
import com.example.school.domain.entities.Payment;
import com.example.school.domain.entities.StudentRegistration;
import com.example.school.domain.repositories.ClassFeeRepositoryInterface;
import com.example.school.domain.repositories.PaymentRepositoryInterface;
import com.example.school.domain.repositories.StudentRegistrationRepositoryInterface;
import com.example.school.domain.repositories.UserRepositoryInterface;
import com.example.school.domain.repositories.ClassRoomRepositoryInterface;
import com.example.school.domain.repositories.AcademicYearRepositoryInterface;
import com.example.school.domain.services.StudentRegistrationServiceInterface;
import com.example.school.presenation.validators.PaymentRequestValidator;
import com.example.school.presenation.validators.StudentRegistrationRequestValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentRegistrationService implements StudentRegistrationServiceInterface {

    private final StudentRegistrationRepositoryInterface studentRegistrationRepository;
    private final PaymentRepositoryInterface paymentRepository;
    private final ClassFeeRepositoryInterface classFeeRepository;
    private final ClassRoomRepositoryInterface classRoomRepository;
    private final UserRepositoryInterface userRepository;
    private final AcademicYearRepositoryInterface academicYearRepository;

    @Override
    public StudentRegistrationDto registerStudent(StudentRegistrationRequestValidator request) {
        UUID studentId = request.getStudentId();
        UUID academicYearId = request.getAcademicYearId();

        studentRegistrationRepository.findByStudentAndAcademicYear(studentId, academicYearId)
                .ifPresent(registration -> {
                    throw new ResourceAlreadyExistsException("L'élève est déjà inscrit pour cette année académique");
                });

        var student = userRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Élève introuvable"));

        var classRoom = classRoomRepository.findById(request.getClassRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Classe introuvable"));

        var academicYear = academicYearRepository.findById(academicYearId)
                .orElseThrow(() -> new EntityNotFoundException("Année académique introuvable"));

        StudentRegistration registration = new StudentRegistration();
        registration.setStudent(student);
        registration.setClassRoom(classRoom);
        registration.setAcademicYear(academicYear);
        registration.setConfirmed(false);

        StudentRegistration saved = studentRegistrationRepository.save(registration);
        return StudentRegistrationMapper.toDto(saved);
    }

    @Override
    public StudentRegistrationDto confirmRegistration(UUID registrationId) {
        StudentRegistration registration = studentRegistrationRepository.findById(registrationId)
                .orElseThrow(() -> new EntityNotFoundException("Inscription introuvable"));
        if (!registration.isConfirmed()) {
            registration.setConfirmed(true);
            registration = studentRegistrationRepository.save(registration);
        }
        return StudentRegistrationMapper.toDto(registration);
    }

    @Override
    public StudentRegistrationDto getRegistrationById(UUID registrationId) {
        return studentRegistrationRepository.findById(registrationId)
                .map(StudentRegistrationMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Inscription introuvable"));
    }

    @Override
    public List<StudentRegistrationDto> getRegistrationsByClassRoom(UUID classRoomId) {
        return studentRegistrationRepository.findByClassRoom(classRoomId)
                .stream()
                .map(StudentRegistrationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentRegistrationDto> getRegistrationsByAcademicYear(UUID academicYearId) {
        return studentRegistrationRepository.findByAcademicYear(academicYearId)
                .stream()
                .map(StudentRegistrationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentRegistrationDto> getRegistrationsBySchool(UUID schoolId) {
        return studentRegistrationRepository.findBySchool(schoolId)
                .stream()
                .map(StudentRegistrationMapper::toDto)
                .collect(Collectors.toList());
    }

	@Override
	public List<StudentRegistrationDto> getRegistrationsBySchool(UUID schoolId, UUID classRoomId, int page, int size) {
		if (page < 0) page = 0;
		if (size <= 0) size = 20;

		List<StudentRegistration> source = (classRoomId != null)
				? studentRegistrationRepository.findByClassRoom(classRoomId)
				: studentRegistrationRepository.findBySchool(schoolId);

		int fromIndex = Math.min(page * size, source.size());
		int toIndex = Math.min(fromIndex + size, source.size());

		return source.subList(fromIndex, toIndex)
				.stream()
				.map(StudentRegistrationMapper::toDto)
				.collect(Collectors.toList());
	}

    @Override
    public List<StudentRegistrationDto> getRegistrationsByStudent(UUID studentId) {
        return studentRegistrationRepository.findByStudent(studentId)
                .stream()
                .map(StudentRegistrationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRegistration(UUID registrationId) {
        studentRegistrationRepository.deleteById(registrationId);
    }

    @Override
    public PaymentDto recordPayment(UUID registrationId, PaymentRequestValidator request) {
        StudentRegistration registration = studentRegistrationRepository.findById(registrationId)
                .orElseThrow(() -> new EntityNotFoundException("Inscription introuvable"));

        ClassFee classFee = classFeeRepository.findById(request.getClassFeeId())
                .orElseThrow(() -> new EntityNotFoundException("Tranche ou frais introuvable"));

        validatePaymentAmount(registration, classFee, request.getAmountPaid(), request.getClassFeeId());

        Payment payment = new Payment();
        payment.setRegistration(registration);
        payment.setClassFee(classFee);
        payment.setAmountPaid(request.getAmountPaid());
        payment.setPaymentDate(request.getPaymentDate() != null ? request.getPaymentDate() : LocalDate.now());

        Payment saved = paymentRepository.save(payment);

        refreshRegistrationStatus(registration);

        return PaymentDtoMapper.toDto(saved);
    }

    @Override
    public List<PaymentDto> getPayments(UUID registrationId) {
        studentRegistrationRepository.findById(registrationId)
                .orElseThrow(() -> new EntityNotFoundException("Inscription introuvable"));

        return paymentRepository.findByRegistration(registrationId)
                .stream()
                .map(PaymentDtoMapper::toDto)
                .collect(Collectors.toList());
    }

	@Override
	public PaymentStatusDto getPaymentStatus(UUID registrationId) {
		StudentRegistration registration = studentRegistrationRepository.findById(registrationId)
				.orElseThrow(() -> new EntityNotFoundException("Inscription introuvable"));

		UUID classRoomId = registration.getClassRoom().getId();

		List<ClassFee> classFees = classFeeRepository.findByClassRoom(classRoomId);
		List<Payment> payments = paymentRepository.findByRegistration(registrationId);

		// Group payments by classFeeId and sum
		Map<UUID, BigDecimal> paidByFee = payments.stream()
				.filter(p -> p.getClassFee() != null && p.getClassFee().getId() != null)
				.collect(Collectors.groupingBy(p -> p.getClassFee().getId(),
						Collectors.mapping(Payment::getAmountPaid,
								Collectors.reducing(BigDecimal.ZERO, (a, b) -> (a != null ? a : BigDecimal.ZERO).add(b != null ? b : BigDecimal.ZERO)))));

		// Index fees by id from class repository
		Map<UUID, ClassFee> feeById = classFees.stream()
				.filter(f -> f.getId() != null)
				.collect(Collectors.toMap(ClassFee::getId, f -> f, (a, b) -> a));

		// Ensure any fee referenced by a payment is represented even if not returned by class fees
		payments.stream()
				.map(Payment::getClassFee)
				.filter(f -> f != null && f.getId() != null)
				.forEach(f -> feeById.putIfAbsent(f.getId(), f));

		List<PaymentStatusItemDto> items = feeById.values().stream().map(fee -> {
			BigDecimal expected = fee.getAmount() != null ? fee.getAmount() : BigDecimal.ZERO;
			BigDecimal paid = paidByFee.getOrDefault(fee.getId(), BigDecimal.ZERO);
			BigDecimal remaining = expected.subtract(paid);
			if (remaining.compareTo(BigDecimal.ZERO) < 0) {
				remaining = BigDecimal.ZERO;
			}

			PaymentStatusItemDto item = new PaymentStatusItemDto();
			item.setClassFeeId(fee.getId());
			item.setFeeName(fee.getFeeType() != null ? fee.getFeeType().getName() : null);
			item.setTrancheLabel(fee.getPaymentPlan() != null ? fee.getPaymentPlan().getLabel() : null);
			item.setTrancheOrder(fee.getPaymentPlan() != null ? fee.getPaymentPlan().getOrderIndex() : null);
			item.setExpectedAmount(expected);
			item.setPaidAmount(paid);
			item.setRemainingAmount(remaining);
			item.setFullyPaid(expected.compareTo(BigDecimal.ZERO) == 0 || paid.compareTo(expected) >= 0);
			return item;
		}).sorted((a, b) -> {
			Integer ao = a.getTrancheOrder() != null ? a.getTrancheOrder() : Integer.MAX_VALUE;
			Integer bo = b.getTrancheOrder() != null ? b.getTrancheOrder() : Integer.MAX_VALUE;
			return Integer.compare(ao, bo);
		}).collect(Collectors.toList());

		BigDecimal totalExpected = items.stream().map(PaymentStatusItemDto::getExpectedAmount)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal totalPaid = items.stream().map(PaymentStatusItemDto::getPaidAmount)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal totalRemaining = items.stream().map(PaymentStatusItemDto::getRemainingAmount)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		PaymentStatusDto dto = new PaymentStatusDto();
		dto.setRegistrationId(registrationId);
		dto.setConfirmed(registration.isConfirmed());
		dto.setTotalExpected(totalExpected);
		dto.setTotalPaid(totalPaid);
		dto.setTotalRemaining(totalRemaining);
		dto.setItems(items);
		return dto;
	}

    private void validatePaymentAmount(StudentRegistration registration,
                                       ClassFee classFee,
                                       BigDecimal amountPaid,
                                       UUID classFeeId) {
        if (amountPaid == null || amountPaid.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessRuleException("Le montant payé doit être supérieur à zéro");
        }

        if (classFee.getAmount() == null) {
            return;
        }

        BigDecimal totalAlreadyPaid = paymentRepository.findByRegistration(registration.getId())
                .stream()
                .filter(payment -> payment.getClassFee() != null && classFeeId.equals(payment.getClassFee().getId()))
                .map(Payment::getAmountPaid)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal newTotal = totalAlreadyPaid.add(amountPaid);

        if (newTotal.compareTo(classFee.getAmount()) > 0) {
            throw new BusinessRuleException("Le montant cumulé dépasse le montant attendu pour cette tranche");
        }
    }

    private void refreshRegistrationStatus(StudentRegistration registration) {
        List<ClassFee> classFees = classFeeRepository.findByClassRoom(registration.getClassRoom().getId());

        if (classFees.isEmpty()) {
            return;
        }

        List<ClassFee> mandatoryFees = classFees.stream()
                .filter(fee -> fee.getFeeType() != null && fee.getFeeType().isMandatory())
                .collect(Collectors.toList());

        if (mandatoryFees.isEmpty()) {
            return;
        }

        List<Payment> payments = paymentRepository.findByRegistration(registration.getId());

        boolean fullyPaid = mandatoryFees.stream().allMatch(fee -> {
            BigDecimal expectedAmount = fee.getAmount() != null ? fee.getAmount() : BigDecimal.ZERO;
            BigDecimal paidForFee = payments.stream()
                    .filter(payment -> payment.getClassFee() != null && fee.getId().equals(payment.getClassFee().getId()))
                    .map(Payment::getAmountPaid)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            return expectedAmount.compareTo(BigDecimal.ZERO) == 0 || paidForFee.compareTo(expectedAmount) >= 0;
        });

        boolean newStatus = fullyPaid;
        if (registration.isConfirmed() != newStatus) {
            registration.setConfirmed(newStatus);
            studentRegistrationRepository.save(registration);
        }
    }
}

