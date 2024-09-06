package com.example.userservice.configurations;

import com.example.userservice.exceptions.UserNotFoundException;
import com.example.userservice.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomUserDetailsService implements ReactiveUserDetailsService {

    @Autowired
    private UserEntityService userEntityService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userEntityService.getUserByUsername(username)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found with username \"" + username + "\".")))
                .map(userEntity -> new User(userEntity.getUsername(), userEntity.getPassword(),
                        AuthorityUtils.createAuthorityList(userEntity.isAdmin() ? "ADMIN" : "USER")));
    }
}
