package com.example.userservice.validations;

import com.example.userservice.dto.UserApplicationDTO;
import com.example.userservice.models.UserEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserEntityValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserApplicationDTO userApp = (UserApplicationDTO) target;

        isValidName(userApp.name(), errors);
        isValidUserName(userApp.username(), errors);
        isValidEmail(userApp.email(), errors);
        isValidPassword(userApp.password(), errors);
    }

    public void isValidName(String name, Errors errors) {
        if (name == null || name.isBlank()) {
            errors.rejectValue("name", "user.name.empty", "User name cannot be empty.");
        }
    }

    public void isValidUserName(String username, Errors errors) {
        if (username == null || username.isBlank()) {
            errors.rejectValue("username", "user.username.empty", "Username cannot be empty.");
        }
    }

    public void isValidEmail(String email, Errors errors) {
        if (email == null || email.isBlank()) {
            errors.rejectValue("email", "user.email.empty", "User email cannot be empty.");
        }
    }

    public void isValidPassword(String password, Errors errors) {
        if (password == null || password.isBlank()) {
            errors.rejectValue("password", "user.password.empty", "User password cannot be empty.");
        }
    }


}
