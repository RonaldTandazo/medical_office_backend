package com.example.proyecto_citas_medicas.controller;

import com.example.proyecto_citas_medicas.entities.*;
import com.example.proyecto_citas_medicas.repository.UserRepository;
import com.example.proyecto_citas_medicas.service.CustomUserDetailsService;
import com.example.proyecto_citas_medicas.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {
    private final CustomUserDetailsService userService;
    private final EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(UserRolesController.class);
    private final UserRepository userRepository;

    public UserController(CustomUserDetailsService customUserDetailsService, EmailService emailService, UserRepository userRepository) {
        this.userService = customUserDetailsService;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody User user) {
        try {
            User savedUser = userService.saveUser(user);

            return ResponseEntity.ok(new ApiResponse(true, "User Successfully Registered", savedUser, 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error on Registering User: " + e.getMessage(), null, 500));
        }
    }

    @PostMapping("/send_recover_mail")
    public ResponseEntity<ApiResponse> recover_email(@RequestBody User user) {
        try {
            Optional<User> verifyUser = userService.getUserByEmail(user.getEmail());

            if(!verifyUser.isPresent()){
                return ResponseEntity.ok(new ApiResponse(true, "User Not Found", null, 303));
            }

            String token = UUID.randomUUID().toString();
            emailService.sendPasswordResetEmail(user.getEmail(), token);

            Optional<UserTokens.UsersTokensProjection> find_item = userService.findUserToken(verifyUser.get().getId());
            if(!find_item.isPresent()){
                Optional<UserTokens> store_token = userService.storeItem(verifyUser.get().getId(), token);
            }

            Optional<UserTokens> update_item = userService.updateUserToken(find_item.get().getUser_token_id(), token);

            return ResponseEntity.ok(new ApiResponse(true, "Email Send", null, 200));
        } catch (Exception e) {
            String className = this.getClass().getName();
            String methodName = new Throwable().getStackTrace()[0].getMethodName();
            logger.error("ERROR:"+className + ":" + methodName+" -> "+e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error Sending Recover Email", null, 404));
        }
    }

    @PostMapping("/reset_password")
    public ResponseEntity<ApiResponse> reset_password(@RequestParam String token, @RequestBody PasswordReset request) {
        try {
            logger.info("token: "+token);
            logger.info("New Password: "+request.getNewPassword());
            logger.info("Confirm Password: "+request.getConfirmPassword());

            return ResponseEntity.ok(new ApiResponse(true, "User Found!!!", null, 200));
        } catch (Exception e) {
            String className = this.getClass().getName();
            String methodName = new Throwable().getStackTrace()[0].getMethodName();
            logger.error("ERROR:"+className + ":" + methodName+" -> "+e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error Recovering Password", null, 404));
        }
    }
}
