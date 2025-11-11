package com.example.school.infrastructure.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "school_fee_configs")
@Data
@NoArgsConstructor
public class SchoolFeeConfigModel {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private SchoolModel school;

    @ManyToOne
    @JoinColumn(name = "class_room_id")
    private ClassRoomModel classRoom;

    @ManyToOne
    @JoinColumn(name = "school_fee_type_id")
    private SchoolFeeTypeModel schoolFeeType;

    private BigDecimal amount;

    private int numberOfInstallments;
}
