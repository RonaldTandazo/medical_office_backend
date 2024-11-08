package com.example.proyecto_citas_medicas.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.proyecto_citas_medicas.controller.AuthenticationController;
import com.example.proyecto_citas_medicas.entities.User;
import com.example.proyecto_citas_medicas.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

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

    public List<Map<String, Object>> getPermissions(Long user_id) {
        List<Map<String, Object>> menus = userRepository.getAsignedMenus(user_id);

        List<Map<String, Object>> mutableMenus = new ArrayList<>();

        for (Map<String, Object> menu : menus) {
            Map<String, Object> mutableMenu = new HashMap<>(menu);

            Long menu_id = (Long) mutableMenu.get("menu_id");

            List<Map<String, Object>> submenus = userRepository.getPermissions(user_id, menu_id);

            mutableMenu.put("submenus", submenus);
            mutableMenus.add(mutableMenu);
        }

        return mutableMenus;
    }

}
