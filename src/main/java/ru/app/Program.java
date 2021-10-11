package ru.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.app.models.Role;
import ru.app.repositories.RoleRepository;

import java.util.List;

@SpringBootApplication
public class Program {
    public static void main(String[] args) {
        SpringApplication.run(Program.class, args);
    }

    @Bean
    public CommandLineRunner demoData(RoleRepository roleRepository) {
        if (roleRepository.findAll().size() == 0) {
            return args -> roleRepository.saveAll(List.of(new Role(1L, "ROLE_ADMIN"),
                    new Role(2L, "ROLE_USER")));
        }
        return args -> {
        };
    }
}
