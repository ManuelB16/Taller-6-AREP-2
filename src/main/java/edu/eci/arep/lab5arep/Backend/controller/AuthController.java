package edu.eci.arep.lab5arep.Backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import edu.eci.arep.lab5arep.Backend.model.User;
import edu.eci.arep.lab5arep.Backend.model.LoginRequest;
import edu.eci.arep.lab5arep.Backend.repository.UserRepository;
import edu.eci.arep.lab5arep.Backend.service.UserService;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        User user = userRepository.findByUsername(loginRequest.getUsername());

        if (user != null && userService.auth(loginRequest.getPassword(), user.getPassword())) {
            // Autenticar con Spring Security
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

            // Guardar usuario en la sesión manualmente
            session.setAttribute("user", user.getUsername());

            // Respuesta JSON
            Map<String, String> response = new HashMap<>();
            response.put("message", "Login exitoso");
            response.put("username", user.getUsername());
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Credenciales incorrectas"));
    }

    @GetMapping("/session/check")
    public ResponseEntity<Map<String, String>> checkSession(HttpSession session) {
        String user = (String) session.getAttribute("user");

        if (user != null) {
            return ResponseEntity.ok(Map.of("authenticated", "true", "username", user));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("authenticated", "false"));
        }
    }
}
