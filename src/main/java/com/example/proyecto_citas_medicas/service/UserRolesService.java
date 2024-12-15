package com.example.proyecto_citas_medicas.service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example.proyecto_citas_medicas.entities.UserRoles;
import com.example.proyecto_citas_medicas.repository.UserRolesRepository;

@Service
public class UserRolesService {

    private final UserRolesRepository userRolesRepository;

    public UserRolesService(UserRolesRepository userRolesRepository) {
        this.userRolesRepository = userRolesRepository;
    }

    public List<Map<String, Object>> getUserRoles(Long user_id){
        Character[] status = {'A'};
        return userRolesRepository.findRolesByUserId(user_id, status);
    }

    public boolean roleActivationControl(Long user_role_id, String state){
        if (!"Activate".equalsIgnoreCase(state) && !"Inactivate".equalsIgnoreCase(state)) {
            throw new IllegalArgumentException("Invalid state: " + state);
        }

        Character status = "Activate".equalsIgnoreCase(state) ? 'A' : 'I';

        Optional<UserRoles> existingUserRole = userRolesRepository.findById(user_role_id);

        if (existingUserRole.isEmpty()) {
            throw new NoSuchElementException("User Role not found");
        }

        UserRoles userRole = existingUserRole.get();
        userRole.setStatus(status);
        userRolesRepository.save(userRole);

        return true;
    }
}
