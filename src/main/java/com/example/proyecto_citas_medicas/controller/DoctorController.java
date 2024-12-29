package com.example.proyecto_citas_medicas.controller;

import com.example.proyecto_citas_medicas.entities.ApiResponse;
import com.example.proyecto_citas_medicas.entities.Doctor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.proyecto_citas_medicas.service.DoctorService;
import java.util.List;

@RestController
@RequestMapping("/api/doctors/")
public class DoctorController {

    private final DoctorService doctorService;
    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);
    
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // @GetMapping
    // public List<Doctor> getAllDoctors() {
    //     return doctorService.findAll();
    // }

    @GetMapping("doctors_by_speciality")
    public ResponseEntity<ApiResponse> getDoctorsBySpeciality(
        @RequestParam(value = "speciality_id", required = true) Long speciality_id
    ){
        try{
            List<Doctor> doctors = doctorService.getDoctorsBySpeciality(speciality_id);

            return ResponseEntity.ok(new ApiResponse(true, "Information Found", doctors, HttpStatus.OK.value()));
        }catch(Exception e){
            String className = this.getClass().getName();
            String methodName = new Throwable().getStackTrace()[0].getMethodName();
            logger.error("ERROR:"+className + ":" + methodName+" -> "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "Server Error", null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PutMapping("unlink_patient/{patient_id}/{doctor_id}")
    public ResponseEntity<ApiResponse> unlikPatient(@PathVariable("patient_id") Long patient_id, @PathVariable("doctor_id") Long doctor_id) {
        try {
            boolean unlik = doctorService.unlinkPatient(patient_id, doctor_id);
            
            return ResponseEntity.ok(new ApiResponse(true, "Patient Unlinked Successfully", unlik, HttpStatus.OK.value()));
        } catch (Exception e) {
            String className = this.getClass().getName();
            String methodName = new Throwable().getStackTrace()[0].getMethodName();
            logger.error("ERROR:"+className + ":" + methodName+" -> "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "Invalid Credentials", null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
