package com.example.userservice.repositories;

import com.example.userservice.models.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserEntityRepository extends CrudRepository<UserEntity, Long> {
}
