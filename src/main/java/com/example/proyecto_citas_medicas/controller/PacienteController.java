package com.example.proyecto_citas_medicas.controller;

import com.example.proyecto_citas_medicas.entities.ApiResponse;
import com.example.proyecto_citas_medicas.entities.Medico;
import java.util.Map;
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

    private final PacienteService pacienteService;
    private final MedicoService medicoService;
    private static final Logger logger = LoggerFactory.getLogger(PacienteController.class);

    public PacienteController(PacienteService pacienteService, MedicoService medicoService) {
        this.pacienteService = pacienteService;
        this.medicoService = medicoService;
    }

    @GetMapping("patients_by_doctor")
    public ResponseEntity<ApiResponse> getPacientesByDoctor(@RequestParam("user_id") Long user_id, @RequestParam("page") int page, @RequestParam("size") int size){
        logger.info("page: "+page);
        try{
            Medico medico = medicoService.findDoctorByUserId(user_id);

            Page<Map<String, Object>> patientsResponse = pacienteService.getPacientesByDoctor(medico.getId(), page, size);
            logger.info("patients: "+patientsResponse.getTotalElements());
            logger.info("patients: "+patientsResponse.getContent());

            return ResponseEntity.ok(new ApiResponse(true, "Information Found", patientsResponse, HttpStatus.OK.value()));
        }catch(Exception e){
            String className = this.getClass().getName();
            String methodName = new Throwable().getStackTrace()[0].getMethodName();
            logger.error("ERROR:"+className + ":" + methodName+" -> "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "Invalid Credentials", null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}