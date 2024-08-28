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

    public static UserEntity toUserEntity(UserApplicationDTO userApplicationDTO) {
        return new UserEntity(userApplicationDTO.name(), userApplicationDTO.email(), userApplicationDTO.password());
    }

    public static Mono<UserEntity> toUserEntityMono(Mono<UserApplicationDTO> userAppMono) {
        return userAppMono.map(userApplicationDTO -> new UserEntity(userApplicationDTO.name(), userApplicationDTO.email(), userApplicationDTO.password()));
    }

}
