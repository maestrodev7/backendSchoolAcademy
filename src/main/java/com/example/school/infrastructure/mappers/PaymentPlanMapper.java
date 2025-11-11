package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.PaymentPlan;
import com.example.school.infrastructure.models.PaymentPlanModel;

public final class PaymentPlanMapper {

    private PaymentPlanMapper() {
    }

    public static PaymentPlan toDomain(PaymentPlanModel model) {
        if (model == null) {
            return null;
        }
        PaymentPlan plan = new PaymentPlan();
        plan.setId(model.getId());
        plan.setLabel(model.getLabel());
        plan.setDueDate(model.getDueDate());
        plan.setOrderIndex(model.getOrderIndex());
        plan.setSchool(SchoolMapper.toDomain(model.getSchool()));
        return plan;
    }

    public static PaymentPlanModel toModel(PaymentPlan plan) {
        if (plan == null) {
            return null;
        }
        PaymentPlanModel model = new PaymentPlanModel();
        model.setId(plan.getId());
        model.setLabel(plan.getLabel());
        model.setDueDate(plan.getDueDate());
        model.setOrderIndex(plan.getOrderIndex());
        model.setSchool(SchoolMapper.toModel(plan.getSchool()));
        return model;
    }
}

