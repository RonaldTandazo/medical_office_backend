package com.example.proyecto_citas_medicas.controller;

import com.example.proyecto_citas_medicas.entities.ApiResponse;
import com.example.proyecto_citas_medicas.entities.Paciente;
import com.example.proyecto_citas_medicas.entities.User;
import com.example.proyecto_citas_medicas.service.CustomUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    private final CustomUserDetailsService customUserDetailsService;

    public UserController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody User user) {
        System.out.println("entro aqui en la funcion");
        try {
            System.out.println("entro aqui en el try");
            // Llamada al servicio para guardar el usuario
            User savedUser = customUserDetailsService.saveUser(user);
            System.out.println(savedUser);
            // Respuesta de éxito
            return ResponseEntity.ok(new ApiResponse(true, "Usuario registrado exitosamente", savedUser));
        } catch (Exception e) {
            System.out.println("entro aqui en el catch");
            // Manejo de excepciones y respuesta de error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error al registrar el usuario: " + e.getMessage(), null));
        }
    }
}
