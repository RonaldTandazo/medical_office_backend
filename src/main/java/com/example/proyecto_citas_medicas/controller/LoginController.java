package com.example.proyecto_citas_medicas.controller;

import com.example.proyecto_citas_medicas.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        // Método para manejar la visualización del formulario de login
        return "login";
    }

    @PostMapping("/login")
    public String authenticate(String username, String password) {
        // Aquí puedes usar passwordEncoder para comparar contraseñas
        return "home";
    }
}
