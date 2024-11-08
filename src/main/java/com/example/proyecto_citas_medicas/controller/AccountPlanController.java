package com.example.proyecto_citas_medicas.controller;

import com.example.proyecto_citas_medicas.entities.AccountPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.example.proyecto_citas_medicas.repository.AccountPlanRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/account-plans")
public class AccountPlanController {
}

