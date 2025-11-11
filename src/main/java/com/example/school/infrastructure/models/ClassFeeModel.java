package com.example.school.infrastructure.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "class_fees")
@Data
@NoArgsConstructor
public class ClassFeeModel {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "class_room_id")
    private ClassRoomModel classRoom;

    @ManyToOne
    @JoinColumn(name = "fee_type_id")
    private FeeTypeModel feeType;

    @ManyToOne
    @JoinColumn(name = "payment_plan_id", nullable = true)
    private PaymentPlanModel paymentPlan; // peut être null pour les frais non fractionnés (ex : inscription)

    private BigDecimal amount;
    private LocalDate dueDate;
}
