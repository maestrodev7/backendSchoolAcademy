package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.ClassFee;
import com.example.school.infrastructure.models.ClassFeeModel;

public final class ClassFeeMapper {

    private ClassFeeMapper() {
    }

    public static ClassFee toDomain(ClassFeeModel model) {
        if (model == null) {
            return null;
        }
        ClassFee classFee = new ClassFee();
        classFee.setId(model.getId());
        classFee.setClassRoom(ClassRoomMapper.toDomain(model.getClassRoom()));
        classFee.setFeeType(FeeTypeMapper.toDomain(model.getFeeType()));
        classFee.setPaymentPlan(PaymentPlanMapper.toDomain(model.getPaymentPlan()));
        classFee.setAmount(model.getAmount());
        classFee.setDueDate(model.getDueDate());
        return classFee;
    }

    public static ClassFeeModel toModel(ClassFee classFee) {
        if (classFee == null) {
            return null;
        }
        ClassFeeModel model = new ClassFeeModel();
        model.setId(classFee.getId());
        model.setClassRoom(ClassRoomMapper.toModel(classFee.getClassRoom()));
        model.setFeeType(FeeTypeMapper.toModel(classFee.getFeeType()));
        model.setPaymentPlan(PaymentPlanMapper.toModel(classFee.getPaymentPlan()));
        model.setAmount(classFee.getAmount());
        model.setDueDate(classFee.getDueDate());
        return model;
    }
}

