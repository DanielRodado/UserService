package com.example.userservice.service;

import com.example.userservice.dto.UserApplicationDTO;
import com.example.userservice.dto.UserEntityDTO;
import com.example.userservice.models.UserEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserEntityService {

    // Methods Repository

    Mono<UserEntity> getUserById(Long id);

    Flux<UserEntity> findAllUsers();

    Mono<UserEntity> saveUser(UserEntity userEntity);

    // Repository methods returning a DTO

    Mono<UserEntityDTO> getUserDTOById(Long id);

    Flux<UserEntityDTO> findAllUsersDTO();

    // Methods Controller

    Mono<UserEntityDTO> createUser(Mono<UserApplicationDTO> userAppMono);

    Mono<UserEntityDTO> updateUser(UserApplicationDTO userApp, Long id);

    Mono<UserEntity> setProperties(UserEntity userEntity, UserApplicationDTO userApp);

    Mono<Void> deleteUserById(Long id);

    // Validations

    Mono<UserApplicationDTO> validate(UserApplicationDTO userApp);

}
