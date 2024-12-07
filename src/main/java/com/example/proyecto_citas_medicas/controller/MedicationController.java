package com.example.proyecto_citas_medicas.controller;

import com.example.proyecto_citas_medicas.entities.ApiResponse;
import com.example.proyecto_citas_medicas.entities.Medication;
import com.example.proyecto_citas_medicas.service.MedicationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import java.util.List;

@RestController
@RequestMapping("/api/medications/")
public class MedicationController {

    private final MedicationService medicationServie;
    private static final Logger logger = LoggerFactory.getLogger(MedicationController.class);
    
    public MedicationController(MedicationService medicationServie) {
        this.medicationServie = medicationServie;
    }

    @GetMapping("medications_by_name")
    public ResponseEntity<ApiResponse> getDoctorsBySpeciality(
        @RequestParam(value = "medication", required = true) String medication,
        @RequestParam("page") int page, @RequestParam("size") int size
    ){
        try{
            Page</*Map<String, Object>*/Medication> medicationResponse = medicationServie.getMedicationsByName(medication, page, size);

            return ResponseEntity.ok(new ApiResponse(true, "Information Found", medicationResponse, HttpStatus.OK.value()));
        }catch(Exception e){
            String className = this.getClass().getName();
            String methodName = new Throwable().getStackTrace()[0].getMethodName();
            logger.error("ERROR:"+className + ":" + methodName+" -> "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "Invalid Credentials", null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
