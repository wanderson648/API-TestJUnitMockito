package br.wo.api.config;

import br.wo.api.domain.User;
import br.wo.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository repository;

    @Bean
    public List<User> startDB() {
        User u1 = new User(null, "Wanderson", "wander@gmail.com", "12345");
        User u2 = new User(null, "Mateus", "mateus@gmail.com", "12345");
        User u3 = new User(null, "Tatiele", "tatiele@gmail.com", "12345");

       return repository.saveAll(List.of(u1, u2, u3));
    }


}
