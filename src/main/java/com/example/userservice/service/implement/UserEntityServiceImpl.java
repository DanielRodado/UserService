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
    public Mono<UserEntityDTO> create(Mono<UserApplicationDTO> userAppMono) {
        return toUserEntityMono(userAppMono).flatMap(userEntity -> save(userEntity).flatMap(userEntitySaved -> toUserEntityDTOMono(Mono.just(userEntitySaved))));
    }

    @Override
    public Mono<UserEntityDTO> update(UserApplicationDTO userApp, Long id) {
        return findById(id).flatMap(userEntity -> {
            userEntity.setName(userApp.name());
            userEntity.setEmail(userApp.email());
            userEntity.setPassword(userApp.password());
            return save(userEntity).then(toUserEntityDTOMono(Mono.just(userEntity)));
        });
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return findById(id).flatMap(userEntityRepository::delete);
    }
}
