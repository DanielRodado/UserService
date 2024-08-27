package com.example.userservice;

import com.example.userservice.models.UserEntity;
import com.example.userservice.repositories.UserEntityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Utils {

    @Bean
    public CommandLineRunner initData(UserEntityRepository userEntityRepository) {

        return args -> {
            /*UserEntity user1 = new UserEntity("Daniel", "daniel@example.com", "12345");
            userEntityRepository.save(user1).subscribe();

            UserEntity user2 = new UserEntity("Maria", "maria@example.com", "12345");
            userEntityRepository.save(user2).subscribe();

            UserEntity user3 = new UserEntity("Pedro", "pedro@example.com", "12345");
            userEntityRepository.save(user3).subscribe();

            UserEntity user4 = new UserEntity("Carlos", "carlos@example.com", "12345");
            userEntityRepository.save(user4).subscribe();*/
        };
    }

}
