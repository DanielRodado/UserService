package com.example.userservice.service.implement;

import com.example.userservice.dto.UserApplicationDTO;
import com.example.userservice.dto.UserEntityDTO;
import com.example.userservice.exceptions.InvalidUserException;
import com.example.userservice.exceptions.UserNotFoundException;
import com.example.userservice.mappers.UserEntityMapper;
import com.example.userservice.models.UserEntity;
import com.example.userservice.repositories.UserEntityRepository;
import com.example.userservice.service.UserEntityService;
import com.example.userservice.validations.UserEntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.example.userservice.mappers.UserEntityMapper.*;

@Service
public class UserEntityServiceImpl implements UserEntityService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private UserEntityValidator userEntityValidator;

    // Methods Repository

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
        return userAppMono
                .flatMap(this::validate)
                .flatMap(userApplicationDTO -> save(toUserEntity(userApplicationDTO)))
                .map(UserEntityMapper::toUserEntityDTO);
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

    // Validation

    @Override
    public Mono<UserApplicationDTO> validate(UserApplicationDTO userApp) {
        Errors errors = new BeanPropertyBindingResult(userApp, "user");
        userEntityValidator.validate(userApp, errors);

        if (errors.hasErrors()) {
            return Mono.error(new InvalidUserException(errors.getFieldError().getDefaultMessage()));
        }

        return Mono.just(userApp);
    }
}
