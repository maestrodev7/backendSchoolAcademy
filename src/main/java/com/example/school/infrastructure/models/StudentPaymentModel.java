package com.example.school.infrastructure.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "student_payments")
@Data
@NoArgsConstructor
public class StudentPaymentModel {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "enrollment_id")
    private EnrollmentModel enrollment;

    @ManyToOne
    @JoinColumn(name = "class_fee_id")
    private ClassFeeModel classFee;

    private BigDecimal amountPaid;
    private LocalDate paymentDate;
}
