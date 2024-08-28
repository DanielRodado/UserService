package com.example.userservice.controllers;

import com.example.userservice.dto.UserApplicationDTO;
import com.example.userservice.dto.UserEntityDTO;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
public class UserEntityController {

    @GetMapping("/{id}")
    public Mono<UserEntityDTO> getUserById(@PathVariable Long id) {
        return null;
    }

    @GetMapping
    public Flux<UserEntityDTO> getAllUsers() {
        return null;
    }

    @PostMapping
    public Mono<UserEntityDTO> createUser(@RequestBody Mono<UserApplicationDTO> userAppMono) {
        return null;
    }

    @PutMapping("/{id}")
    public Mono<UserEntityDTO> updateUser(@PathVariable Long id, @RequestBody Mono<UserApplicationDTO> userAppMono) {
        return null;
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser(@PathVariable Long id) {
        return null;
    }

}
