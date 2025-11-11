package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.Payment;
import com.example.school.infrastructure.models.PaymentModel;

public final class PaymentMapper {

    private PaymentMapper() {
    }

    public static Payment toDomain(PaymentModel model) {
        if (model == null) {
            return null;
        }
        Payment payment = new Payment();
        payment.setId(model.getId());
        payment.setRegistration(StudentRegistrationMapper.toDomain(model.getRegistration()));
        payment.setClassFee(ClassFeeMapper.toDomain(model.getClassFee()));
        payment.setAmountPaid(model.getAmountPaid());
        payment.setPaymentDate(model.getPaymentDate());
        return payment;
    }

    public static PaymentModel toModel(Payment payment) {
        if (payment == null) {
            return null;
        }
        PaymentModel model = new PaymentModel();
        model.setId(payment.getId());
        model.setRegistration(StudentRegistrationMapper.toModel(payment.getRegistration()));
        model.setClassFee(ClassFeeMapper.toModel(payment.getClassFee()));
        model.setAmountPaid(payment.getAmountPaid());
        model.setPaymentDate(payment.getPaymentDate());
        return model;
    }
}
