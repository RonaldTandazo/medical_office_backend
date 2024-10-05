package com.example.proyecto_citas_medicas.controller;

import com.example.proyecto_citas_medicas.entities.ApiResponse;
import com.example.proyecto_citas_medicas.entities.Medico;
import com.example.proyecto_citas_medicas.entities.Patient;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.proyecto_citas_medicas.service.MedicoService;
import com.example.proyecto_citas_medicas.service.PacienteService;


@RestController
@RequestMapping("/api/patients/")
public class PacienteController {

    private final PacienteService patienteService;
    private final MedicoService medicoService;
    private static final Logger logger = LoggerFactory.getLogger(PacienteController.class);

    public PacienteController(PacienteService patienteService, MedicoService medicoService) {
        this.patienteService = patienteService;
        this.medicoService = medicoService;
    }

    @GetMapping("patients_by_doctor")
    public ResponseEntity<ApiResponse> getPacientesByDoctor(@RequestParam("user_id") Long user_id, @RequestParam("page") int page, @RequestParam("size") int size){
        try{
            Page<Map<String, Object>> patientsResponse = patienteService.getPacientesByDoctor(user_id, page, size);

            return ResponseEntity.ok(new ApiResponse(true, "Information Found", patientsResponse, HttpStatus.OK.value()));
        }catch(Exception e){
            String className = this.getClass().getName();
            String methodName = new Throwable().getStackTrace()[0].getMethodName();
            logger.error("ERROR:"+className + ":" + methodName+" -> "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "Invalid Credentials", null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PostMapping("store")
    public ResponseEntity<ApiResponse> store(@RequestBody Patient patient) {
        try {
            patienteService.store(patient);
            return ResponseEntity.ok(new ApiResponse(true, "Patient Save", null, HttpStatus.OK.value()));
        } catch (Exception e) {
            String className = this.getClass().getName();
            String methodName = new Throwable().getStackTrace()[0].getMethodName();
            logger.error("ERROR:"+className + ":" + methodName+" -> "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "Invalid Credentials", null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
    
    @PutMapping("update/{patient_id}")
    public ResponseEntity<ApiResponse> update(@PathVariable("patient_id") Long patient_id, @RequestBody Patient NewData) {
        try {
            Optional<Patient> Patient = patienteService.findById(patient_id);
            if (Patient.isPresent()) {
                patienteService.update(NewData, Patient.get());
                
                return ResponseEntity.ok(new ApiResponse(true, "Patient Updated Successfully", null, HttpStatus.OK.value()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(false, "Patient Not Found", null, HttpStatus.NOT_FOUND.value()));
            }
        } catch (Exception e) {
            String className = this.getClass().getName();
            String methodName = new Throwable().getStackTrace()[0].getMethodName();
            logger.error("ERROR:"+className + ":" + methodName+" -> "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "Invalid Credentials", null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}