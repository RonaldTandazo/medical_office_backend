package com.example.proyecto_citas_medicas.controller;

import com.example.proyecto_citas_medicas.entities.ApiResponse;
import com.example.proyecto_citas_medicas.entities.Patient;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.proyecto_citas_medicas.service.PatientService;

@RestController
@RequestMapping("/api/patients/")
public class PatientController {

    private final PatientService patienteService;
    //private final DoctorService medicoService;
    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    public PatientController(PatientService patienteService/*, DoctorService medicoService*/) {
        this.patienteService = patienteService;
        //this.medicoService = medicoService;
    }

    @GetMapping("patients_by_doctor")
    public ResponseEntity<ApiResponse> getPacientesByDoctor(
        @RequestParam(value = "identification", required = false) String identification,
        @RequestParam(value = "patient", required = false) String patient,
        @RequestParam(value = "gender", required = false) Character gender,
        @RequestParam(value = "doctorId", required = true) Long doctorId,
        @RequestParam("page") int page, @RequestParam("size") int size
    ){
        try{
            Page</*Map<String, Object>*/Patient> patientsResponse = patienteService.getPacientesByDoctor(doctorId, identification, patient, gender, page, size);

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