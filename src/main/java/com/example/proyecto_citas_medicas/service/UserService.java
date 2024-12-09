package com.example.proyecto_citas_medicas.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.example.proyecto_citas_medicas.entities.User;
import com.example.proyecto_citas_medicas.repository.UserRepository;
import com.example.proyecto_citas_medicas.specifications.UserSpecification;

@Service
public class UserService {
    protected final UserRepository userRepository;
    protected final UserSpecification userSpecification;

    public UserService(UserRepository userRepository, UserSpecification userSpecification){
        this.userRepository = userRepository;
        this.userSpecification = userSpecification;
    }

    public Map<String, Object> getAllUsers(String identification, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
    
        Specification<User> spec = this.userSpecification.getAllUsers(identification);
    
        Page<User> usersPage = userRepository.findAll(spec, pageable);
    
        List<Map<String, Object>> dataUsers = new ArrayList<>();
    
        for (User user : usersPage) {
            Long user_id = user.getUserId();
            Character[] status = {'A', 'I', 'D'};
            List<Map<String, Object>> roles = userRepository.findRolesByUserId(user_id, status);
    
            Map<String, Object> userData = new HashMap<>();
            userData.put("user_id", user.getUserId());
            userData.put("fullname", user.getUsername());
            userData.put("identification", user.getIdentification());
            userData.put("email", user.getEmail());
            userData.put("phonenumber", user.getPhonenumber());
            userData.put("age", user.getAge());
            userData.put("gender", user.getGender());
            userData.put("status", user.getStatus());
            userData.put("roles", roles);
    
            dataUsers.add(userData);
        }
    
        // Estructura de respuesta final
        Map<String, Object> response = new HashMap<>();
        response.put("users", dataUsers);
        response.put("pagination", Map.of(
            "totalElements", usersPage.getTotalElements(),
            "totalPages", usersPage.getTotalPages(),
            "currentPage", usersPage.getNumber(),
            "pageSize", usersPage.getSize()
        ));
        
        return response;
    } 

    public User verifyUser(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    public List<Map<String, Object>> getUserRoles(Long user_id){
        Character[] status = {'A'};
        return userRepository.findRolesByUserId(user_id, status);
    }

    public User updatePassword(Long user_id, String new_password){
        return userRepository.updatePassword(user_id, new_password);
    }

    public User updateUserInformation(User user){
        Long userId = user.getUserId();
        String username = user.getUsername();
        String identification = user.getIdentification();
        Character gender = user.getGender();
        Long age = user.getAge();
        String phonenumber = user.getPhonenumber();

        return userRepository.updateUserInformation(userId, username, identification, gender, age, phonenumber);
    }

    public List<Map<String, Object>> getPermissions(Long user_id, Long role_id) {
        List<Map<String, Object>> menus = userRepository.getAsignedMenus(user_id, role_id);

        List<Map<String, Object>> mutableMenus = new ArrayList<>();

        for (Map<String, Object> menu : menus) {
            Map<String, Object> mutableMenu = new HashMap<>(menu);

            Long menu_id = (Long) mutableMenu.get("menu_id");

            List<Map<String, Object>> submenus = userRepository.getPermissions(user_id, role_id, menu_id);

            mutableMenu.put("submenus", submenus);
            mutableMenus.add(mutableMenu);
        }

        return mutableMenus;
    }
}
