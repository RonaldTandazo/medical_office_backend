package com.example.proyecto_citas_medicas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

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
