package com.example.userservice;

import com.example.userservice.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Utils {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData(UserEntityRepository userEntityRepository) {

        return args -> {
            /*UserEntity user1 = new UserEntity("Daniel", "daniel14","daniel@example.com", passwordEncoder.encode(
                    "12345"), true);
            userEntityRepository.save(user1).subscribe();

            UserEntity user2 = new UserEntity("Stephany", "Steph23","steph@example.com", passwordEncoder.encode(
                    "12345"), true);
            userEntityRepository.save(user2).subscribe();

            UserEntity user3 = new UserEntity("Pedro", "prezzz12","pedro@example.com", passwordEncoder.encode(
                    "12345"));
            userEntityRepository.save(user3).subscribe();

            UserEntity user4 = new UserEntity("Carlos", "carlo21","carlos@example.com", passwordEncoder.encode(
                    "12345"));
            userEntityRepository.save(user4).subscribe();*/
        };
    }

}
