package com.example.proyecto_citas_medicas.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.example.proyecto_citas_medicas.entities.User;
import com.example.proyecto_citas_medicas.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User verifyUser(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    public List<Map<String, Object>> getUserRoles(Long user_id){
        return userRepository.findRolesByUserId(user_id);
    }

    public User updatePassword(Long user_id, String new_password){
        return userRepository.updatePassword(user_id, new_password);
    } 
}
