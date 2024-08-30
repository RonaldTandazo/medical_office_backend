package com.example.proyecto_citas_medicas.controller;

import com.example.proyecto_citas_medicas.dtos.LoginResponse;
import com.example.proyecto_citas_medicas.dtos.LoginUserDto;
import com.example.proyecto_citas_medicas.dtos.RegisterUserDto;
import com.example.proyecto_citas_medicas.entities.ApiResponse;
import com.example.proyecto_citas_medicas.entities.User;
import com.example.proyecto_citas_medicas.service.AuthenticationService;
import com.example.proyecto_citas_medicas.service.EmailService;
import com.example.proyecto_citas_medicas.service.UserService;
import com.example.proyecto_citas_medicas.utils.JwtUtil;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/auth/")
@RestController
public class AuthenticationController {
    private final JwtUtil jwtUtil;
    private final EmailService emailService;
    private final AuthenticationService authenticationService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final UserService userService;

    public AuthenticationController(JwtUtil jwtUtil, EmailService emailService, AuthenticationService authenticationService, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("signup")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterUserDto registerUserDto) {
        try{
            User registeredUser = authenticationService.signup(registerUserDto);

            return ResponseEntity.ok(new ApiResponse(true, "Signed Up Successfully", registeredUser, HttpStatus.OK.value()));
        }catch(Exception e){
            String className = this.getClass().getName();
            String methodName = new Throwable().getStackTrace()[0].getMethodName();
            logger.error("ERROR:"+className + ":" + methodName+" -> "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "Signed Up Failed", null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        try{
            User authenticatedUser = authenticationService.authenticate(loginUserDto);
    
            String jwtToken = jwtUtil.generateToken(authenticatedUser);
    
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(jwtToken);
            loginResponse.setExpiresIn(jwtUtil.getExpirationTime());

            return ResponseEntity.ok(new ApiResponse(true, "Logged Successfully", loginResponse, HttpStatus.OK.value()));
        }catch(Exception e){
            String className = this.getClass().getName();
            String methodName = new Throwable().getStackTrace()[0].getMethodName();
            logger.error("ERROR:"+className + ":" + methodName+" -> "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "Invalid Credentials", null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PostMapping("verify_email")
    public ResponseEntity<ApiResponse> verifyUser(@RequestBody User user) {
        try{
            User verifyUser = userService.verifyUser(user.getEmail());
            if(verifyUser != null){
                return ResponseEntity.ok(new ApiResponse(false, "This E-mail is already in use", null, HttpStatus.FOUND.value()));
            }

            return ResponseEntity.ok(new ApiResponse(true, "Can use this E-mail", null, HttpStatus.OK.value()));
        }catch(Exception e){
            String className = this.getClass().getName();
            String methodName = new Throwable().getStackTrace()[0].getMethodName();
            logger.error("ERROR:"+className + ":" + methodName+" -> "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "Error", null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
    

    @PostMapping("send_recover_email")
    public ResponseEntity<ApiResponse> recover_email(@RequestBody User user) {
        try {
            User verifyUser = userService.verifyUser(user.getEmail());

            if(verifyUser == null){
                return ResponseEntity.ok(new ApiResponse(true, "User Not Found", null, HttpStatus.NOT_FOUND.value()));
            }

            String token = UUID.randomUUID().toString();
            emailService.sendPasswordResetEmail(user.getEmail(), token);

            Optional<UserTokens.UsersTokensProjection> find_item = userService.findUserToken(verifyUser.getId());
            if(find_item.isEmpty()){
                Optional<UserTokens.UsersTokensProjection> store_token = userService.storeItem(verifyUser.getId(), token);
            }

            Optional<UserTokens.UsersTokensProjection> update_item = userService.updateUserToken(find_item.get().getUser_token_id(), token);

            return ResponseEntity.ok(new ApiResponse(true, "Recover Password Email Send", null, HttpStatus.OK.value()));
        } catch (Exception e) {
            String className = this.getClass().getName();
            String methodName = new Throwable().getStackTrace()[0].getMethodName();
            logger.error("ERROR:"+className + ":" + methodName+" -> "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error Sending Recover Email", null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    // @PutMapping("/reset_password")
    // public ResponseEntity<ApiResponse> reset_password(@RequestParam String token, @RequestBody PasswordReset request) {
    //     try {
    //         Optional<UserTokens.UsersTokensProjection> user = userService.findUserByToken(token);
    //         if (user.isEmpty()) {
    //             return ResponseEntity.ok(new ApiResponse(true, "Expired Link", null, 303));
    //         }


    //         String password_crypted = bcryptEncoder.encode(request.getNewPassword());
    //         Optional<User> updatePassword = userService.updatePassword(user.get().getUser_id(), password_crypted);

    //         Long user_token_id = user.get().getUser_token_id();
    //         Optional<UserTokens.UsersTokensProjection> update_item = userService.updateUserToken(user_token_id, null);

    //         return ResponseEntity.ok(new ApiResponse(true, "Password Reset", null, 200));
    //     } catch (Exception e) {
    //         String className = this.getClass().getName();
    //         String methodName = new Throwable().getStackTrace()[0].getMethodName();
    //         logger.error("ERROR:"+className + ":" + methodName+" -> "+e.getMessage());

    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    //                 .body(new ApiResponse(false, "Error Recovering Password", null, 404));
    //     }
    // }
}
