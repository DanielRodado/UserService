package com.example.userservice.service;

import com.example.userservice.dto.UserApplicationDTO;
import com.example.userservice.dto.UserEntityDTO;
import com.example.userservice.models.UserEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserEntityService {

    // Methods Repository

    Mono<UserEntity> findById(Long id);

    Flux<UserEntity> findAll();

    Mono<UserEntity> save(UserEntity userEntity);

    // Repository methods returning a DTO

    Mono<UserEntityDTO> findByIdDTO(Long id);

    Flux<UserEntityDTO> findAllDTO();

    // Methods Controller

    Mono<UserEntity> create(Mono<UserApplicationDTO> userAppMono);

    Mono<UserEntity> update(Mono<UserApplicationDTO> userAppMono, Long id);

    Mono<Void> updateProperties(Mono<UserApplicationDTO> userAppMono, UserEntity userEntity);

    Mono<Void> deleteById(Long id);

}
