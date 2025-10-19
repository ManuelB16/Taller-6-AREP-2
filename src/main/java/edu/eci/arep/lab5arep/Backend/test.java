package edu.eci.arep.lab5arep.Backend;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class test {
    
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode("123456");

        System.out.println("Contraseña hasheada: " + hashedPassword);
    }

}
