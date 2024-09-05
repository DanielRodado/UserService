package com.example.userservice.mappers;

import com.example.userservice.dto.UserApplicationDTO;
import com.example.userservice.dto.UserEntityDTO;
import com.example.userservice.models.UserEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UserEntityMapper {

    // To DTO

    public static UserEntityDTO toUserEntityDTO(UserEntity userEntity) {
        return new UserEntityDTO(userEntity);
    }

    public static Mono<UserEntityDTO> toUserEntityDTOMono(Mono<UserEntity> userEntityMono) {
        return userEntityMono.map(UserEntityDTO::new);
    }

    public static Flux<UserEntityDTO> toUserEntityDTOFlux(Flux<UserEntity> userEntityFlux) {
        return userEntityFlux.map(UserEntityDTO::new);
    }

    // To Entity

    public static UserEntity toUserEntity(UserApplicationDTO userApp) {
        return new UserEntity(userApp.name(), userApp.username(), userApp.email(), userApp.password());
    }

}
