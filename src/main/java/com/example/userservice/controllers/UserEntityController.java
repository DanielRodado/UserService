package com.example.userservice.controllers;

import com.example.userservice.dto.UserApplicationDTO;
import com.example.userservice.dto.UserEntityDTO;
import com.example.userservice.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
public class UserEntityController {

    @Autowired
    private UserEntityService userEntityService;

    @GetMapping("/{id}")
    public Mono<UserEntityDTO> getUserById(@PathVariable Long id) {
        return userEntityService.findByIdDTO(id);
    }

    @GetMapping
    public Flux<UserEntityDTO> getAllUsers() {
        return userEntityService.findAllDTO();
    }

    @PostMapping
    public Mono<UserEntityDTO> createUser(@RequestBody Mono<UserApplicationDTO> userAppMono) {
        return userEntityService.create(userAppMono);
    }

    @PutMapping("/{id}")
    public Mono<UserEntityDTO> updateUser(@PathVariable Long id, @RequestBody UserApplicationDTO userApp) {
        return userEntityService.update(userApp, id);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteUser(@PathVariable Long id) {
        return userEntityService.deleteById(id).then(Mono.just(ResponseEntity.noContent().build()));
    }

}
