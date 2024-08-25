package com.example.proyecto_citas_medicas.controller;

import com.example.proyecto_citas_medicas.entities.ApiResponse;
import com.example.proyecto_citas_medicas.entities.Role;
import com.example.proyecto_citas_medicas.entities.User;
import com.example.proyecto_citas_medicas.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserRolesController {

    private static final Logger logger = LoggerFactory.getLogger(UserRolesController.class);
    private CustomUserDetailsService userService;

    public UserRolesController(CustomUserDetailsService customUserDetailsService) {
        this.userService = customUserDetailsService;
    }

    @PostMapping("/user_roles")
    public ResponseEntity<ApiResponse> get_user_roles(@RequestBody User user) {
        try {
            Optional<User> verifyUser = userService.getUserByEmail(user.getEmail());

            if(!verifyUser.isPresent()){
                return ResponseEntity.ok(new ApiResponse(true, "User Not Found", null, 303));
            }

            List<Role.RoleProjection> userRoles = userService.getUserRoles(verifyUser.get().getId());
            // Comprobar si la lista está vacía
            if (userRoles.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse(true, "No roles found for user", null, 303));
            }

            // Respuesta de éxito
            return ResponseEntity.ok(new ApiResponse(true, "User Found!!!", userRoles, 200));
        } catch (Exception e) {
            String className = this.getClass().getName();
            String methodName = new Throwable().getStackTrace()[0].getMethodName();
            logger.error("ERROR:"+className + ":" + methodName+" -> "+e.getMessage());            // Manejo de excepciones y respuesta de error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error Searching User Roles: " + e.getMessage(), null, 404));
        }
    }
}
