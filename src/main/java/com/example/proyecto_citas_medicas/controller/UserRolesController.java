package com.example.proyecto_citas_medicas.controller;

import com.example.proyecto_citas_medicas.dtos.LoginUserDto;
import com.example.proyecto_citas_medicas.entities.ApiResponse;
import com.example.proyecto_citas_medicas.entities.User;
import com.example.proyecto_citas_medicas.service.UserRolesService;
import com.example.proyecto_citas_medicas.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/")
public class UserRolesController {

    private static final Logger logger = LoggerFactory.getLogger(UserRolesController.class);
    private final UserService userService;
    private final UserRolesService userRoleSerive;

    public UserRolesController(UserService userService, UserRolesService userRoleSerive) {
        this.userService = userService;
        this.userRoleSerive = userRoleSerive;
    }

    @PostMapping("user_roles")
    public ResponseEntity<ApiResponse> getUserRoles(@RequestBody LoginUserDto loginUserDto) {
        try {
            User verifyUser = userService.verifyUser(loginUserDto.getEmail());

            List<Map<String, Object>> userRoles = userRoleSerive.getUserRoles(verifyUser.getUserId());
            if (userRoles.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse(false, "No roles found", null, HttpStatus.NOT_FOUND.value()));
            }

            return ResponseEntity.ok(new ApiResponse(true, "User Found", userRoles, HttpStatus.OK.value()));
        } catch (NoSuchElementException e) {
            String className = this.getClass().getName();
            String methodName = new Throwable().getStackTrace()[0].getMethodName();
            logger.error("ERROR: "+className + ":" + methodName+" -> "+e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage(), null, HttpStatus.NOT_FOUND.value()));
        } catch (Exception e) {
            String className = this.getClass().getName();
            String methodName = new Throwable().getStackTrace()[0].getMethodName();
            logger.error("ERROR: "+className + ":" + methodName+" -> "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PutMapping("user_roles/role_activation_control/{user_role_id}")
    public ResponseEntity<ApiResponse> roleActivationControl(
        @PathVariable("user_role_id") Long user_role_id,
        @RequestParam("state") String state
    ){
        try {
            boolean activationControl = userRoleSerive.roleActivationControl(user_role_id, state);

            return ResponseEntity.ok(
                    new ApiResponse(true, "User Role Status Updated", activationControl, HttpStatus.OK.value())
            );
        } catch (NoSuchElementException e) {
            String className = this.getClass().getName();
            String methodName = new Throwable().getStackTrace()[0].getMethodName();
            logger.error("ERROR: "+className + ":" + methodName+" -> "+e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "User Role not found", null, HttpStatus.NOT_FOUND.value()));
        } catch(Exception e){
            String className = this.getClass().getName();
            String methodName = new Throwable().getStackTrace()[0].getMethodName();
            logger.error("ERROR:"+className + ":" + methodName+" -> "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "Error Updating Status", null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
