package com.example.userservice.dto;

import com.example.userservice.models.UserEntity;

public class UserEntityDTO {

    private final Long id;
    private final String name, username, email;

    public UserEntityDTO(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.name = userEntity.getName();
        this.username = userEntity.getUsername();
        this.email = userEntity.getEmail();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

}
