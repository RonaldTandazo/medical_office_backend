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

    public User updateItem(User user){
        Long userId = user.getId();
        String username = user.getUsername();
        String identification = user.getIdentification();
        Character gender = user.getGender();
        Long age = user.getAge();
        String phonenumber = user.getPhonenumber();

        return userRepository.updateItem(userId, username, identification, gender, age, phonenumber);
    }

    public List<Map<String, Object>> getPermissions(Long user_id){
        return userRepository.getPermissions(user_id);
    }
}
