package edu.eci.arep.lab5arep.Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories
public class Server {
    public static void main(String[] args){
        SpringApplication.run(Server.class, args);
    }
    
}
