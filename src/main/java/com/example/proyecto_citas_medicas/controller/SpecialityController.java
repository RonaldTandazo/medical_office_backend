package com.example.proyecto_citas_medicas.controller;

import com.example.proyecto_citas_medicas.entities.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.proyecto_citas_medicas.service.SpecialityService;

@RestController
@RequestMapping("/api/specialities")
public class SpecialityController {

    private final SpecialityService specialityService;
    private static final Logger logger = LoggerFactory.getLogger(SpecialityController.class);

    public SpecialityController(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllSpecialities() {
        try{
            return ResponseEntity.ok(new ApiResponse(true, "Specialities Found", specialityService.findAll(), HttpStatus.OK.value()));
        }catch(Exception e){
            String className = this.getClass().getName();
            String methodName = new Throwable().getStackTrace()[0].getMethodName();
            logger.error("ERROR:"+className + ":" + methodName+" -> "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "Server Error", null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
