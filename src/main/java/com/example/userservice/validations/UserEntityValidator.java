package com.example.userservice.validations;

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
        UserEntity userEntity = (UserEntity) target;

        isValidName(userEntity.getName(), errors);
        isValidEmail(userEntity.getEmail(), errors);
        isValidPassword(userEntity.getPassword(), errors);
    }

    public void isValidName(String name, Errors errors) {
        if (name == null || name.isBlank()) {
            errors.rejectValue("name", "user.name.empty", "User name cannot be empty.");
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
