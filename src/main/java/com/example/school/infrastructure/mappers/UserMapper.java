package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.User;
import com.example.school.infrastructure.models.UserModel;

public class UserMapper {
    public static User toDomain(UserModel model) {
        if (model == null) return null;
        User user = new User();
        user.setId(model.getId());
        user.setUsername(model.getUsername());
        user.setFirstName(model.getFirstName());
        user.setLastName(model.getLastName());
        user.setEmail(model.getEmail());
        user.setPassword(model.getPassword());
        user.setPhoneNumber(model.getPhoneNumber());
        user.setPasswordChanged(model.isPasswordChanged());
        user.setRoles(model.getRoles());
        return user;
    }

    public static UserModel toModel(User user) {
        if (user == null) return null;
        UserModel model = new UserModel();
        model.setId(user.getId());
        model.setUsername(user.getUsername());
        model.setFirstName(user.getFirstName());
        model.setLastName(user.getLastName());
        model.setEmail(user.getEmail());
        model.setPassword(user.getPassword());
        model.setPhoneNumber(user.getPhoneNumber());
        model.setPasswordChanged(user.isPasswordChanged());
        model.setRoles(user.getRoles());
        return model;
    }
}
