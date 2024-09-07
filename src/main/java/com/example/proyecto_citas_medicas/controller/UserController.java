package com.example.proyecto_citas_medicas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.proyecto_citas_medicas.entities.ApiResponse;
import com.example.proyecto_citas_medicas.entities.User;
import com.example.proyecto_citas_medicas.service.UserService;

@RestController
@RequestMapping("/api/user/")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserRolesController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("update_information")
    public ResponseEntity<ApiResponse> updateInformation(@RequestBody User user){
        try{
            User verifyUser = userService.verifyUser(user.getEmail());
            if(verifyUser == null){
                return ResponseEntity.ok(new ApiResponse(false, "User Not Found", null, HttpStatus.NOT_FOUND.value()));
            }

            verifyUser.setUsername(user.getUsername());
            verifyUser.setIdentification(user.getIdentification());
            verifyUser.setGender(user.getGender());
            verifyUser.setAge(user.getAge());
            verifyUser.setPhonenumber(user.getPhonenumber());

            userService.updateItem(verifyUser);

            return ResponseEntity.ok(new ApiResponse(true, "Information Updated", null, HttpStatus.OK.value()));
        }catch(Exception e){
            String className = this.getClass().getName();
            String methodName = new Throwable().getStackTrace()[0].getMethodName();
            logger.error("ERROR:"+className + ":" + methodName+" -> "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "Error While Updating", null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
