package com.example.userservice.service;

import com.example.userservice.dto.LoginUser;
import com.example.userservice.dto.UserApplicationDTO;
import com.example.userservice.dto.UserEntityDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public interface AuthService {

    Mono<UserEntityDTO> createUser(Mono<UserApplicationDTO> userAppMono);

    Mono<ResponseEntity<String>> login(LoginUser loginUser);

    Mono<Authentication> getCurrentUser(LoginUser loginUser);

    UsernamePasswordAuthenticationToken authenticateUser(LoginUser loginUser);

    Mono<UserDetails> getUserDetails(Authentication authentication);

    Mono<String> generateJwtToken(String username);

}
