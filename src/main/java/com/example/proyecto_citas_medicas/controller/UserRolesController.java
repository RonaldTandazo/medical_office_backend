package com.example.proyecto_citas_medicas.controller;

import com.example.proyecto_citas_medicas.dtos.LoginUserDto;
import com.example.proyecto_citas_medicas.entities.ApiResponse;
import com.example.proyecto_citas_medicas.entities.User;
import com.example.proyecto_citas_medicas.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserRolesController {

    private static final Logger logger = LoggerFactory.getLogger(UserRolesController.class);
    private final UserService userService;

    public UserRolesController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user_roles")
    public ResponseEntity<ApiResponse> get_user_roles(@RequestBody LoginUserDto loginUserDto) {
        try {
            User verifyUser = userService.verifyUser(loginUserDto.getEmail());
            if(verifyUser == null){
                return ResponseEntity.ok(new ApiResponse(false, "User Not Found", null, HttpStatus.NOT_FOUND.value()));
            }

            List<Map<String, Object>> userRoles = userService.getUserRoles(verifyUser.getId());
            if (userRoles.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse(false, "No roles found", null, HttpStatus.NOT_FOUND.value()));
            }

            return ResponseEntity.ok(new ApiResponse(true, "User Found!!!", userRoles, HttpStatus.OK.value()));
        } catch (Exception e) {
            String className = this.getClass().getName();
            String methodName = new Throwable().getStackTrace()[0].getMethodName();
            logger.error("ERROR:"+className + ":" + methodName+" -> "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "Error Searching User Roles: " + e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
