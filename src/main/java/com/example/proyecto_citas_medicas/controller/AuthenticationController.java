package com.example.proyecto_citas_medicas.controller;

import com.example.proyecto_citas_medicas.dtos.LoginResponse;
import com.example.proyecto_citas_medicas.dtos.LoginUserDto;
import com.example.proyecto_citas_medicas.dtos.RecoverPasswordDto;
import com.example.proyecto_citas_medicas.dtos.RegisterUserDto;
import com.example.proyecto_citas_medicas.entities.ApiResponse;
import com.example.proyecto_citas_medicas.entities.Doctor;
import com.example.proyecto_citas_medicas.entities.User;
import com.example.proyecto_citas_medicas.entities.UserTokens;
import com.example.proyecto_citas_medicas.service.AuthenticationService;
import com.example.proyecto_citas_medicas.service.DoctorService;
import com.example.proyecto_citas_medicas.service.EmailService;
import com.example.proyecto_citas_medicas.service.UserService;
import com.example.proyecto_citas_medicas.service.UserTokensService;
import com.example.proyecto_citas_medicas.utils.JwtUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth/")
@RestController
public class AuthenticationController {
    private final JwtUtil jwtUtil;
    private final EmailService emailService;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final UserTokensService userTokensService;
    private final DoctorService doctorService;
    private BCryptPasswordEncoder bcryptEncoder;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    public AuthenticationController(
        JwtUtil jwtUtil, 
        EmailService emailService,
        AuthenticationService authenticationService,
        UserService userService,
        UserTokensService userTokensService,
        BCryptPasswordEncoder bcryptEncoder,
        DoctorService doctorService
    ) {
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.userTokensService = userTokensService;
        this.bcryptEncoder = bcryptEncoder;
        this.doctorService = doctorService;
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
            Long role_id = loginUserDto.getRoleId();
            String role = loginUserDto.getRole();
            User authenticatedUser = authenticationService.authenticate(loginUserDto);
            List<Map<String, Object>> permissions = userService.getPermissions(authenticatedUser.getUserId(), role_id);

            Map<String, Object> userData = new HashMap<>();
            userData.put("user_id", authenticatedUser.getUserId());
            userData.put("email", authenticatedUser.getEmail());
            userData.put("username", authenticatedUser.getUsername());
            userData.put("identification", authenticatedUser.getIdentification());
            userData.put("gender", authenticatedUser.getGender());
            userData.put("age", authenticatedUser.getAge());
            userData.put("phonenumber", authenticatedUser.getPhonenumber());
            userData.put("role_id", role_id);
            userData.put("role", role);
            
            if(role_id == 2){
                Doctor doctor = doctorService.findDoctorByUserId(authenticatedUser.getUserId());
                if(doctor != null){
                    userData.put("doctor_id", doctor.getDoctorId());
                }
            }
            
            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("user_data", userData);
            extraClaims.put("permissions", permissions);
            
            String jwtToken = jwtUtil.generateToken(extraClaims, authenticatedUser);
            
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
                return ResponseEntity.ok(new ApiResponse(false, "Este E-mail ya está en uso", null, HttpStatus.FOUND.value()));
            }

            return ResponseEntity.ok(new ApiResponse(true, "E-mail Válido", null, HttpStatus.OK.value()));
        }catch(Exception e){
            String className = this.getClass().getName();
            String methodName = new Throwable().getStackTrace()[0].getMethodName();
            logger.error("ERROR:"+className + ":" + methodName+" -> "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "Error", null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
    

    @PostMapping("send_recover_email")
    public ResponseEntity<ApiResponse> recoverEmail(@RequestBody RecoverPasswordDto recover) {
        try {
            User verifyUser = userService.verifyUser(recover.getEmail());
            if(verifyUser == null){
                return ResponseEntity.ok(new ApiResponse(false, "User Not Found", null, HttpStatus.NOT_FOUND.value()));
            }

            String token = UUID.randomUUID().toString();
            emailService.sendPasswordResetEmail(recover.getEmail(), token);

            UserTokens find_item = userTokensService.findItemByUserId(verifyUser.getUserId());
            UserTokens info = null;

            if(find_item == null){
                info = userTokensService.insertItem(verifyUser.getUserId(), token);
            }else{
                info = userTokensService.updateItem(find_item.getUserTokenId(), token);
            }

            return ResponseEntity.ok(new ApiResponse(true, "Correo de Restalecer Contraseña Enviado", info, HttpStatus.OK.value()));
        } catch (Exception e) {
            String className = this.getClass().getName();
            String methodName = new Throwable().getStackTrace()[0].getMethodName();
            logger.error("ERROR: "+className + ":" + methodName+" -> "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error Sending Recover Email", null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PutMapping("reset_password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam("token") String token, @RequestBody RecoverPasswordDto request) {
        try {
            UserTokens userToken = userTokensService.findItemByToken(token);
            if (userToken == null) {
                return ResponseEntity.ok(new ApiResponse(false, "Link Expirado", null, HttpStatus.NOT_FOUND.value()));
            }
            
            Long user_token_id = userToken.getUserTokenId();
            String password_crypted = bcryptEncoder.encode(request.getNewPassword());
            boolean update = userService.updatePassword(userToken.getUserId(), password_crypted);

            if(!update){
                return ResponseEntity.ok(new ApiResponse(false, "No Se puede restablecer la contraseña", null, HttpStatus.NOT_MODIFIED.value()));
            }

            userTokensService.updateItem(user_token_id, null);

            return ResponseEntity.ok(new ApiResponse(true, "Contraseña Restablecida", null, HttpStatus.OK.value()));
        } catch (Exception e) {
            String className = this.getClass().getName();
            String methodName = new Throwable().getStackTrace()[0].getMethodName();
            logger.error("ERROR: "+className + ":" + methodName+" -> "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error Recovering Password", null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
