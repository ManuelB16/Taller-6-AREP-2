package edu.eci.arep.lab5arep.Backend.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.eci.arep.lab5arep.Backend.model.User;
import edu.eci.arep.lab5arep.Backend.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
    
    
    private final PasswordEncoder passwordEncoder; 

    @Autowired
    private UserRepository userRepository;

    @Autowired
public UserService(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
}

public boolean auth(String rawPassword, String hashedPassword) {
    System.out.println("Contraseña ingresada: " + rawPassword);
    System.out.println("Contraseña en BD: " + hashedPassword);
    
    boolean match = passwordEncoder.matches(rawPassword, hashedPassword);
    System.out.println("¿Coinciden? " + match);
    
    return match;
}



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.emptyList()  // Aquí podrías asignar roles si los tuvieras
        );
    }
}

