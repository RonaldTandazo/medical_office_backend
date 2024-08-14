package com.example.proyecto_citas_medicas.controller;

import com.example.proyecto_citas_medicas.entities.GeneralParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.example.proyecto_citas_medicas.repository.GeneralParameterRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/general-parameters")
public class GeneralParameterController {

    @Autowired
    private GeneralParameterRepository generalParameterRepository;

    @GetMapping
    public List<GeneralParameter> getAllGeneralParameters() {
        return generalParameterRepository.findAll();
    }

    @PostMapping
    public GeneralParameter createGeneralParameter(@RequestBody GeneralParameter generalParameter) {
        return generalParameterRepository.save(generalParameter);
    }

    @PutMapping("/{id}")
    public GeneralParameter updateGeneralParameter(@PathVariable Long id, @RequestBody GeneralParameter generalParameterDetails) {
        GeneralParameter generalParameter = generalParameterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parameter not found for this id :: " + id));

        generalParameter.setParameterName(generalParameterDetails.getParameterName());
        generalParameter.setParameterValue(generalParameterDetails.getParameterValue());

        return generalParameterRepository.save(generalParameter);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteGeneralParameter(@PathVariable Long id) {
        GeneralParameter generalParameter = generalParameterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parameter not found for this id :: " + id));

        generalParameterRepository.delete(generalParameter);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}