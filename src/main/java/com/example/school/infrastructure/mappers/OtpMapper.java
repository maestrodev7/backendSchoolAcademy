package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.Otp;
import com.example.school.infrastructure.models.OtpModel;

public class OtpMapper {
    public static Otp toDomain(OtpModel model) {
        if (model == null) return null;
        Otp otp = new Otp();
        otp.setId(model.getId());
        otp.setUserId(model.getUserId());
        otp.setCode(model.getCode());
        otp.setExpiresAt(model.getExpiresAt());
        otp.setVerified(model.isVerified());
        otp.setVerifiedAt(model.getVerifiedAt());
        otp.setPurpose(model.getPurpose());
        return otp;
    }

    public static OtpModel toModel(Otp otp) {
        if (otp == null) return null;
        OtpModel model = new OtpModel();
        model.setId(otp.getId());
        model.setUserId(otp.getUserId());
        model.setCode(otp.getCode());
        model.setExpiresAt(otp.getExpiresAt());
        model.setVerified(otp.isVerified());
        model.setVerifiedAt(otp.getVerifiedAt());
        model.setPurpose(otp.getPurpose());
        return model;
    }
}

