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

    @Autowired
    private AccountPlanRepository accountPlanRepository;

    @GetMapping
    public List<AccountPlan> getAllAccountPlans() {
        return accountPlanRepository.findAll();
    }

    @PostMapping
    public AccountPlan createAccountPlan(@RequestBody AccountPlan accountPlan) {
        return accountPlanRepository.save(accountPlan);
    }

    @PutMapping("/{id}")
    public AccountPlan updateAccountPlan(@PathVariable Long id, @RequestBody AccountPlan accountPlanDetails) {
        AccountPlan accountPlan = accountPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account Plan not found for this id :: " + id));

        accountPlan.setAccountName(accountPlanDetails.getAccountName());
        accountPlan.setAccountCode(accountPlanDetails.getAccountCode());
        accountPlan.setAccountType(accountPlanDetails.getAccountType());

        return accountPlanRepository.save(accountPlan);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteAccountPlan(@PathVariable Long id) {
        AccountPlan accountPlan = accountPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account Plan not found for this id :: " + id));

        accountPlanRepository.delete(accountPlan);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}

