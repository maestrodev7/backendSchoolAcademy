package com.example.school.infrastructure.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
public class PaymentModel {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "registration_id")
    private StudentRegistrationModel registration;

    @ManyToOne
    @JoinColumn(name = "fee_id")
    private ClassFeeModel classFee;

    private BigDecimal amountPaid;

    private LocalDate paymentDate;
}
