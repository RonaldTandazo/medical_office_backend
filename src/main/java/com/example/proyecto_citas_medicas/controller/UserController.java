package com.example.proyecto_citas_medicas.controller;

import com.example.proyecto_citas_medicas.entities.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.proyecto_citas_medicas.service.CustomUserDetailsService;
import com.example.proyecto_citas_medicas.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {
    private final CustomUserDetailsService userService;
    private final EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(UserRolesController.class);
    private final BCryptPasswordEncoder bcryptEncoder;

    public UserController(CustomUserDetailsService customUserDetailsService, EmailService emailService, BCryptPasswordEncoder bcryptEncoder) {
        this.userService = customUserDetailsService;
        this.emailService = emailService;
        this.bcryptEncoder = bcryptEncoder;
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

            if(verifyUser.isEmpty()){
                return ResponseEntity.ok(new ApiResponse(true, "User Not Found", null, 303));
            }

            String token = UUID.randomUUID().toString();
            emailService.sendPasswordResetEmail(user.getEmail(), token);

            Optional<UserTokens.UsersTokensProjection> find_item = userService.findUserToken(verifyUser.get().getId());
            if(find_item.isEmpty()){
                Optional<UserTokens.UsersTokensProjection> store_token = userService.storeItem(verifyUser.get().getId(), token);
            }

            Optional<UserTokens.UsersTokensProjection> update_item = userService.updateUserToken(find_item.get().getUser_token_id(), token);

            return ResponseEntity.ok(new ApiResponse(true, "Recover Password Email Send", null, 200));
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
            Optional<UserTokens.UsersTokensProjection> user = userService.findUserByToken(token);
            if (user.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse(true, "Expired Link", null, 303));
            }


            String password_crypted = bcryptEncoder.encode(request.getNewPassword());
            Optional<User> updatePassword = userService.updatePassword(user.get().getUser_id(), password_crypted);

            BigInteger user_token_id = user.get().getUser_token_id();
            Optional<UserTokens.UsersTokensProjection> update_item = userService.updateUserToken(user_token_id, null);

            return ResponseEntity.ok(new ApiResponse(true, "Password Reset", null, 200));
        } catch (Exception e) {
            String className = this.getClass().getName();
            String methodName = new Throwable().getStackTrace()[0].getMethodName();
            logger.error("ERROR:"+className + ":" + methodName+" -> "+e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error Recovering Password", null, 404));
        }
    }
}
