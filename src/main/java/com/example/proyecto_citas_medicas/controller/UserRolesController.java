package com.example.proyecto_citas_medicas.controller;

import com.example.proyecto_citas_medicas.entities.ApiResponse;
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
public class UserRolesController {

    @PostMapping("/user_roles")
    public ResponseEntity<ApiResponse> get_user_roles(@RequestBody User user) {
        System.out.println("entro aqui en la funcion");
        System.out.println("user: "+user.getRoles());
        try {

            // Respuesta de éxito
            return ResponseEntity.ok(new ApiResponse(true, "Usuario registrado exitosamente", 1));
        } catch (Exception e) {
            System.out.println("entro aqui en el catch");
            // Manejo de excepciones y respuesta de error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error Searching User Roles: " + e.getMessage(), null));
        }
    }
}
