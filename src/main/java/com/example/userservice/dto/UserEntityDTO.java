package com.example.userservice.dto;

import com.example.userservice.models.UserEntity;

public class UserEntityDTO {

    private final String name, email;

    public UserEntityDTO(UserEntity userEntity) {
        this.name = userEntity.getName();
        this.email = userEntity.getEmail();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

}
