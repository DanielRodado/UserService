package com.example.userservice.service.implement;

import com.example.userservice.configurations.JwtUtils;
import com.example.userservice.dto.LoginUser;
import com.example.userservice.dto.UserApplicationDTO;
import com.example.userservice.dto.UserEntityDTO;
import com.example.userservice.service.AuthService;
import com.example.userservice.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private ReactiveAuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserEntityService userEntityService;

    @Override
    public Mono<UserEntityDTO> createUser(Mono<UserApplicationDTO> userAppMono) {
        return userEntityService.createUser(userAppMono);
    }

    @Override
    public Mono<ResponseEntity<String>> login(LoginUser loginUser) {
        return generateCurrentUser(loginUser)
                .flatMap(this::getUserDetails)
                .flatMap(userDetails -> generateJwtToken(userDetails.getUsername()))
                .map(token -> ResponseEntity.ok().body(token))
                .onErrorResume(err -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    @Override
    public Mono<Authentication> generateCurrentUser(LoginUser loginUser) {
        return authenticationManager.authenticate(authenticateUser(loginUser));
    }

    @Override
    public UsernamePasswordAuthenticationToken authenticateUser(LoginUser loginUser) {
        return new UsernamePasswordAuthenticationToken(loginUser.username(), loginUser.password());
    }

    @Override
    public Mono<UserDetails> getUserDetails(Authentication authentication) {
        return Mono.just((UserDetails) authentication.getPrincipal());
    }

    @Override
    public Mono<String> generateJwtToken(String username) {
        return Mono.just(jwtUtils.generateToken(username));
    }


}
