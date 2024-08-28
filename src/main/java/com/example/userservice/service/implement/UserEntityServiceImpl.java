package com.example.userservice.service.implement;

import com.example.userservice.dto.UserApplicationDTO;
import com.example.userservice.dto.UserEntityDTO;
import com.example.userservice.exceptions.UserNotFoundException;
import com.example.userservice.models.UserEntity;
import com.example.userservice.repositories.UserEntityRepository;
import com.example.userservice.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.example.userservice.mappers.UserEntityMapper.*;

@Service
public class UserEntityServiceImpl implements UserEntityService {

    // Methods Repository

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Override
    public Mono<UserEntity> findById(Long id) {
        return userEntityRepository.findById(id).switchIfEmpty(Mono.error(new UserNotFoundException(id)));
    }

    @Override
    public Flux<UserEntity> findAll() {
        return userEntityRepository.findAll();
    }

    @Override
    public Mono<UserEntity> save(UserEntity userEntity) {
        return userEntityRepository.save(userEntity);
    }

    // Repository methods returning a DTO

    @Override
    public Mono<UserEntityDTO> findByIdDTO(Long id) {
        return toUserEntityDTOMono(findById(id));
    }

    @Override
    public Flux<UserEntityDTO> findAllDTO() {
        return toUserEntityDTOFlux(findAll());
    }

    // Methods Controller

    @Override
    public Mono<UserEntity> create(Mono<UserApplicationDTO> userAppMono) {
        return toUserEntityMono(userAppMono).flatMap(this::save);
    }

    @Override
    public Mono<UserEntity> update(Mono<UserApplicationDTO> userAppMono, Long id) {
        return findById(id).flatMap(userEntity -> {
            updateProperties(userAppMono, userEntity);
            return save(userEntity);
        });
    }

    @Override
    public Mono<Void> updateProperties(Mono<UserApplicationDTO> userAppMono, UserEntity userEntity) {
        return userAppMono.flatMap(userAppMonoParam -> {
            userEntity.setName(userAppMonoParam.name());
            userEntity.setEmail(userAppMonoParam.email());
            userEntity.setPassword(userAppMonoParam.password());
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return findById(id).flatMap(userEntityRepository::delete);
    }
}
