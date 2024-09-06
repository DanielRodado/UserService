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
    public Mono<UserEntity> getUserById(Long id) {
        return userEntityRepository.findById(id).switchIfEmpty(Mono.error(new UserNotFoundException(id)));
    }

    @Override
    public Mono<UserEntity> getUserByUsername(String username) {
        return userEntityRepository.findByUsername(username);
    }

    @Override
    public Flux<UserEntity> findAllUsers() {
        return userEntityRepository.findAll();
    }

    @Override
    public Mono<UserEntity> saveUser(UserEntity userEntity) {
        return userEntityRepository.save(userEntity);
    }

    // Repository methods returning a DTO

    @Override
    public Mono<UserEntityDTO> getUserDTOById(Long id) {
        return toUserEntityDTOMono(getUserById(id));
    }

    @Override
    public Flux<UserEntityDTO> findAllUsersDTO() {
        return toUserEntityDTOFlux(findAllUsers());
    }

    // Methods Controller

    @Override
    public Mono<UserEntityDTO> createUser(Mono<UserApplicationDTO> userAppMono) {
        return userAppMono
                .flatMap(this::validate)
                .flatMap(userApplicationDTO -> saveUser(toUserEntity(userApplicationDTO)))
                .map(UserEntityMapper::toUserEntityDTO);
    }

    @Override
    public Mono<UserEntityDTO> updateUser(UserApplicationDTO userApp, Long id) {
        return validate(userApp)
                .then(getUserById(id))
                .flatMap(userEntity -> setProperties(userEntity, userApp))
                .flatMap(this::saveUser)
                .map(UserEntityMapper::toUserEntityDTO);
    }

    @Override
    public Mono<UserEntity> setProperties(UserEntity userEntity, UserApplicationDTO userApp) {
        userEntity.setName(userApp.name());
        userEntity.setEmail(userApp.email());
        userEntity.setPassword(userApp.password());
        return Mono.just(userEntity);
    }

    @Override
    public Mono<Void> deleteUserById(Long id) {
        return getUserById(id).flatMap(userEntityRepository::delete);
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
